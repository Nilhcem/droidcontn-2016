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

    @Inject Picasso picasso;

    @Bind(R.id.speakers_detail_name) TextView name;
    @Bind(R.id.speakers_detail_title) TextView title;
    @Bind(R.id.speakers_detail_bio) TextView bio;
    @Bind(R.id.speakers_detail_photo) ImageView photo;
    @Bind(R.id.speakers_detail_links_container) ViewGroup linksContainer;
    @Bind(R.id.speakers_detail_twitter) ImageView twitter;
    @Bind(R.id.speakers_detail_github) ImageView github;
    @Bind(R.id.speakers_detail_website) ImageView website;

    private static final String EXTRA_SPEAKER = "speaker";

    public static void show(@NonNull Speaker speaker, @NonNull FragmentManager fm) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_SPEAKER, speaker);
        SpeakerDetailDialogFragment fragment = new SpeakerDetailDialogFragment();
        fragment.setArguments(args);
        fragment.show(fm, SpeakerDetailDialogFragment.class.getSimpleName());
    }

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
        name.setText(speaker.getName());
        title.setText(speaker.getTitle());
        bio.setText(speaker.getBio());
        picasso.load(speaker.getPhoto()).transform(new CircleTransformation()).into(photo);
        bindLinks(speaker);
    }

    private void bindLinks(Speaker speaker) {
        boolean hasLink = false;

        if (!TextUtils.isEmpty(speaker.getTwitter())) {
            twitter.setVisibility(View.VISIBLE);
            twitter.setOnClickListener(v -> openLink("https://www.twitter.com/#!/" + speaker.getTwitter()));
            hasLink = true;
        }

        if (!TextUtils.isEmpty(speaker.getGithub())) {
            github.setVisibility(View.VISIBLE);
            github.setOnClickListener(v -> openLink("https://www.github.com/" + speaker.getGithub()));
            hasLink = true;
        }

        if (!TextUtils.isEmpty(speaker.getWebsite())) {
            website.setVisibility(View.VISIBLE);
            website.setOnClickListener(v -> openLink(speaker.getWebsite()));
            hasLink = true;
        }

        linksContainer.setVisibility(hasLink ? View.VISIBLE : View.GONE);
    }

    private void openLink(String url) {
        Intents.startExternalUrl(getContext(), url);
    }
}
