package com.nilhcem.droidcontn.data.network.model;

import java.util.List;

import lombok.Value;

@Value
public class Slot {

    int id;
    String fromTime;
    String toTime;
    List<Session> sessions;
}
