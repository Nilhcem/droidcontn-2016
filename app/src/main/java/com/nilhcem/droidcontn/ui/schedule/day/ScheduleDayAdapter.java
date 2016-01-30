package com.nilhcem.droidcontn.ui.schedule.day;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Slot;
import com.nilhcem.droidcontn.data.database.dao.SelectedSessionsDao;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ScheduleDayAdapter extends RecyclerView.Adapter<ScheduleDayEntry> {

    private final List<Slot> slots;
    private final SelectedSessionsDao dao;
    private final Picasso picasso;
    private final ScheduleDayEntry.OnSessionClickListener listener;

    public ScheduleDayAdapter(List<Slot> slots, SelectedSessionsDao dao, Picasso picasso, ScheduleDayEntry.OnSessionClickListener listener) {
        this.slots = slots;
        this.dao = dao;
        this.picasso = picasso;
        this.listener = listener;
    }

    @Override
    public ScheduleDayEntry onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ScheduleDayEntry(parent, picasso, listener);
    }

    @Override
    public void onBindViewHolder(ScheduleDayEntry holder, int position) {
        Slot slot = slots.get(position);
        List<Session> slotSessions = slot.getSessions();
        Session selectedSession = findSelectedSession(slot.getId(), slotSessions);

        if (selectedSession == null) {
            if (slotSessions.size() > 1) {
                holder.bindFreeSlot(slot);
            } else {
                Session session = slotSessions.get(0);
                if (session.getSpeakers() == null) {
                    holder.bindBreakSlot(slot, session);
                } else {
                    holder.bindSelectedSession(slot, session);
                }
            }
        } else {
            holder.bindSelectedSession(slot, selectedSession);
        }
    }

    @Override
    public int getItemCount() {
        return slots.size();
    }

    private Session findSelectedSession(int slotId, List<Session> slotSessions) {
        Session selectedSession = null;
        int selectedSessionId = dao.get(slotId);

        if (selectedSessionId != 0) {
            for (Session session : slotSessions) {
                if (session.getId() == selectedSessionId) {
                    selectedSession = session;
                    break;
                }
            }
        }
        return selectedSession;
    }
}
