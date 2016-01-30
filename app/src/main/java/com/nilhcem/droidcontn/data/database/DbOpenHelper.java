package com.nilhcem.droidcontn.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nilhcem.droidcontn.data.database.model.SelectedSession;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String NAME = "droidcon.db";
    private static final int VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createSelectedSessionsTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createSelectedSessionsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + SelectedSession.TABLE + " (" +
                SelectedSession.SESSION_ID + " INTEGER PRIMARY KEY," +
                SelectedSession.SLOT_ID + " INTEGER);");
    }
}
