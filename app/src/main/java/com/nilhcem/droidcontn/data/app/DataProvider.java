package com.nilhcem.droidcontn.data.app;

import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.data.database.dao.SessionsDao;
import com.nilhcem.droidcontn.data.database.dao.SpeakersDao;
import com.nilhcem.droidcontn.data.network.DroidconService;
import com.nilhcem.droidcontn.data.network.NetworkMapper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;

@Singleton
public class DataProvider {

    private final AppMapper appMapper;
    private final NetworkMapper networkMapper;
    private final DroidconService service;
    private final SpeakersDao speakersDao;
    private final SessionsDao sessionsDao;

    @Inject
    public DataProvider(AppMapper appMapper, NetworkMapper networkMapper, DroidconService service, SpeakersDao speakersDao, SessionsDao sessionsDao) {
        this.appMapper = appMapper;
        this.networkMapper = networkMapper;
        this.service = service;
        this.speakersDao = speakersDao;
        this.sessionsDao = sessionsDao;
    }

    public Observable<Schedule> getSchedule() {
        return getSessions().map(appMapper::toSchedule);
    }

    public Observable<List<Speaker>> getSpeakers() {
        return Observable.create(subscriber -> speakersDao.getSpeakers().subscribe(speakers -> {
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

    private Observable<List<Session>> getSessions() {
        return Observable.create(subscriber -> sessionsDao.getSessions().subscribe(sessions -> {
            if (sessions.isEmpty()) {
                // TODO: get from embedded json
            } else {
                subscriber.onNext(sessions);
            }

            if (!subscriber.isUnsubscribed()) {
                getSessionsFromNetwork(subscriber);
            }
        }, subscriber::onError));
    }

    private void getSessionsFromNetwork(Subscriber<? super List<Session>> subscriber) {
        Observable.zip(
                service.loadSessions(),
                getSpeakers().last().map(appMapper::speakersToMap),
                networkMapper::toAppSessions)
                .subscribe(sessions -> {
                    subscriber.onNext(sessions);
                    sessionsDao.saveSessions(sessions);
                }, throwable -> subscriber.onCompleted(), subscriber::onCompleted);
    }
}
