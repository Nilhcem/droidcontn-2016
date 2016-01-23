package com.nilhcem.droidcontn.ui.schedule.day;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.data.model.Slot;

import java.util.List;

public class ScheduleDayAdapter extends RecyclerView.Adapter<ScheduleDayEntry> {

    private final List<Slot> slots;
    private final ScheduleDayView view;

    public ScheduleDayAdapter(List<Slot> slots, ScheduleDayView view) {
        this.slots = slots;
        this.view = view;
    }

    @Override
    public ScheduleDayEntry onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleDayEntry(parent);
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
