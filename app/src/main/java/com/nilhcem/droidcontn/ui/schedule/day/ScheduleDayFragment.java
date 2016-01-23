package com.nilhcem.droidcontn.ui.schedule.day;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nilhcem.droidcontn.data.model.ScheduleDay;
import com.nilhcem.droidcontn.ui.BaseFragment;

import lombok.val;

public class ScheduleDayFragment extends BaseFragment<ScheduleDayPresenter> implements ScheduleDayView {

    private static final String ARG_SCHEDULE_DAY = "scheduleDay";

    private ScheduleDay scheduleDay;

    private static boolean sTemporary;

    public static ScheduleDayFragment newInstance(ScheduleDay scheduleDay) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEDULE_DAY, scheduleDay);
        ScheduleDayFragment fragment = new ScheduleDayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scheduleDay = getArguments().getParcelable(ARG_SCHEDULE_DAY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        val view = new FrameLayout(getContext());
        view.setBackgroundColor(sTemporary ? Color.MAGENTA : Color.GREEN);
        sTemporary = !sTemporary;
        return view;
    }

    @Override
    protected ScheduleDayPresenter newPresenter() {
        return new ScheduleDayPresenter(this);
    }
}
