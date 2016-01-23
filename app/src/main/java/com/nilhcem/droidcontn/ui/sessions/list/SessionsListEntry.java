package com.nilhcem.droidcontn.ui.sessions.list;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.model.Session;
import com.nilhcem.droidcontn.ui.core.recyclerview.BaseViewHolder;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class SessionsListEntry extends BaseViewHolder {

    @Bind(R.id.sessions_list_entry_label) TextView label;

    private final Picasso picasso;

    public SessionsListEntry(ViewGroup parent, Picasso picasso) {
        super(parent, R.layout.sessions_list_entry);
        this.picasso = picasso;
    }

    public void bindSession(Session session) {
        label.setText(session.getTitle());
    }
}
