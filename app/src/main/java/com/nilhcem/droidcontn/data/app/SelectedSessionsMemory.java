package com.nilhcem.droidcontn.data.app;

import org.threeten.bp.LocalDateTime;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SelectedSessionsMemory {

    private final Map<LocalDateTime, Integer> selectedSessions = new ConcurrentHashMap<>();

    @Inject
    public SelectedSessionsMemory() {
    }

    public void setSelectedSessions(Map<LocalDateTime, Integer> selectedSessions) {
        this.selectedSessions.clear();
        this.selectedSessions.putAll(selectedSessions);
    }

    public Integer get(LocalDateTime slotTime) {
        return selectedSessions.get(slotTime);
    }

    public void toggleSessionState(com.nilhcem.droidcontn.data.app.model.Session session, boolean isSelected) {
        selectedSessions.remove(session.getFromTime());
        if (!isSelected) {
            selectedSessions.put(session.getFromTime(), session.getId());
        }
    }
}
