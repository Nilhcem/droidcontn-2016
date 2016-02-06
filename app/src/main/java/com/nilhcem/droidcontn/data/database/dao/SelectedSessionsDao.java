package com.nilhcem.droidcontn.data.database.dao;

import com.nilhcem.droidcontn.core.moshi.LocalDateTimeAdapter;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.database.model.SelectedSession;
import com.squareup.sqlbrite.BriteDatabase;

import org.threeten.bp.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Singleton
public class SelectedSessionsDao {

    private final BriteDatabase database;
    private final LocalDateTimeAdapter adapter;

    // Format: [Slot Time][Session Id]
    private Map<LocalDateTime, Integer> sessions;

    @Inject
    public SelectedSessionsDao(BriteDatabase database, LocalDateTimeAdapter adapter) {
        this.database = database;
        this.adapter = adapter;
    }

    public void init() {
        database.createQuery(SelectedSession.TABLE, "SELECT * FROM " + SelectedSession.TABLE)
                .mapToList(SelectedSession.MAPPER)
                .first()
                .map(selectedSessions -> {
                    Map<LocalDateTime, Integer> sessions = new HashMap<>();
                    for (SelectedSession session : selectedSessions) {
                        sessions.put(adapter.fromText(session.slotTime), session.sessionId);
                    }
                    return sessions;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(sessions -> this.sessions = sessions);
    }

    public boolean isSelected(Session session) {
        Integer selectedSessionId = sessions.get(session.getFromTime());
        return selectedSessionId != null && selectedSessionId == session.getId();
    }

    public void select(Session session) {
        // TODO: NOT ON MAIN THREAD
        unselect(session);
        sessions.put(session.getFromTime(), session.getId());
        database.insert(SelectedSession.TABLE, new SelectedSession.Builder().slotTime(adapter.toText(session.getFromTime())).sessionId(session.getId()).build());
    }

    public void unselect(Session session) {
        // TODO: NOT ON MAIN THREAD
        sessions.remove(session.getFromTime());
        database.delete(SelectedSession.TABLE, SelectedSession.SLOT_TIME + "=?", adapter.toText(session.getFromTime()));
    }

    public Integer get(LocalDateTime slotTime) {
        return sessions.get(slotTime);
    }
}
