package com.nilhcem.droidcontn.ui.schedule.day;

import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.ScheduleSlot;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.ui.core.recyclerview.BaseViewHolder;
import com.squareup.picasso.Picasso;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.List;

import butterknife.Bind;
import java8.util.stream.StreamSupport;

public class ScheduleDayEntry extends BaseViewHolder {

    public interface OnSessionClickListener {
        void onFreeSlotClicked(ScheduleSlot slot);

        void onSelectedSessionClicked(Session session);
    }

    @Bind(R.id.schedule_day_entry_time) TextView time;

    @Bind(R.id.schedule_day_entry_break_card) CardView breakCard;
    @Bind(R.id.schedule_day_entry_break_text) TextView breakText;

    @Bind(R.id.schedule_day_entry_browse_card) CardView browseCard;

    @Bind(R.id.schedule_day_entry_session_card) CardView sessionCard;
    @Bind(R.id.schedule_day_entry_session_title) TextView sessionTitle;
    @Bind(R.id.schedule_day_entry_session_time) TextView sessionTime;
    @Bind(R.id.schedule_day_entry_session_room) TextView sessionRoom;
    @Bind(R.id.schedule_day_entry_slot_speakers) ViewGroup sessionSpeakers;

    private final Picasso picasso;
    private final OnSessionClickListener listener;

    public ScheduleDayEntry(ViewGroup parent, Picasso picasso, OnSessionClickListener listener) {
        super(parent, R.layout.schedule_day_entry);
        this.picasso = picasso;
        this.listener = listener;
    }

    public void bindFreeSlot(ScheduleSlot slot) {
        breakCard.setVisibility(View.GONE);
        sessionCard.setVisibility(View.GONE);

        bindTime(slot, true);
        browseCard.setVisibility(View.VISIBLE);
        browseCard.setOnClickListener(v -> listener.onFreeSlotClicked(slot));
    }

    public void bindBreakSlot(ScheduleSlot slot, Session session, boolean showTime) {
        browseCard.setVisibility(View.GONE);
        sessionCard.setVisibility(View.GONE);

        bindTime(slot, showTime);
        breakCard.setVisibility(View.VISIBLE);
        breakText.setText(session.getTitle());
    }

    public void bindSelectedSession(ScheduleSlot slot, Session session, boolean showTime) {
        breakCard.setVisibility(View.GONE);
        browseCard.setVisibility(View.GONE);

        bindTime(slot, showTime);
        sessionCard.setVisibility(View.VISIBLE);
        sessionTitle.setText(session.getTitle());
        sessionTime.setText(formatSessionTime(session));
        sessionRoom.setText(session.getRoom());
        bindSessionSpeakers(session);
        sessionCard.setOnClickListener(v -> listener.onSelectedSessionClicked(session));
    }

    private String formatTime(LocalDateTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        return time.format(formatter);
    }

    private void bindTime(ScheduleSlot slot, boolean showTime) {
        if (showTime) {
            String timeStr = formatTime(slot.getTime());
            time.setVisibility(View.VISIBLE);
            time.setText(timeStr);
        } else {
            time.setVisibility(View.INVISIBLE);
        }
    }

    private String formatSessionTime(Session session) {
        LocalDateTime fromTime = session.getFromTime();
        LocalDateTime toTime = session.getToTime();
        long minutes = ChronoUnit.MINUTES.between(fromTime, toTime);
        return itemView.getContext().getString(R.string.schedule_day_entry_session_time_format,
                formatTime(fromTime), formatTime(toTime), minutes);
    }

    private void bindSessionSpeakers(Session session) {
        sessionSpeakers.removeAllViews();

        List<Speaker> speakers = session.getSpeakers();
        if (speakers != null) {
            StreamSupport.stream(speakers)
                    .map(speaker -> new ScheduleDayEntrySpeaker(sessionSpeakers.getContext(), speaker, picasso))
                    .forEach(sessionSpeakers::addView);
        }
    }
}
