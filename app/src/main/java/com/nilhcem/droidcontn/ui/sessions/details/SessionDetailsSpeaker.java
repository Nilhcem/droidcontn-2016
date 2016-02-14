package com.nilhcem.droidcontn.ui.sessions.details;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.ui.core.picasso.CircleTransformation;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SessionDetailsSpeaker extends FrameLayout {

    @Bind(R.id.session_details_speaker_photo) ImageView photo;
    @Bind(R.id.session_details_speaker_name) TextView name;
    @Bind(R.id.session_details_speaker_title) TextView title;

    public SessionDetailsSpeaker(Context context, Speaker speaker, Picasso picasso) {
        super(context);

        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        TypedArray ta = context.obtainStyledAttributes(attrs);
        setForeground(ta.getDrawable(0));
        ta.recycle();

        LayoutInflater.from(context).inflate(R.layout.session_details_speaker, this);
        ButterKnife.bind(this, this);
        bind(speaker, picasso);
    }

    private void bind(Speaker speaker, Picasso picasso) {
        if (!TextUtils.isEmpty(speaker.getPhoto())) {
            picasso.load(speaker.getPhoto()).transform(new CircleTransformation()).into(photo);
        }
        name.setText(speaker.getName());
        title.setText(speaker.getTitle());
    }
}
