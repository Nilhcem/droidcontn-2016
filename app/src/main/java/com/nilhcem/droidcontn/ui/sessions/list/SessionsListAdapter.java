package com.nilhcem.droidcontn.ui.sessions.list;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.data.app.model.Session;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SessionsListAdapter extends RecyclerView.Adapter<SessionsListEntry> {

    private final List<Session> sessions;
    private final Picasso picasso;
    private final SessionsListView listener;

    public SessionsListAdapter(List<Session> sessions, Picasso picasso, SessionsListView listener) {
        this.sessions = sessions;
        this.picasso = picasso;
        this.listener = listener;
    }

    @Override
    public SessionsListEntry onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SessionsListEntry(parent, picasso);
    }

    @Override
    public void onBindViewHolder(SessionsListEntry holder, int position) {
        holder.bindSession(sessions.get(position));
        holder.itemView.setOnClickListener(v -> listener.startSessionDetails(sessions.get(position)));
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }
}
