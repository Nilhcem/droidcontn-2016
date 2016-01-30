package com.nilhcem.droidcontn.utils;

import android.database.Cursor;

public class Database {

    public static int getInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    private Database() {
        throw new UnsupportedOperationException();
    }
}
