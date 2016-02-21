package com.nilhcem.droidcontn.ui.sessions.list;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.ui.core.picasso.CircleTransformation;
import com.nilhcem.droidcontn.ui.core.recyclerview.BaseViewHolder;
import com.nilhcem.droidcontn.utils.App;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class SessionsListEntry extends BaseViewHolder {

    @Bind(R.id.sessions_list_entry_photo) ImageView photo;
    @Bind(R.id.sessions_list_entry_title) TextView title;
    @Bind(R.id.sessions_list_entry_selected_state) ImageView selectedState;
    @Bind(R.id.sessions_list_entry_room) TextView room;
    @Bind(R.id.sessions_list_entry_description) TextView description;

    private final Picasso picasso;

    public SessionsListEntry(ViewGroup parent, Picasso picasso) {
        super(parent, R.layout.sessions_list_entry);
        this.picasso = picasso;
    }

    public void bindSession(Session session, boolean isSelected) {
        String url = App.getPhotoUrl(session);
        if (!TextUtils.isEmpty(url)) {
            picasso.load(url).transform(new CircleTransformation()).into(photo);
        }

        title.setText(session.getTitle());
        room.setText(session.getRoom());
        description.setText(session.getDescription());

        int selectedRes = isSelected ? R.drawable.sessions_list_entry_selected : R.drawable.sessions_list_entry_default;
        selectedState.setImageDrawable(ContextCompat.getDrawable(selectedState.getContext(), selectedRes));
    }
}
