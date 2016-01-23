package com.nilhcem.droidcontn.ui.schedule.day;

import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.model.Session;
import com.nilhcem.droidcontn.data.model.Slot;
import com.nilhcem.droidcontn.ui.core.recyclerview.BaseViewHolder;
import com.nilhcem.droidcontn.utils.Views;

import java.util.List;

import butterknife.Bind;

public class ScheduleDayEntry extends BaseViewHolder {

    @Bind(R.id.schedule_day_entry_time) TextView time;
    @Bind(R.id.schedule_day_entry_desc) TextView desc;

    public ScheduleDayEntry(ViewGroup parent) {
        super(parent, R.layout.schedule_day_entry);
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
            bindFreeSlot();
        }
        time.setText(slot.getFromTime());
    }

    private void bindFreeSlot() {
        Views.setBackground(desc, R.drawable.schedule_day_entry_free);
        desc.setText(R.string.schedule_browse_sessions);
        desc.setTextColor(ContextCompat.getColor(desc.getContext(), R.color.primary));
    }

    private void bindBreakSlot(Session session) {
        Views.setBackground(desc, R.drawable.schedule_day_entry_break);
        desc.setText(session.getTitle());
        desc.setTextColor(ContextCompat.getColor(desc.getContext(), R.color.primary_text));
    }

    private void bindChosenSlot(Session session) {
        // TODO
        bindBreakSlot(session);
    }
}
