package com.nilhcem.droidcontn.data.network.model;

import org.threeten.bp.LocalDate;

import java.util.List;

import lombok.Value;

@Value
public class ScheduleDay {

    int dayId;
    LocalDate day;
    List<Slot> slots;
}
