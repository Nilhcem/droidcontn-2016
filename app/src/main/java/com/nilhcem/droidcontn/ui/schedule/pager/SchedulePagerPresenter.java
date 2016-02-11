package com.nilhcem.droidcontn.ui.schedule.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nilhcem.droidcontn.data.app.DataProvider;
import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.ui.BaseFragmentPresenter;

import icepick.State;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SchedulePagerPresenter extends BaseFragmentPresenter<SchedulePagerView> {

    private final DataProvider dataProvider;
    private Subscription scheduleSubscription;

    @State Schedule schedule;

    public SchedulePagerPresenter(SchedulePagerView view, DataProvider dataProvider) {
        super(view);
        this.dataProvider = dataProvider;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (schedule == null) {
            scheduleSubscription = dataProvider.getSchedule()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(scheduleDays -> {
                        schedule = scheduleDays;
                        this.view.displaySchedule(schedule);
                    }, this.view::displayLoadingError);
        } else {
            this.view.displaySchedule(schedule);
        }
    }

    @Override
    public void onDestroyView() {
        if (scheduleSubscription != null) {
            scheduleSubscription.unsubscribe();
        }
        super.onDestroyView();
    }
}
