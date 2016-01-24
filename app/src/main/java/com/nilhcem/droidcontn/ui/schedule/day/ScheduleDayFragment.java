package com.nilhcem.droidcontn.ui.schedule.day;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.ScheduleDay;
import com.nilhcem.droidcontn.data.app.model.Slot;
import com.nilhcem.droidcontn.ui.BaseFragment;
import com.nilhcem.droidcontn.ui.sessions.list.SessionsListActivity;

import butterknife.Bind;

public class ScheduleDayFragment extends BaseFragment<ScheduleDayPresenter> implements ScheduleDayView, ScheduleDayEntry.OnSessionClickListener {

    private static final String ARG_SCHEDULE_DAY = "scheduleDay";

    @Bind(R.id.schedule_day_recyclerview) RecyclerView recyclerView;

    private ScheduleDay scheduleDay;

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_day, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ScheduleDayAdapter adapter = new ScheduleDayAdapter(scheduleDay.getSlots(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected ScheduleDayPresenter newPresenter() {
        return new ScheduleDayPresenter(this);
    }

    @Override
    public void onFreeSlotClicked(Slot slot) {
        startActivity(SessionsListActivity.createIntent(getContext(), scheduleDay.getDay(), slot));
    }
}
