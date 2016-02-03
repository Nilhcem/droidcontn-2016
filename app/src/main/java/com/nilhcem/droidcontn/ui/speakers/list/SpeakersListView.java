package com.nilhcem.droidcontn.ui.speakers.list;

import com.nilhcem.droidcontn.data.app.model.Speaker;

import java.util.List;

public interface SpeakersListView {

    void displayLoadingError(Throwable error);

    void displaySpeakers(List<Speaker> speakers);

    void showSpeakerDetails(Speaker speaker);
}
