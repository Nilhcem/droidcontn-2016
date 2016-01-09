package com.nilhcem.droidcontn.ui.schedule;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nilhcem.droidcontn.ui.BaseFragment;

import lombok.val;

public class ScheduleFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        val view = new FrameLayout(getContext());
        view.setBackgroundColor(Color.MAGENTA);
        return view;
    }
}
