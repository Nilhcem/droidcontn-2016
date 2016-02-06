package com.nilhcem.droidcontn.data.app;

import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.data.database.DbMapper;
import com.nilhcem.droidcontn.data.database.dao.SelectedSessionsDao;
import com.nilhcem.droidcontn.data.database.dao.SessionsDao;
import com.nilhcem.droidcontn.data.database.dao.SpeakersDao;
import com.nilhcem.droidcontn.data.network.DroidconService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class DataProvider {

    private final DbMapper dbMapper;
    private final DroidconService service;
    private final SpeakersDao speakersDao;
    private final SessionsDao sessionsDao;
    private final SelectedSessionsDao selectedSessionsDao;

    @Inject
    public DataProvider(DbMapper dbMapper, DroidconService service, SpeakersDao speakersDao, SessionsDao sessionsDao, SelectedSessionsDao selectedSessionsDao) {
        this.dbMapper = dbMapper;
        this.service = service;
        this.speakersDao = speakersDao;
        this.sessionsDao = sessionsDao;
        this.selectedSessionsDao = selectedSessionsDao;
    }

    public Observable<Schedule> getSchedule() {
        selectedSessionsDao.init();
        return Observable.combineLatest(getSpeakers(), getSessions(), (speakers, sessions) ->
                AppMapper.toSchedule(sessions, AppMapper.speakersToMap(speakers)));
    }

    public Observable<List<Speaker>> getSpeakerList() {
        return getSpeakers()
                .map(AppMapper::mapSpeakers);
    }

    private Observable<List<com.nilhcem.droidcontn.data.network.model.Speaker>> getSpeakers() {
        return Observable.create(subscriber -> {
            // Get from Database
            speakersDao.getSpeakers()
                    .map(dbMapper::toNetworkSpeaker)
                    .subscribe(dbSpeakers -> {
                        if (dbSpeakers.isEmpty()) {
                            // TODO: get from embedded json
                        } else {
                            subscriber.onNext(dbSpeakers);
                        }

                        if (!subscriber.isUnsubscribed()) {
                            getSpeakersFromNetwork(subscriber);
                        }
                    }, subscriber::onError);
        });
    }

    private void getSpeakersFromNetwork(Subscriber<? super List<com.nilhcem.droidcontn.data.network.model.Speaker>> subscriber) {
        service.loadSpeakers()
                .subscribe(networkSpeakers -> {
                    subscriber.onNext(networkSpeakers);
                    speakersDao.saveSpeakers(dbMapper.fromNetworkSpeakers(networkSpeakers));
                }, throwable -> subscriber.onCompleted(), subscriber::onCompleted);
    }

    private Observable<List<com.nilhcem.droidcontn.data.network.model.Session>> getSessions() {
        return Observable.create(subscriber -> {
            // Get from Database
            sessionsDao.getSessions()
                    .map(dbMapper::toNetworkSession)
                    .subscribe(dbSessions -> {
                        if (dbSessions.isEmpty()) {
                            // TODO: get from embedded json
                        } else {
                            subscriber.onNext(dbSessions);
                        }

                        if (!subscriber.isUnsubscribed()) {
                            getSessionsFromNetwork(subscriber);
                        }
                    }, subscriber::onError);
        });
    }

    private void getSessionsFromNetwork(Subscriber<? super List<com.nilhcem.droidcontn.data.network.model.Session>> subscriber) {
        service.loadSessions()
                .subscribe(networkSessions -> {
                    subscriber.onNext(networkSessions);
                    sessionsDao.saveSessions(dbMapper.fromNetworkSessions(networkSessions));
                }, throwable -> subscriber.onCompleted(), subscriber::onCompleted);
    }
}
