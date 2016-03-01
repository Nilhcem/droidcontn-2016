package com.nilhcem.droidcontn.ui.sessions.list;

import android.content.Context;
import android.os.Bundle;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.ScheduleSlot;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.ui.BaseActivityPresenter;
import com.nilhcem.droidcontn.utils.Strings;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.List;
import java.util.Locale;

public class SessionsListPresenter extends BaseActivityPresenter<SessionsListView> {

    private final List<Session> sessions;

    public SessionsListPresenter(Context context, SessionsListView view, ScheduleSlot slot) {
        super(view);
        this.view.initToobar(formatDateTime(context, slot.getTime()));
        sessions = slot.getSessions();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.view.initSessionsList(sessions);
    }

    private String formatDateTime(Context context, LocalDateTime dateTime) {
        String dayPattern = context.getString(R.string.schedule_pager_day_pattern);
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern(dayPattern, Locale.getDefault());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

        String formatted = context.getString(R.string.schedule_browse_sessions_title_pattern,
                dateTime.format(dayFormatter),
                dateTime.format(timeFormatter));
        return Strings.capitalizeFirstLetter(formatted);
    }
}
