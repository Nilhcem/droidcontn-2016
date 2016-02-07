package com.nilhcem.droidcontn.data.database.dao;

import com.nilhcem.droidcontn.data.database.model.SelectedSession;
import com.nilhcem.droidcontn.data.database.model.Session;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class SessionsDao {

    private final BriteDatabase database;

    @Inject
    public SessionsDao(BriteDatabase database) {
        this.database = database;
    }

    public Observable<List<Session>> getSessions() {
        return database.createQuery(Session.TABLE, "SELECT * FROM " + Session.TABLE)
                .mapToList(Session.MAPPER)
                .first();
    }

    public void saveSessions(List<Session> toSave) {
        BriteDatabase.Transaction transaction = database.newTransaction();
        try {
            database.delete(Session.TABLE, null);
            for (Session session : toSave) {
                database.insert(Session.TABLE, Session.createContentValues(session));
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
