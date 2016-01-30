package com.nilhcem.droidcontn.ui.sessions.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.data.database.dao.SelectedSessionsDao;
import com.nilhcem.droidcontn.ui.BaseActivity;
import com.nilhcem.droidcontn.ui.speakers.details.SpeakerDetailsDialogFragment;
import com.nilhcem.droidcontn.utils.Views;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class SessionDetailsActivity extends BaseActivity<SessionDetailsPresenter> implements SessionDetailsView {

    private static final String EXTRA_SESSION = "session";

    @Inject Picasso picasso;
    @Inject SelectedSessionsDao selectedSessionsDao;

    @Bind(R.id.session_details_layout) View layout;
    @Bind(R.id.session_details_toolbar) Toolbar toolbar;
    @Bind(R.id.session_details_toolbar_layout) CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.session_details_photo) ImageView photo;
    @Bind(R.id.session_details_header) ViewGroup header;
    @Bind(R.id.session_details_title) TextView title;
    @Bind(R.id.session_details_subtitle) TextView subtitle;
    @Bind(R.id.session_details_description) TextView description;
    @Bind(R.id.session_details_speakers_title) TextView speakersTitle;
    @Bind(R.id.session_details_speakers_container) ViewGroup speakersContainer;

    private Session session;

    public static Intent createIntent(@NonNull Context context, @NonNull Session session) {
        return new Intent(context, SessionDetailsActivity.class)
                .putExtra(EXTRA_SESSION, session);
    }

    @Override
    protected SessionDetailsPresenter newPresenter() {
        return new SessionDetailsPresenter(this, selectedSessionsDao);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_details);
        DroidconApp.get(this).component().inject(this);

        session = getIntent().getParcelableExtra(EXTRA_SESSION);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        title.setText(session.getTitle());
        subtitle.setText("May 29, 2015, 10:00 - 11:00 AM\nRoom 1 (L2)");
        description.setText(session.getDescription());

        header.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = header.getHeight();
                if (height != 0) {
                    Views.removeOnGlobalLayoutListener(header.getViewTreeObserver(), this);
                    int toolbarHeight = height + Views.getActionBarSize(SessionDetailsActivity.this);
                    toolbar.getLayoutParams().height = toolbarHeight;
                    toolbar.requestLayout();
                    toolbarLayout.getLayoutParams().height = Math.round(2.25f * (toolbarHeight));
                    toolbarLayout.requestLayout();
                    setHeaderPhoto(header.getWidth());
                }
            }
        });

        List<Speaker> speakers = session.getSpeakers();
        if (speakers != null && !speakers.isEmpty()) {
            speakersTitle.setText(getResources().getQuantityString(R.plurals.session_details_speakers, speakers.size()));
            for (Speaker speaker : speakers) {
                SessionDetailsSpeaker view = new SessionDetailsSpeaker(this, speaker, picasso);
                view.setOnClickListener(v -> openSpeakerDetails(speaker));
                speakersContainer.addView(view);
            }
        }
    }

    @OnClick(R.id.session_details_fab)
    void onFloatingActionButtonClicked() {
        if (selectedSessionsDao.isSelected(session)) {
            selectedSessionsDao.unselect(session);
            Snackbar.make(layout, R.string.session_details_removed, Snackbar.LENGTH_SHORT).show();
        } else {
            selectedSessionsDao.select(session);
            Snackbar.make(layout, R.string.session_details_added, Snackbar.LENGTH_SHORT).show();
        }
    }

    private void setHeaderPhoto(int headerWidth) {
        List<Speaker> speakers = session.getSpeakers();
        if (speakers != null && !speakers.isEmpty()) {
            picasso.load(speakers.get(0).getPhoto()).resize(headerWidth, 0).into(photo);
        }
    }

    private void openSpeakerDetails(Speaker speaker) {
        SpeakerDetailsDialogFragment.show(speaker, getSupportFragmentManager());
    }
}