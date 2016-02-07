package com.nilhcem.droidcontn.service.reminder;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Room;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.database.dao.SessionsDao;
import com.nilhcem.droidcontn.utils.App;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class SessionsReminder {

    private final Context context;
    private final SessionsDao sessionsDao;
    private final SharedPreferences preferences;
    private final AlarmManager alarmManager;

    @Inject
    public SessionsReminder(Application app, SessionsDao sessionsDao, SharedPreferences preferences) {
        this.context = app;
        this.sessionsDao = sessionsDao;
        this.preferences = preferences;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public boolean isEnabled() {
        return preferences.getBoolean(context.getString(R.string.settings_notify_key), false);
    }

    public void enableSessionReminder() {
        sessionsDao.getSelectedSessions()
                .flatMap(Observable::from)
                .map(session -> new Session(session.id, Room.getFromId(session.roomId).name, null, session.title, session.description, LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusHours(1)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(this::addSessionReminder, error -> {
                    Timber.e(error, "Error enabling session reminder");
                });
    }

    public void disableSessionReminder() {
        sessionsDao.getSelectedSessions()
                .flatMap(Observable::from)
                .map(session -> new Session(session.id, Room.getFromId(session.roomId).name, null, session.title, session.description, LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusHours(1)))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(this::removeSessionReminder, error -> {
                    Timber.e(error, "Error enabling session reminder");
                });
    }

    public void addSessionReminder(@NonNull Session session) {
        if (!isEnabled()) {
            Timber.d("SessionsReminder is not enable, skip adding session");
            return;
        }

        PendingIntent intent = createSessionReminderIntent(session);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sessionStartTime = session.getFromTime().minusMinutes(3);
        if (!sessionStartTime.isAfter(now)) {
            Timber.w("No setting reminder for passed session");
            return;
        }
        Timber.d("Setting reminder on %s", sessionStartTime);
        App.setExactAlarm(alarmManager, sessionStartTime.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli(), intent);
    }

    public void removeSessionReminder(@NonNull Session session) {
        alarmManager.cancel(createSessionReminderIntent(session));
    }

    private PendingIntent createSessionReminderIntent(@NonNull Session session) {
        Intent intent = ReminderReceiver.createReceiverIntent(context, session);
        return PendingIntent.getBroadcast(context, session.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
