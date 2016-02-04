package com.nilhcem.droidcontn.ui.sessions.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.ScheduleSlot;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.ui.BaseActivity;
import com.nilhcem.droidcontn.ui.sessions.details.SessionDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class SessionsListActivity extends BaseActivity<SessionsListPresenter> implements SessionsListView {

    private static final String EXTRA_SLOT = "slot";

    @Inject Picasso picasso;

    @Bind(R.id.sessions_list_recyclerview) RecyclerView recyclerView;

    public static Intent createIntent(@NonNull Context context, @NonNull ScheduleSlot slot) {
        return new Intent(context, SessionsListActivity.class)
                .putExtra(EXTRA_SLOT, slot);
    }

    @Override
    protected SessionsListPresenter newPresenter() {
        DroidconApp.get(this).component().inject(this);
        ScheduleSlot slot = getIntent().getParcelableExtra(EXTRA_SLOT);
        return new SessionsListPresenter(this, this, slot);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sessions_list);
    }

    @Override
    public void initToobar(String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void initSessionsList(List<Session> sessions) {
        SessionsListAdapter adapter = new SessionsListAdapter(sessions, picasso, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startSessionDetails(Session session) {
        startActivity(SessionDetailsActivity.createIntent(this, session));
    }
}
