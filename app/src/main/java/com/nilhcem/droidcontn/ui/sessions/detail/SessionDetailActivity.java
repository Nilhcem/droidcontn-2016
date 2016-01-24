package com.nilhcem.droidcontn.ui.sessions.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.ui.BaseActivity;

import butterknife.Bind;

public class SessionDetailActivity extends BaseActivity<SessionDetailPresenter> implements SessionDetailView {

    private static final String EXTRA_SESSION = "session";

    @Bind(R.id.session_detail_name) TextView name;

    private Session session;

    public static Intent createIntent(@NonNull Context context, @NonNull Session session) {
        return new Intent(context, SessionDetailActivity.class)
                .putExtra(EXTRA_SESSION, session);
    }

    @Override
    protected SessionDetailPresenter newPresenter() {
        return new SessionDetailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_detail);

        session = getIntent().getParcelableExtra(EXTRA_SESSION);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        name.setText(session.getTitle());
    }
}
