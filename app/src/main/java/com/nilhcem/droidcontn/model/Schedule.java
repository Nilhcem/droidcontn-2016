package com.nilhcem.droidcontn.model;

import java.util.List;

import lombok.Value;

@Value
public class Schedule {

    int id;
    int dayId;
    String fromTime;
    String toTime;
    List<Session> sessions;
}
