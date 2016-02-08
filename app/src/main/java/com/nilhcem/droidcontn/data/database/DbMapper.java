package com.nilhcem.droidcontn.data.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nilhcem.droidcontn.core.moshi.LocalDateTimeAdapter;
import com.nilhcem.droidcontn.data.app.model.Room;
import com.nilhcem.droidcontn.data.database.model.Session;
import com.nilhcem.droidcontn.data.database.model.Speaker;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.threeten.bp.LocalDateTime;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

public class DbMapper {

    private final LocalDateTimeAdapter localDateTimeAdapter;
    private final JsonAdapter<List<Integer>> intListAdapter;

    @Inject
    public DbMapper(Moshi moshi, LocalDateTimeAdapter localDateTimeAdapter) {
        this.localDateTimeAdapter = localDateTimeAdapter;
        this.intListAdapter = moshi.adapter(Types.newParameterizedType(List.class, Integer.class));
    }

    public List<Speaker> fromNetworkSpeakers(@NonNull List<com.nilhcem.droidcontn.data.network.model.Speaker> from) {
        List<Speaker> speakers = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Speaker speaker : from) {
            speakers.add(new Speaker(speaker.getId(), speaker.getName(), speaker.getTitle(),
                    speaker.getBio(), speaker.getWebsite(), speaker.getTwitter(),
                    speaker.getGithub(), speaker.getPhoto()));
        }
        return speakers;
    }

    public List<com.nilhcem.droidcontn.data.network.model.Speaker> toNetworkSpeaker(@NonNull List<Speaker> from) {
        List<com.nilhcem.droidcontn.data.network.model.Speaker> speakers = new ArrayList<>(from.size());
        for (Speaker speaker : from) {
            speakers.add(new com.nilhcem.droidcontn.data.network.model.Speaker(speaker.id,
                    speaker.name, speaker.title, speaker.bio, speaker.website, speaker.twitter,
                    speaker.github, speaker.photo));
        }
        return speakers;
    }

    public List<Session> fromNetworkSessions(@NonNull List<com.nilhcem.droidcontn.data.network.model.Session> from) {
        List<Session> sessions = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Session session : from) {
            sessions.add(new Session(session.getId(), localDateTimeAdapter.toText(session.getStartAt()),
                    session.getDuration(), session.getRoomId(), serialize(session.getSpeakersId()),
                    session.getTitle(), session.getDescription()));
        }
        return sessions;
    }

    public List<com.nilhcem.droidcontn.data.network.model.Session> toNetworkSession(@NonNull List<Session> from) {
        List<com.nilhcem.droidcontn.data.network.model.Session> sessions = new ArrayList<>(from.size());

        for (Session session : from) {
            sessions.add(new com.nilhcem.droidcontn.data.network.model.Session(session.id,
                    localDateTimeAdapter.fromText(session.startAt), session.duration, session.roomId,
                    deserialize(session.speakersIds), session.title, session.description));
        }
        return sessions;
    }

    private String serialize(@Nullable List<Integer> toSerialize) {
        String result = null;
        if (toSerialize != null) {
            result = intListAdapter.toJson(toSerialize);
        }
        return result;
    }

    private List<Integer> deserialize(@Nullable String toDeserialize) {
        List<Integer> result = null;
        if (toDeserialize != null) {
            try {
                result = intListAdapter.fromJson(toDeserialize);
            } catch (IOException e) {
                Timber.e(e, "Error getting speakersIds for String: %s", toDeserialize);
            }
        }
        return result;
    }

    public List<com.nilhcem.droidcontn.data.app.model.Session> toAppSessions(@NonNull List<Session> from, @NonNull Map<Integer, com.nilhcem.droidcontn.data.app.model.Speaker> speakersMap) {
        List<com.nilhcem.droidcontn.data.app.model.Session> sessions = new ArrayList<>(from.size());
        for (Session session : from) {
            LocalDateTime fromTime = localDateTimeAdapter.fromText(session.startAt);
            sessions.add(new com.nilhcem.droidcontn.data.app.model.Session(
                    session.id, Room.getFromId(session.roomId).name, getSpeakersList(deserialize(session.speakersIds), speakersMap),
                    session.title, session.description, fromTime, fromTime.plusMinutes(session.duration)));
        }
        return sessions;
    }

    private static List<com.nilhcem.droidcontn.data.app.model.Speaker> getSpeakersList(@Nullable List<Integer> from, @NonNull Map<Integer, com.nilhcem.droidcontn.data.app.model.Speaker> speakersMap) {
        List<com.nilhcem.droidcontn.data.app.model.Speaker> speakers = null;
        if (from != null) {
            speakers = new ArrayList<>(from.size());
            for (Integer speakerId : from) {
                speakers.add(speakersMap.get(speakerId));
            }
        }
        return speakers;
    }

    public List<com.nilhcem.droidcontn.data.app.model.Speaker> toAppSpeakers(@NonNull List<Speaker> from) {
        List<com.nilhcem.droidcontn.data.app.model.Speaker> speakers = new ArrayList<>(from.size());
        for (Speaker speaker : from) {
            speakers.add(new com.nilhcem.droidcontn.data.app.model.Speaker(speaker.id,
                    speaker.name, speaker.title, speaker.bio, speaker.website, speaker.twitter,
                    speaker.github, speaker.photo));
        }
        return speakers;
    }
}
