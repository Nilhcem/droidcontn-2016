package com.nilhcem.droidcontn.ui.schedule.day;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.data.model.Slot;

import java.util.List;

public class ScheduleDayAdapter extends RecyclerView.Adapter<ScheduleDayEntry> {

    private final List<Slot> slots;
    private final ScheduleDayEntry.OnSessionClickListener listener;

    public ScheduleDayAdapter(List<Slot> slots, ScheduleDayEntry.OnSessionClickListener listener) {
        this.slots = slots;
        this.listener = listener;
    }

    @Override
    public ScheduleDayEntry onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleDayEntry(parent, listener);
    }

    @Override
    public void onBindViewHolder(ScheduleDayEntry holder, int position) {
        holder.bindSlot(slots.get(position));
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }
}
