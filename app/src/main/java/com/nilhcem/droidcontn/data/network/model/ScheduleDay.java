package com.nilhcem.droidcontn.data.network.model;

import java.util.List;

import lombok.Value;

@Value
public class ScheduleDay {

    int dayId;
    String day;
    List<Slot> slots;
}
