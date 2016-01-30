package com.nilhcem.droidcontn.data.network.model;

import java.util.List;

import lombok.Value;

@Value
public class Session {

    int roomId;
    int sessionId;
    List<Integer> speakersId;
    String title;
    String description;
}
