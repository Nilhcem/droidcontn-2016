package com.nilhcem.droidcontn.ui.speakers.list;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.data.model.Speaker;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpeakersListAdapter extends RecyclerView.Adapter<SpeakersListEntry> {

    private final List<Speaker> mSpeakers;
    private final Picasso mPicasso;

    public SpeakersListAdapter(List<Speaker> speakers, Picasso picasso) {
        mSpeakers = speakers;
        mPicasso = picasso;
    }

    @Override
    public SpeakersListEntry onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpeakersListEntry(parent, mPicasso);
    }

    @Override
    public void onBindViewHolder(SpeakersListEntry holder, int position) {
        holder.bindSpeaker(mSpeakers.get(position));
    }

    @Override
    public int getItemCount() {
        return mSpeakers.size();
    }
}
