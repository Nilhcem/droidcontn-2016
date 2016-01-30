package com.nilhcem.droidcontn.data.database.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.nilhcem.droidcontn.utils.Database;

import rx.functions.Func1;

public class SelectedSession {

    public static final String TABLE = "selected_sessions";

    public static final String SLOT_ID = "slot_id";
    public static final String SESSION_ID = "session_id";

    public final int slotId;
    public final int sessionId;

    public SelectedSession(int slotId, int sessionId) {
        this.slotId = slotId;
        this.sessionId = sessionId;
    }

    public static final Func1<Cursor, SelectedSession> MAPPER = cursor -> {
        int slotId1 = Database.getInt(cursor, SLOT_ID);
        int sessionId1 = Database.getInt(cursor, SESSION_ID);
        return new SelectedSession(slotId1, sessionId1);
    };

    public static final class Builder {

        private final ContentValues values = new ContentValues();

        public Builder slotId(int slotId) {
            values.put(SLOT_ID, slotId);
            return this;
        }

        public Builder sessionId(int sessionId) {
            values.put(SESSION_ID, sessionId);
            return this;
        }

        public ContentValues build() {
            return values;
        }
    }
}
