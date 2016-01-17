package com.nilhcem.droidcontn.ui.speakers.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.ui.BaseFragment;

import lombok.val;

public class SpeakersListFragment extends BaseFragment<SpeakersListPresenter> implements SpeakersListView {

    public static SpeakersListFragment newInstance() {
        return new SpeakersListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        val view = new FrameLayout(getContext());
        view.setBackgroundColor(Color.GREEN);
        return view;
    }

    @Override
    protected SpeakersListPresenter newPresenter() {
        DroidconApp.get(getContext()).component().inject(this);
        return null;
    }
}
