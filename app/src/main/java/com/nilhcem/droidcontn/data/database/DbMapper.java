package com.nilhcem.droidcontn.data.database;

import android.support.annotation.NonNull;

import com.nilhcem.droidcontn.data.database.model.Speaker;

import java.util.ArrayList;
import java.util.List;

public class DbMapper {

    public static List<Speaker> fromNetworkSpeakers(@NonNull List<com.nilhcem.droidcontn.data.network.model.Speaker> from) {
        List<Speaker> speakers = new ArrayList<>(from.size());
        for (com.nilhcem.droidcontn.data.network.model.Speaker speaker : from) {
            speakers.add(new Speaker(speaker.getId(), speaker.getName(), speaker.getTitle(),
                    speaker.getBio(), speaker.getWebsite(), speaker.getTwitter(),
                    speaker.getGithub(), speaker.getPhoto()));
        }
        return speakers;
    }

    public static List<com.nilhcem.droidcontn.data.network.model.Speaker> toNetworkSpeaker(@NonNull List<Speaker> from) {
        List<com.nilhcem.droidcontn.data.network.model.Speaker> speakers = new ArrayList<>(from.size());
        for (Speaker speaker : from) {
            speakers.add(new com.nilhcem.droidcontn.data.network.model.Speaker(speaker.id,
                    speaker.name, speaker.title, speaker.bio, speaker.website, speaker.twitter,
                    speaker.github, speaker.photo));
        }
        return speakers;
    }
}
