package com.nilhcem.droidcontn.data.database.dao;

import com.nilhcem.droidcontn.data.database.model.Speaker;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class SpeakersDao {

    private final BriteDatabase database;

    @Inject
    public SpeakersDao(BriteDatabase database) {
        this.database = database;
    }

    public Observable<List<Speaker>> getSpeakers() {
        return database.createQuery(Speaker.TABLE, "SELECT * FROM " + Speaker.TABLE)
                .mapToList(Speaker.MAPPER)
                .first();
    }

    public void saveSpeakers(List<Speaker> toSave) {
        BriteDatabase.Transaction transaction = database.newTransaction();
        try {
            database.delete(Speaker.TABLE, null);
            for (Speaker speaker : toSave) {
                database.insert(Speaker.TABLE, Speaker.createContentValues(speaker));
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }
}
