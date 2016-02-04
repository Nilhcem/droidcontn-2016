package com.nilhcem.droidcontn.ui.schedule.day;

import com.nilhcem.droidcontn.data.app.model.ScheduleSlot;

import java.util.List;

public interface ScheduleDayView {

    void initSlotsList(List<ScheduleSlot> slots);

    void refreshSlotsList();
}
