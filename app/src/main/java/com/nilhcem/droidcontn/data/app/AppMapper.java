package com.nilhcem.droidcontn.data.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.data.app.model.ScheduleDay;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Slot;
import com.nilhcem.droidcontn.data.app.model.Speaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppMapper {

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
        return new ScheduleDay(day.getDay(), mapSlots(day.getSlots(), speakersMap));
    }

    private static List<Slot> mapSlots(@NonNull List<com.nilhcem.droidcontn.data.network.model.Slot> from, @NonNull Map<Integer, Speaker> speakersMap) {
        List<Slot> slots = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Slot slot : from) {
            slots.add(mapSlot(slot, speakersMap));
        }
        return slots;
    }

    private static Slot mapSlot(@NonNull com.nilhcem.droidcontn.data.network.model.Slot from, @NonNull Map<Integer, Speaker> speakersMap) {
        return new Slot(from.getId(), from.getFromTime(), from.getToTime(), mapSessions(from.getSessions(), speakersMap));
    }

    private static List<Session> mapSessions(@NonNull List<com.nilhcem.droidcontn.data.network.model.Session> from, @NonNull Map<Integer, Speaker> speakersMap) {
        List<Session> sessions = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Session session : from) {
            sessions.add(mapSession(session, speakersMap));
        }
        return sessions;
    }

    private static Session mapSession(@NonNull com.nilhcem.droidcontn.data.network.model.Session from, @NonNull Map<Integer, Speaker> speakersMap) {
        return new Session(from.getRoomId(), mapSpeakerIds(from.getSpeakersId(), speakersMap), from.getTitle(), from.getDescription());
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
