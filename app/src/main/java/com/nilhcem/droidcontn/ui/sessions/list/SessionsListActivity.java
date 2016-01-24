package com.nilhcem.droidcontn.ui.sessions.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Slot;
import com.nilhcem.droidcontn.ui.BaseActivity;
import com.nilhcem.droidcontn.ui.sessions.detail.SessionDetailActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;

public class SessionsListActivity extends BaseActivity<SessionsListPresenter> implements SessionsListView {

    private static final String EXTRA_DAY = "day";
    private static final String EXTRA_SLOT = "slot";

    @Inject Picasso picasso;

    @Bind(R.id.sessions_list_recyclerview) RecyclerView recyclerView;

    private String day;
    private Slot slot;

    public static Intent createIntent(@NonNull Context context, @NonNull String day, @NonNull Slot slot) {
        return new Intent(context, SessionsListActivity.class)
                .putExtra(EXTRA_DAY, day)
                .putExtra(EXTRA_SLOT, slot);
    }

    @Override
    protected SessionsListPresenter newPresenter() {
        return new SessionsListPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sessions_list);
        DroidconApp.get(this).component().inject(this);

        day = getIntent().getStringExtra(EXTRA_DAY);
        slot = getIntent().getParcelableExtra(EXTRA_SLOT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.schedule_browse_slot, day, slot.getFromTime()));

        SessionsListAdapter adapter = new SessionsListAdapter(slot.getSessions(), picasso, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void startSessionDetail(Session session) {
        startActivity(SessionDetailActivity.createIntent(this, session));
    }
}
