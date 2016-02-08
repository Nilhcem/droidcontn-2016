package com.nilhcem.droidcontn.data.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nilhcem.droidcontn.data.app.AppMapper;
import com.nilhcem.droidcontn.data.app.model.Room;
import com.nilhcem.droidcontn.data.network.model.Session;
import com.nilhcem.droidcontn.data.network.model.Speaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class NetworkMapper {

    private final AppMapper appMapper;

    @Inject
    public NetworkMapper(AppMapper appMapper) {
        this.appMapper = appMapper;
    }

    public List<com.nilhcem.droidcontn.data.app.model.Speaker> toAppSpeakers(@Nullable List<Speaker> from) {
        List<com.nilhcem.droidcontn.data.app.model.Speaker> speakers = null;

        if (from != null) {
            speakers = new ArrayList<>(from.size());
            for (Speaker speaker : from) {
                speakers.add(new com.nilhcem.droidcontn.data.app.model.Speaker(
                        speaker.getId(), speaker.getName(), speaker.getTitle(),
                        speaker.getBio(), speaker.getWebsite(), speaker.getTwitter(),
                        speaker.getGithub(), speaker.getPhoto()));
            }
        }
        return speakers;
    }

    public List<com.nilhcem.droidcontn.data.app.model.Session> toAppSessions(@Nullable List<Session> from, @NonNull Map<Integer, com.nilhcem.droidcontn.data.app.model.Speaker> speakersMap) {
        List<com.nilhcem.droidcontn.data.app.model.Session> sessions = null;

        if (from != null) {
            sessions = new ArrayList<>(from.size());

            for (Session session : from) {
                sessions.add(new com.nilhcem.droidcontn.data.app.model.Session(session.getId(),
                        Room.getFromId(session.getRoomId()).name,
                        appMapper.toSpeakersList(session.getSpeakersId(), speakersMap),
                        session.getTitle(), session.getDescription(),
                        session.getStartAt(), session.getStartAt().plusMinutes(session.getDuration())));
            }
        }
        return sessions;
    }
}
