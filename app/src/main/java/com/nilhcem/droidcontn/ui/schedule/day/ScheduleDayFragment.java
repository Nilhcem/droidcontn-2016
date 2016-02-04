package com.nilhcem.droidcontn.ui.schedule.day;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.ScheduleDay;
import com.nilhcem.droidcontn.data.app.model.ScheduleSlot;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.database.dao.SelectedSessionsDao;
import com.nilhcem.droidcontn.ui.BaseFragment;
import com.nilhcem.droidcontn.ui.sessions.details.SessionDetailsActivity;
import com.nilhcem.droidcontn.ui.sessions.list.SessionsListActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class ScheduleDayFragment extends BaseFragment<ScheduleDayPresenter> implements ScheduleDayView, ScheduleDayEntry.OnSessionClickListener {

    private static final String ARG_SCHEDULE_DAY = "scheduleDay";

    @Inject Picasso picasso;
    @Inject SelectedSessionsDao dao;

    @Bind(R.id.schedule_day_recyclerview) RecyclerView recyclerView;

    private ScheduleDayAdapter adapter;

    public static ScheduleDayFragment newInstance(ScheduleDay scheduleDay) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_SCHEDULE_DAY, scheduleDay);
        ScheduleDayFragment fragment = new ScheduleDayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ScheduleDayPresenter newPresenter() {
        DroidconApp.get(getContext()).component().inject(this);
        ScheduleDay scheduleDay = getArguments().getParcelable(ARG_SCHEDULE_DAY);
        return new ScheduleDayPresenter(this, scheduleDay);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_day, container, false);
    }

    @Override
    public void initSlotsList(List<ScheduleSlot> slots) {
        adapter = new ScheduleDayAdapter(slots, dao, picasso, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshSlotsList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFreeSlotClicked(ScheduleSlot slot) {
        startActivity(SessionsListActivity.createIntent(getContext(), slot));
    }

    @Override
    public void onSelectedSessionClicked(Session session) {
        startActivity(SessionDetailsActivity.createIntent(getContext(), session));
    }
}
