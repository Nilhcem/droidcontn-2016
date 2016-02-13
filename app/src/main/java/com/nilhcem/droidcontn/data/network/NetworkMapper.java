package com.nilhcem.droidcontn.data.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nilhcem.droidcontn.data.app.AppMapper;
import com.nilhcem.droidcontn.data.app.model.Room;
import com.nilhcem.droidcontn.data.network.model.Session;
import com.nilhcem.droidcontn.data.network.model.Speaker;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import java8.util.stream.Collectors;

import static java8.util.stream.StreamSupport.stream;

public class NetworkMapper {

    private final AppMapper appMapper;

    @Inject
    public NetworkMapper(AppMapper appMapper) {
        this.appMapper = appMapper;
    }

    public List<com.nilhcem.droidcontn.data.app.model.Speaker> toAppSpeakers(@Nullable List<Speaker> from) {
        if (from == null) {
            return null;
        }

        return stream(from).map(speaker -> new com.nilhcem.droidcontn.data.app.model.Speaker(
                speaker.getId(), speaker.getName(), speaker.getTitle(),
                speaker.getBio(), speaker.getWebsite(), speaker.getTwitter(),
                speaker.getGithub(), speaker.getPhoto())
        ).collect(Collectors.toList());
    }

    public List<com.nilhcem.droidcontn.data.app.model.Session> toAppSessions(@Nullable List<Session> from, @NonNull Map<Integer, com.nilhcem.droidcontn.data.app.model.Speaker> speakersMap) {
        if (from == null) {
            return null;
        }

        return stream(from).map(session -> new com.nilhcem.droidcontn.data.app.model.Session(session.getId(),
                Room.getFromId(session.getRoomId()).name,
                appMapper.toSpeakersList(session.getSpeakersId(), speakersMap),
                session.getTitle(), session.getDescription(),
                session.getStartAt(), session.getStartAt().plusMinutes(session.getDuration()))
        ).collect(Collectors.toList());
    }
}
