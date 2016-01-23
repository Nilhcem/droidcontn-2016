package com.nilhcem.droidcontn.ui.schedule.day;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.model.Session;
import com.nilhcem.droidcontn.data.model.Slot;
import com.nilhcem.droidcontn.ui.core.recyclerview.BaseViewHolder;
import com.nilhcem.droidcontn.utils.Views;

import java.util.List;

import butterknife.Bind;

public class ScheduleDayEntry extends BaseViewHolder {

    public interface OnSessionClickListener {
        void onFreeSlotClicked(Slot slot);
    }

    @Bind(R.id.schedule_day_entry_time) TextView time;
    @Bind(R.id.schedule_day_entry_slot_container) FrameLayout slotContainer;
    @Bind(R.id.schedule_day_entry_slot_name) TextView slotName;

    private final Drawable selectableItemBackground;
    private final OnSessionClickListener listener;

    public ScheduleDayEntry(ViewGroup parent, OnSessionClickListener listener) {
        super(parent, R.layout.schedule_day_entry);
        this.listener = listener;

        int[] attrs = new int[]{android.R.attr.selectableItemBackground};
        TypedArray ta = itemView.getContext().obtainStyledAttributes(attrs);
        selectableItemBackground = ta.getDrawable(0);
        ta.recycle();
    }

    public void bindSlot(Slot slot) {
        List<Session> sessions = slot.getSessions();
        if (sessions.size() == 1) {
            Session session = sessions.get(0);
            if (session.getSpeakersId() == null) {
                bindBreakSlot(session);
            } else {
                bindChosenSlot(session);
            }
        } else {
            bindFreeSlot(slot);
        }
        time.setText(slot.getFromTime());
    }

    private void bindFreeSlot(Slot slot) {
        slotName.setText(R.string.schedule_browse_sessions);
        slotName.setTextColor(ContextCompat.getColor(slotName.getContext(), R.color.primary));

        Views.setBackground(slotContainer, R.drawable.schedule_day_entry_free);
        slotContainer.setForeground(selectableItemBackground);
        slotContainer.setOnClickListener(v -> listener.onFreeSlotClicked(slot));
    }

    private void bindBreakSlot(Session session) {
        slotName.setText(session.getTitle());
        slotName.setTextColor(ContextCompat.getColor(slotName.getContext(), R.color.primary_text));

        Views.setBackground(slotContainer, R.drawable.schedule_day_entry_break);
        slotContainer.setForeground(null);
        slotContainer.setOnClickListener(null);
    }

    private void bindChosenSlot(Session session) {
        // TODO
        bindBreakSlot(session);
    }
}
