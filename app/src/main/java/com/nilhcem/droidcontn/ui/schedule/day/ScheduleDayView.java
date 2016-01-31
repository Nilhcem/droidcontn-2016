package com.nilhcem.droidcontn.ui.schedule.day;

import com.nilhcem.droidcontn.data.app.model.Slot;

import java.util.List;

public interface ScheduleDayView {

    void initSlotsList(List<Slot> slots);

    void refreshSlotsList();
}
