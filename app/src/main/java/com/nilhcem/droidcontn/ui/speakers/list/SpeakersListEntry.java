package com.nilhcem.droidcontn.ui.speakers.list;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.model.Speaker;
import com.nilhcem.droidcontn.ui.core.recyclerview.BaseViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class SpeakersListEntry extends BaseViewHolder {

    @Bind(R.id.speakers_list_entry_photo) ImageView mPhoto;
    @Bind(R.id.speakers_list_entry_name) TextView mName;

    private final Picasso mPicasso;

    public SpeakersListEntry(ViewGroup parent, Picasso picasso) {
        super(parent, R.layout.speakers_list_entry);
        mPicasso = picasso;
    }

    public void bindSpeaker(Speaker speaker) {
        mPicasso.load(speaker.getPhoto()).into(mPhoto);
        mName.setText(speaker.getName());
    }
}
