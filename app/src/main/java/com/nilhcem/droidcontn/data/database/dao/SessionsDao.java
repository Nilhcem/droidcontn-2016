package com.nilhcem.droidcontn.data.database.dao;

import com.nilhcem.droidcontn.data.database.DbMapper;
import com.nilhcem.droidcontn.data.database.model.SelectedSession;
import com.nilhcem.droidcontn.data.database.model.Session;
import com.nilhcem.droidcontn.utils.Preconditions;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class SessionsDao {

    private final BriteDatabase database;
    private final DbMapper dbMapper;
    private final SpeakersDao speakersDao;

    @Inject
    public SessionsDao(BriteDatabase database, DbMapper dbMapper, SpeakersDao speakersDao) {
        this.database = database;
        this.dbMapper = dbMapper;
        this.speakersDao = speakersDao;
    }

    public Observable<List<com.nilhcem.droidcontn.data.app.model.Session>> getSessions() {
        return Observable.zip(
                database.createQuery(Session.TABLE, "SELECT * FROM " + Session.TABLE)
                        .mapToList(Session.MAPPER).first(),
                speakersDao.getSpeakersMap(),
                dbMapper::toAppSessions);
    }

    public void saveSessions(List<com.nilhcem.droidcontn.data.app.model.Session> toSave) {
        Preconditions.checkNotOnMainThread();

        BriteDatabase.Transaction transaction = database.newTransaction();
        try {
            database.delete(Session.TABLE, null);
            for (com.nilhcem.droidcontn.data.app.model.Session session : toSave) {
                database.insert(Session.TABLE, Session.createContentValues(dbMapper.fromAppSession(session)));
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    public Observable<List<Session>> getSelectedSessions() {
        String query = String.format(Locale.US, "SELECT * FROM %s INNER JOIN %s ON %s.%s=%s.%s",
                Session.TABLE, SelectedSession.TABLE, Session.TABLE, Session.ID, SelectedSession.TABLE, SelectedSession.SESSION_ID);
        return database.createQuery(Session.TABLE, query)
                .mapToList(Session.MAPPER)
                .first();
    }
}
