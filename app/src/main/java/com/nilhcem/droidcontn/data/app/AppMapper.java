package com.nilhcem.droidcontn.data.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.data.app.model.ScheduleDay;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Slot;
import com.nilhcem.droidcontn.data.app.model.Speaker;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppMapper {

    private AppMapper() {
        throw new UnsupportedOperationException();
    }

    public static List<Speaker> mapSpeakers(@NonNull List<com.nilhcem.droidcontn.data.network.model.Speaker> from) {
        List<Speaker> speakers = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Speaker speaker : from) {
            speakers.add(mapSpeaker(speaker));
        }
        return speakers;
    }

    public static Map<Integer, Speaker> speakersToMap(@NonNull List<com.nilhcem.droidcontn.data.network.model.Speaker> from) {
        List<Speaker> speakers = mapSpeakers(from);
        Map<Integer, Speaker> map = new HashMap<>(speakers.size());
        for (Speaker speaker : speakers) {
            map.put(speaker.getId(), speaker);
        }
        return map;
    }

    public static Schedule mapSchedule(@NonNull List<com.nilhcem.droidcontn.data.network.model.ScheduleDay> scheduleDays, @NonNull Map<Integer, Speaker> speakersMap) {
        Schedule schedule = new Schedule();

        for (com.nilhcem.droidcontn.data.network.model.ScheduleDay day : scheduleDays) {
            schedule.add(mapScheduleDay(day, speakersMap));
        }
        return schedule;
    }

    private static Speaker mapSpeaker(@NonNull com.nilhcem.droidcontn.data.network.model.Speaker from) {
        return new Speaker(from.getId(), from.getName(), from.getTitle(), from.getBio(), from.getWebsite(), from.getTwitter(), from.getGithub(), from.getPhoto());
    }

    private static ScheduleDay mapScheduleDay(@NonNull com.nilhcem.droidcontn.data.network.model.ScheduleDay day, @NonNull Map<Integer, Speaker> speakersMap) {
        return new ScheduleDay(day.getDay(), mapSlots(day.getDay(), day.getSlots(), speakersMap));
    }

    private static List<Slot> mapSlots(@NonNull LocalDate date, @NonNull List<com.nilhcem.droidcontn.data.network.model.Slot> from, @NonNull Map<Integer, Speaker> speakersMap) {
        List<Slot> slots = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Slot slot : from) {
            slots.add(mapSlot(date, slot, speakersMap));
        }
        return slots;
    }

    private static Slot mapSlot(@NonNull LocalDate date, @NonNull com.nilhcem.droidcontn.data.network.model.Slot from, @NonNull Map<Integer, Speaker> speakersMap) {
        LocalDateTime fromTime = LocalDateTime.of(date, from.getFromTime());
        LocalDateTime toTime = LocalDateTime.of(date, from.getToTime());
        return new Slot(from.getId(), fromTime, toTime, mapSessions(from.getId(), from.getSessions(), speakersMap));
    }

    private static List<Session> mapSessions(int slotId, @NonNull List<com.nilhcem.droidcontn.data.network.model.Session> from, @NonNull Map<Integer, Speaker> speakersMap) {
        List<Session> sessions = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Session session : from) {
            sessions.add(mapSession(slotId, session, speakersMap));
        }
        return sessions;
    }

    private static Session mapSession(int slotId, @NonNull com.nilhcem.droidcontn.data.network.model.Session from, @NonNull Map<Integer, Speaker> speakersMap) {
        return new Session(from.getSessionId(), slotId, from.getRoomId(), mapSpeakerIds(from.getSpeakersId(), speakersMap), from.getTitle(), from.getDescription());
    }

    private static List<Speaker> mapSpeakerIds(@Nullable List<Integer> speakerIds, @NonNull Map<Integer, Speaker> speakersMap) {
        List<Speaker> speakers = null;
        if (speakerIds != null) {
            speakers = new ArrayList<>(speakerIds.size());
            for (Integer speakerId : speakerIds) {
                speakers.add(speakersMap.get(speakerId));
            }
        }
        return speakers;
    }
}
