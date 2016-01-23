package com.nilhcem.droidcontn.ui.schedule.day;

import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.model.Slot;
import com.nilhcem.droidcontn.ui.core.recyclerview.BaseViewHolder;

import butterknife.Bind;

public class ScheduleDayEntry extends BaseViewHolder {

    @Bind(R.id.schedule_day_entry_text) TextView name;

    public ScheduleDayEntry(ViewGroup parent) {
        super(parent, R.layout.schedule_day_entry);
    }

    public void bindSlot(Slot slot) {
        name.setText(slot.getFromTime() + " - " + slot.getToTime());
    }
}
