package com.nilhcem.droidcontn.ui.schedule.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nilhcem.droidcontn.data.provider.DataProvider;
import com.nilhcem.droidcontn.ui.BaseFragmentPresenter;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SchedulePagerPresenter extends BaseFragmentPresenter<SchedulePagerView> {

    private final DataProvider dataProvider;
    private Subscription mScheduleDaysSubscription;

    public SchedulePagerPresenter(SchedulePagerView view, DataProvider dataProvider) {
        super(view);
        this.dataProvider = dataProvider;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mScheduleDaysSubscription = dataProvider.getScheduleDays()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.view::displaySchedule);
    }

    @Override
    public void onDestroyView() {
        mScheduleDaysSubscription.unsubscribe();
        super.onDestroyView();
    }
}

