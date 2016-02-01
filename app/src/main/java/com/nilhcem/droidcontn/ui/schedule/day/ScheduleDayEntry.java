package com.nilhcem.droidcontn.ui.schedule.day;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Slot;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.ui.core.picasso.CircleTransformation;
import com.nilhcem.droidcontn.ui.core.recyclerview.BaseViewHolder;
import com.nilhcem.droidcontn.utils.Views;
import com.squareup.picasso.Picasso;

import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import butterknife.Bind;

public class ScheduleDayEntry extends BaseViewHolder {

    public interface OnSessionClickListener {
        void onFreeSlotClicked(Slot slot);

        void onSelectedSessionClicked(Session session);
    }

    @Bind(R.id.schedule_day_entry_time) TextView time;
    @Bind(R.id.schedule_day_entry_slot_container) FrameLayout slotContainer;
    @Bind(R.id.schedule_day_entry_slot_image) ImageView slotImage;
    @Bind(R.id.schedule_day_entry_slot_name) TextView slotName;
    @Bind(R.id.schedule_day_entry_slot_desc) TextView slotDesc;

    private final Drawable selectableItemBackground;
    private final Picasso picasso;
    private final OnSessionClickListener listener;

    public ScheduleDayEntry(ViewGroup parent, Picasso picasso, OnSessionClickListener listener) {
        super(parent, R.layout.schedule_day_entry);
        this.picasso = picasso;
        this.listener = listener;

        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        TypedArray ta = itemView.getContext().obtainStyledAttributes(attrs);
        selectableItemBackground = ta.getDrawable(0);
        ta.recycle();
    }

    public void bindFreeSlot(Slot slot) {
        slotName.setText(R.string.schedule_browse_sessions);
        slotName.setTextColor(ContextCompat.getColor(slotName.getContext(), R.color.primary));
        slotDesc.setVisibility(View.GONE);

        Views.setBackground(slotContainer, R.drawable.schedule_day_entry_free);
        slotContainer.setForeground(selectableItemBackground);
        slotContainer.setOnClickListener(v -> listener.onFreeSlotClicked(slot));

        slotImage.setVisibility(View.GONE);
        bindTime(slot);
    }

    public void bindBreakSlot(Slot slot, Session session) {
        slotName.setText(session.getTitle());
        slotName.setTextColor(ContextCompat.getColor(slotName.getContext(), R.color.primary_text));
        slotDesc.setVisibility(View.GONE);

        Views.setBackground(slotContainer, R.drawable.schedule_day_entry_break);
        slotContainer.setForeground(null);
        slotContainer.setOnClickListener(null);

        slotImage.setVisibility(View.GONE);
        bindTime(slot);
    }

    public void bindSelectedSession(Slot slot, Session session) {
        slotName.setText(session.getTitle());
        slotName.setTextColor(ContextCompat.getColor(slotName.getContext(), R.color.primary_text));
        bindDescription(session);

        Views.setBackground(slotContainer, R.drawable.schedule_day_entry_free);
        slotContainer.setForeground(selectableItemBackground);
        slotContainer.setOnClickListener(v -> listener.onSelectedSessionClicked(session));

        picasso.load(session.getSpeakers().get(0).getPhoto()).transform(new CircleTransformation()).into(slotImage);
        slotImage.setVisibility(View.VISIBLE);
        bindTime(slot);
    }

    private void bindDescription(Session session) {
        StringBuilder sb = new StringBuilder();
        sb.append(session.getRoom()).append("\n");
        String separator = null;

        boolean addSeparator = false;
        for (Speaker speaker : session.getSpeakers()) {
            if (addSeparator) {
                if (separator == null) {
                    separator = itemView.getContext().getString(R.string.schedule_speakers_separator);
                }
                sb.append(separator);
            } else {
                addSeparator = true;
            }
            sb.append(speaker.getName());
        }

        slotDesc.setText(sb.toString());
        slotDesc.setVisibility(View.VISIBLE);
    }

    private void bindTime(Slot slot) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        String timeStr = slot.getFromTime().format(formatter);
        time.setText(timeStr);
    }
}
