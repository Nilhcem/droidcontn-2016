package com.nilhcem.droidcontn.data.database.dao;

import android.util.SparseIntArray;

import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.database.model.SelectedSession;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class SelectedSessionsDao {

    private final BriteDatabase database;

    // key = slotId, value = sessionId
    private SparseIntArray sessions;

    @Inject
    public SelectedSessionsDao(BriteDatabase database) {
        this.database = database;
    }

    public void init() {
        database.createQuery(SelectedSession.TABLE, "SELECT * FROM " + SelectedSession.TABLE)
                .mapToList(SelectedSession.MAPPER)
                .map(selectedSessions -> {
                    SparseIntArray sessions = new SparseIntArray();
                    for (SelectedSession session : selectedSessions) {
                        sessions.put(session.slotId, session.sessionId);
                    }
                    return sessions;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sessions -> this.sessions = sessions);
    }

    public boolean isSelected(Session session) {
        return session.getId() == sessions.get(session.getSlotId(), -1);
    }

    public void select(Session session) {
        unselect(session);
        sessions.put(session.getSlotId(), session.getId());
        database.insert(SelectedSession.TABLE, new SelectedSession.Builder().slotId(session.getSlotId()).sessionId(session.getId()).build());
    }

    public void unselect(Session session) {
        sessions.delete(session.getSlotId());
        database.delete(SelectedSession.TABLE, SelectedSession.SLOT_ID + "=?", Integer.toString(session.getSlotId()));
    }

    public int get(int slotId) {
        return sessions.get(slotId, -1);
    }
}
