package com.nilhcem.droidcontn.ui.speakers.detail;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.model.Speaker;
import com.nilhcem.droidcontn.ui.core.picasso.CircleTransformation;
import com.nilhcem.droidcontn.utils.Intents;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpeakerDetailDialogFragment extends AppCompatDialogFragment {

    private static final String EXTRA_SPEAKER = "mSpeaker";

    public static void show(@NonNull Speaker speaker, @NonNull FragmentManager fm) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_SPEAKER, speaker);
        SpeakerDetailDialogFragment fragment = new SpeakerDetailDialogFragment();
        fragment.setArguments(args);
        fragment.show(fm, SpeakerDetailDialogFragment.class.getSimpleName());
    }

    @Inject Picasso mPicasso;

    @Bind(R.id.speakers_detail_name) TextView mName;
    @Bind(R.id.speakers_detail_title) TextView mTitle;
    @Bind(R.id.speakers_detail_bio) TextView mBio;
    @Bind(R.id.speakers_detail_photo) ImageView mPhoto;
    @Bind(R.id.speakers_detail_links_container) ViewGroup mLinksContainer;
    @Bind(R.id.speakers_detail_twitter) ImageView mTwitter;
    @Bind(R.id.speakers_detail_github) ImageView mGithub;
    @Bind(R.id.speakers_detail_website) ImageView mWebsite;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DroidconApp.get(getContext()).component().inject(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.speakers_detail, null);
        ButterKnife.bind(this, view);
        bindSpeaker(getArguments().getParcelable(EXTRA_SPEAKER));
        return new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton(R.string.speakers_details_hide, (dialog, which) -> dismiss())
                .create();
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    private void bindSpeaker(Speaker speaker) {
        mName.setText(speaker.getName());
        mTitle.setText(speaker.getTitle());
        mBio.setText(speaker.getBio());
        mPicasso.load(speaker.getPhoto()).transform(new CircleTransformation()).into(mPhoto);
        bindLinks(speaker);
    }

    private void bindLinks(Speaker speaker) {
        boolean hasLink = false;

        if (!TextUtils.isEmpty(speaker.getTwitter())) {
            mTwitter.setVisibility(View.VISIBLE);
            mTwitter.setOnClickListener(v -> openLink("https://www.twitter.com/#!/" + speaker.getTwitter()));
            hasLink = true;
        }

        if (!TextUtils.isEmpty(speaker.getGithub())) {
            mGithub.setVisibility(View.VISIBLE);
            mGithub.setOnClickListener(v -> openLink("https://www.github.com/" + speaker.getGithub()));
            hasLink = true;
        }

        if (!TextUtils.isEmpty(speaker.getWebsite())) {
            mWebsite.setVisibility(View.VISIBLE);
            mWebsite.setOnClickListener(v -> openLink(speaker.getWebsite()));
            hasLink = true;
        }

        mLinksContainer.setVisibility(hasLink ? View.VISIBLE : View.GONE);
    }

    private void openLink(String url) {
        Intents.startExternalUrl(getContext(), url);
    }
}
