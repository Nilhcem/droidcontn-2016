package com.nilhcem.droidcontn.ui.schedule.pager;

import com.nilhcem.droidcontn.data.model.ScheduleDay;

import java.util.List;

public interface SchedulePagerView {

    void displaySchedule(List<ScheduleDay> scheduleDays);
}
