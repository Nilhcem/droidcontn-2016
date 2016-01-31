package com.nilhcem.droidcontn.ui.schedule.day;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nilhcem.droidcontn.data.app.model.ScheduleDay;
import com.nilhcem.droidcontn.ui.BaseFragmentPresenter;

public class ScheduleDayPresenter extends BaseFragmentPresenter<ScheduleDayView> {

    private final ScheduleDay scheduleDay;

    public ScheduleDayPresenter(ScheduleDayView view, ScheduleDay scheduleDay) {
        super(view);
        this.scheduleDay = scheduleDay;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view.initSlotsList(scheduleDay.getSlots());
    }

    @Override
    public void onResume() {
        super.onResume();
        view.refreshSlotsList();
    }

    public String getDay() {
        return scheduleDay.getDay();
    }
}
