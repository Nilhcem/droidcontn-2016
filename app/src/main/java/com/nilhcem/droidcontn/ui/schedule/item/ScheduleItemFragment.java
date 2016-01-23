package com.nilhcem.droidcontn.ui.schedule.item;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nilhcem.droidcontn.data.model.SessionDay;
import com.nilhcem.droidcontn.ui.BaseFragment;

import lombok.val;

public class ScheduleItemFragment extends BaseFragment<ScheduleItemPresenter> implements ScheduleItemView {

    private static final String ARG_DAY = "day";

    private SessionDay day;

    private static boolean sTemporary;

    public static ScheduleItemFragment newInstance(SessionDay sessionDay) {
        Bundle args = new Bundle();
        args.putString(ARG_DAY, sessionDay.name());
        ScheduleItemFragment fragment = new ScheduleItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = SessionDay.valueOf(getArguments().getString(ARG_DAY));
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
    protected ScheduleItemPresenter newPresenter() {
        return new ScheduleItemPresenter(this);
    }
}
