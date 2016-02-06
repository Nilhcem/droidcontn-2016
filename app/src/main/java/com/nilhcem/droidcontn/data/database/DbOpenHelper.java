package com.nilhcem.droidcontn.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nilhcem.droidcontn.data.database.model.SelectedSession;
import com.nilhcem.droidcontn.data.database.model.Speaker;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = "droidcon.db";
    private static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSpeakersTable(db);
        createSelectedSessionsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createSpeakersTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Speaker.TABLE + " (" +
                Speaker.ID + " INTEGER PRIMARY KEY," +
                Speaker.NAME + " VARCHAR," +
                Speaker.TITLE + " VARCHAR," +
                Speaker.BIO + " VARCHAR," +
                Speaker.WEBSITE + " VARCHAR," +
                Speaker.TWITTER + " VARCHAR," +
                Speaker.GITHUB + " VARCHAR," +
                Speaker.PHOTO + " VARCHAR);");
    }

    private void createSelectedSessionsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SelectedSession.TABLE + " (" +
                SelectedSession.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SelectedSession.SLOT_TIME + " VARCHAR," +
                SelectedSession.SESSION_ID + " INTEGER);");
    }
}
