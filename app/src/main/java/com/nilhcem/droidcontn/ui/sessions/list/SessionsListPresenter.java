package com.nilhcem.droidcontn.ui.sessions.list;

import android.content.Context;
import android.os.Bundle;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Slot;
import com.nilhcem.droidcontn.ui.BaseActivityPresenter;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.Locale;

public class SessionsListPresenter extends BaseActivityPresenter<SessionsListView> {

    private final Slot slot;

    public SessionsListPresenter(Context context, SessionsListView view, Slot slot) {
        super(view);
        this.slot = slot;
        this.view.initToobar(formatDateTime(context, slot.getFromTime()));
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.view.initSessionsList(slot.getSessions());
    }

    private String formatDateTime(Context context, LocalDateTime dateTime) {
        String dayPattern = context.getString(R.string.schedule_pager_day_pattern);
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern(dayPattern, Locale.getDefault());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

        String formatted = context.getString(R.string.schedule_browse_sessions_title_pattern,
                dateTime.format(dayFormatter),
                dateTime.format(timeFormatter));

        // Capitalize first letter
        return formatted.substring(0, 1).toUpperCase(Locale.getDefault()) + formatted.substring(1);
    }
}
