package com.nilhcem.droidcontn.data.network.model;

import org.threeten.bp.LocalTime;

import java.util.List;

import lombok.Value;

@Value
public class Slot {

    int id;
    LocalTime fromTime;
    LocalTime toTime;
    List<Session> sessions;
}
