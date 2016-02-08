package com.nilhcem.droidcontn.data.database.dao;

import com.nilhcem.droidcontn.data.database.DbMapper;
import com.nilhcem.droidcontn.data.database.model.Speaker;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class SpeakersDao {

    private final BriteDatabase database;
    private final DbMapper dbMapper;

    @Inject
    public SpeakersDao(BriteDatabase database, DbMapper dbMapper) {
        this.database = database;
        this.dbMapper = dbMapper;
    }

    public Observable<List<com.nilhcem.droidcontn.data.app.model.Speaker>> getSpeakers() {
        return database.createQuery(Speaker.TABLE, "SELECT * FROM " + Speaker.TABLE)
                .mapToList(Speaker.MAPPER)
                .first()
                .map(dbMapper::toAppSpeakers);
    }

    public void saveSpeakers(List<com.nilhcem.droidcontn.data.app.model.Speaker> toSave) {
        BriteDatabase.Transaction transaction = database.newTransaction();
        try {
            database.delete(Speaker.TABLE, null);
            for (com.nilhcem.droidcontn.data.app.model.Speaker speaker : toSave) {
                database.insert(Speaker.TABLE, Speaker.createContentValues(dbMapper.fromAppSpeaker(speaker)));
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }
}
