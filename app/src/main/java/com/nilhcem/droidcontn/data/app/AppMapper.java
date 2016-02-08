package com.nilhcem.droidcontn.data.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.data.app.model.ScheduleDay;
import com.nilhcem.droidcontn.data.app.model.ScheduleSlot;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Speaker;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class AppMapper {

    @Inject
    public AppMapper() {
    }

    public Map<Integer, Speaker> speakersToMap(@NonNull List<Speaker> from) {
        Map<Integer, Speaker> map = new HashMap<>(from.size());
        for (Speaker speaker : from) {
            map.put(speaker.getId(), speaker);
        }
        return map;
    }

    public List<Speaker> toSpeakersList(@Nullable List<Integer> speakerIds, @NonNull Map<Integer, Speaker> speakersMap) {
        List<Speaker> speakers = null;
        if (speakerIds != null) {
            speakers = new ArrayList<>(speakerIds.size());
            for (Integer speakerId : speakerIds) {
                speakers.add(speakersMap.get(speakerId));
            }
        }
        return speakers;
    }

    public Schedule toSchedule(@NonNull List<Session> sessions) {
        // Map and sort Session per start date
        Collections.sort(sessions, (lhs, rhs) -> lhs.getFromTime().compareTo(rhs.getFromTime()));

        // Gather Sessions per ScheduleSlot
        List<ScheduleSlot> scheduleSlots = mapToScheduleSlots(sessions);

        // Gather ScheduleSlots per ScheduleDays
        return mapToScheduleDays(scheduleSlots);
    }

    private List<ScheduleSlot> mapToScheduleSlots(@NonNull List<Session> sortedSessions) {
        List<ScheduleSlot> slots = new ArrayList<>();

        LocalDateTime previousTime = null;
        List<Session> previousSessionsList = null;

        LocalDateTime currentTime;
        for (Session currentSession : sortedSessions) {
            currentTime = currentSession.getFromTime();
            if (previousSessionsList != null) {
                if (currentTime.equals(previousTime)) {
                    previousSessionsList.add(currentSession);
                } else {
                    slots.add(new ScheduleSlot(previousTime, previousSessionsList));
                    previousSessionsList = null;
                }
            }

            if (previousSessionsList == null) {
                previousTime = currentTime;
                previousSessionsList = new ArrayList<>();
                previousSessionsList.add(currentSession);
            }
        }

        if (previousSessionsList != null) {
            slots.add(new ScheduleSlot(previousTime, previousSessionsList));
        }
        return slots;
    }

    private Schedule mapToScheduleDays(@NonNull List<ScheduleSlot> scheduleSlots) {
        Schedule schedule = new Schedule();

        LocalDate previousDay = null;
        List<ScheduleSlot> previousSlotList = null;

        LocalDate currentDay;
        for (ScheduleSlot currentSlot : scheduleSlots) {
            currentDay = LocalDate.from(currentSlot.getTime());
            if (previousSlotList != null) {
                if (currentDay.equals(previousDay)) {
                    previousSlotList.add(currentSlot);
                } else {
                    schedule.add(new ScheduleDay(previousDay, previousSlotList));
                    previousSlotList = null;
                }
            }

            if (previousSlotList == null) {
                previousDay = currentDay;
                previousSlotList = new ArrayList<>();
                previousSlotList.add(currentSlot);
            }
        }

        if (previousSlotList != null) {
            schedule.add(new ScheduleDay(previousDay, previousSlotList));
        }
        return schedule;
    }
}
