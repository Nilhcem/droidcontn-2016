package com.nilhcem.droidcontn.data.app;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nilhcem.droidcontn.data.app.model.Room;
import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.data.app.model.ScheduleDay;
import com.nilhcem.droidcontn.data.app.model.ScheduleSlot;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.utils.Preconditions;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppMapper {

    private AppMapper() {
        throw new UnsupportedOperationException();
    }

    public static Map<Integer, Speaker> speakersToMap(@NonNull List<com.nilhcem.droidcontn.data.network.model.Speaker> from) {
        List<Speaker> speakers = mapSpeakers(from);
        Map<Integer, Speaker> map = new HashMap<>(speakers.size());
        for (Speaker speaker : speakers) {
            map.put(speaker.getId(), speaker);
        }
        return map;
    }

    public static List<Speaker> mapSpeakers(@NonNull List<com.nilhcem.droidcontn.data.network.model.Speaker> from) {
        List<Speaker> speakers = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Speaker speaker : from) {
            speakers.add(new Speaker(speaker.getId(), speaker.getName(), speaker.getTitle(),
                    speaker.getBio(), speaker.getWebsite(), speaker.getTwitter(),
                    speaker.getGithub(), speaker.getPhoto()));
        }
        return speakers;
    }

    public static Schedule toSchedule(@NonNull List<com.nilhcem.droidcontn.data.network.model.Session> fromSession, @NonNull Map<Integer, Speaker> speakersMap) {
        // Map and sort Session per start date
        Preconditions.checkArgument(!fromSession.isEmpty());
        List<Session> sessions = mapSessions(fromSession, speakersMap);
        Collections.sort(sessions, (lhs, rhs) -> lhs.getFromTime().compareTo(rhs.getFromTime()));

        // Gather Sessions per ScheduleSlot
        List<ScheduleSlot> scheduleSlots = mapToScheduleSlots(sessions);

        // Gather ScheduleSlots per ScheduleDays
        return mapToScheduleDays(scheduleSlots);
    }

    private static List<Session> mapSessions(@NonNull List<com.nilhcem.droidcontn.data.network.model.Session> from, @NonNull Map<Integer, Speaker> speakersMap) {
        List<Session> sessions = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Session session : from) {
            sessions.add(new Session(session.getId(), Room.getFromId(session.getRoomId()).name,
                    mapSpeakerIds(session.getSpeakersId(), speakersMap),
                    session.getTitle(), session.getDescription(),
                    session.getStartAt(), session.getStartAt().plusMinutes(session.getDuration())));
        }
        return sessions;
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

    private static List<ScheduleSlot> mapToScheduleSlots(@NonNull List<Session> sortedSessions) {
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

    private static Schedule mapToScheduleDays(@NonNull List<ScheduleSlot> scheduleSlots) {
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
