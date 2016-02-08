package com.nilhcem.droidcontn.data.app;

import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.data.database.DbMapper;
import com.nilhcem.droidcontn.data.database.dao.SelectedSessionsDao;
import com.nilhcem.droidcontn.data.database.dao.SessionsDao;
import com.nilhcem.droidcontn.data.database.dao.SpeakersDao;
import com.nilhcem.droidcontn.data.network.DroidconService;
import com.nilhcem.droidcontn.data.network.NetworkMapper;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class DataProvider {

    private final AppMapper appMapper;
    private final DbMapper dbMapper;
    private final NetworkMapper networkMapper;
    private final DroidconService service;
    private final SpeakersDao speakersDao;
    private final SessionsDao sessionsDao;
    private final SelectedSessionsDao selectedSessionsDao;

    @Inject
    public DataProvider(AppMapper appMapper, DbMapper dbMapper, NetworkMapper networkMapper, DroidconService service, SpeakersDao speakersDao, SessionsDao sessionsDao, SelectedSessionsDao selectedSessionsDao) {
        this.appMapper = appMapper;
        this.dbMapper = dbMapper;
        this.networkMapper = networkMapper;
        this.service = service;
        this.speakersDao = speakersDao;
        this.sessionsDao = sessionsDao;
        this.selectedSessionsDao = selectedSessionsDao;
    }

    public Observable<Schedule> getSchedule() {
        selectedSessionsDao.init();
        return Observable.combineLatest(getSpeakers(), getSessions(), (speakers, sessions) -> {
            Map<Integer, Speaker> speakersMap = appMapper.speakersToMap(speakers);
            List<Session> appSessions = networkMapper.toAppSessions(sessions, speakersMap);
            return appMapper.toSchedule(appSessions);
        });
    }

    public Observable<List<Speaker>> getSpeakers() {
        return Observable.create(subscriber -> speakersDao.getSpeakers()
                .subscribe(speakers -> {
                    if (speakers.isEmpty()) {
                        // TODO: get from embedded JSON
                    } else {
                        subscriber.onNext(speakers);
                    }

                    if (!subscriber.isUnsubscribed()) {
                        getSpeakersFromNetwork(subscriber);
                    }
                }, subscriber::onError));
    }

    private void getSpeakersFromNetwork(Subscriber<? super List<Speaker>> subscriber) {
        service.loadSpeakers()
                .map(networkMapper::toAppSpeakers)
                .subscribe(speakers -> {
                    subscriber.onNext(speakers);
                    speakersDao.saveSpeakers(speakers);
                }, throwable -> subscriber.onCompleted(), subscriber::onCompleted);
    }

    private Observable<List<com.nilhcem.droidcontn.data.network.model.Session>> getSessions() {
        return Observable.create(subscriber -> {
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
