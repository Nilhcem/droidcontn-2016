package com.nilhcem.droidcontn.ui.schedule.day;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.data.app.model.Slot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScheduleDayAdapter extends RecyclerView.Adapter<ScheduleDayEntry> {

    private final List<Slot> slots;
    private final Picasso picasso;
    private final ScheduleDayEntry.OnSessionClickListener listener;

    public ScheduleDayAdapter(List<Slot> slots, Picasso picasso, ScheduleDayEntry.OnSessionClickListener listener) {
        this.slots = slots;
        this.picasso = picasso;
        this.listener = listener;
    }

    @Override
    public ScheduleDayEntry onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleDayEntry(parent, picasso, listener);
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
