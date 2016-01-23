package com.nilhcem.droidcontn.ui.speakers.list;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.data.model.Speaker;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SpeakersListAdapter extends RecyclerView.Adapter<SpeakersListEntry> {

    private final List<Speaker> speakers;
    private final Picasso picasso;
    private final SpeakersListView view;

    public SpeakersListAdapter(List<Speaker> speakers, Picasso picasso, SpeakersListView view) {
        this.speakers = speakers;
        this.picasso = picasso;
        this.view = view;
    }

    @Override
    public SpeakersListEntry onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SpeakersListEntry(parent, picasso);
    }

    @Override
    public void onBindViewHolder(SpeakersListEntry holder, int position) {
        holder.bindSpeaker(speakers.get(position));
        holder.itemView.setOnClickListener(v -> view.showSpeakerDetail(speakers.get(position)));
    }

    @Override
    public int getItemCount() {
        return speakers.size();
    }
}
