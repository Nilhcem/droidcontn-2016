package com.nilhcem.droidcontn.receiver.reminder;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.AppMapper;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.database.DbMapper;
import com.nilhcem.droidcontn.data.database.dao.SessionsDao;
import com.nilhcem.droidcontn.data.database.dao.SpeakersDao;
import com.nilhcem.droidcontn.utils.App;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

@Singleton
public class SessionsReminder {

    private final Context context;
    private final SessionsDao sessionsDao;
    private final SpeakersDao speakerDao;
    private final AppMapper appMapper;
    private final DbMapper dbMapper;
    private final SharedPreferences preferences;
    private final AlarmManager alarmManager;

    @Inject
    public SessionsReminder(Application app, SessionsDao sessionsDao, SpeakersDao speakersDao, AppMapper appMapper, DbMapper dbMapper, SharedPreferences preferences) {
        this.context = app;
        this.sessionsDao = sessionsDao;
        this.speakerDao = speakersDao;
        this.appMapper = appMapper;
        this.dbMapper = dbMapper;
        this.preferences = preferences;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public boolean isEnabled() {
        return preferences.getBoolean(context.getString(R.string.settings_notify_key), false);
    }

    public void enableSessionReminder() {
        performOnSelectedSessions(this::addSessionReminder);
    }

    public void disableSessionReminder() {
        performOnSelectedSessions(this::removeSessionReminder);
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
            Timber.w("Do not set reminder for passed session");
            return;
        }
        Timber.d("Setting reminder on %s", sessionStartTime);
        App.setExactAlarm(alarmManager, sessionStartTime.atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli(), intent);
    }

    public void removeSessionReminder(@NonNull Session session) {
        Timber.d("Cancelling reminder on %s", session.getFromTime().minusMinutes(3));
        alarmManager.cancel(createSessionReminderIntent(session));
    }

    private PendingIntent createSessionReminderIntent(@NonNull Session session) {
        Intent intent = ReminderReceiver.createReceiverIntent(context, session);
        return PendingIntent.getBroadcast(context, session.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void performOnSelectedSessions(Action1<? super Session> onNext) {
        speakerDao.getSpeakers()
                .map(appMapper::speakersToMap)
                .subscribeOn(Schedulers.io())
                .subscribe(speakersMap -> {
                    sessionsDao.getSelectedSessions()
                            .map(sessions -> dbMapper.toAppSessions(sessions, speakersMap))
                            .flatMap(Observable::from)
                            .subscribeOn(Schedulers.io())
                            .observeOn(Schedulers.computation())
                            .subscribe(onNext, throwable -> Timber.e(throwable, "Error getting sessions"));
                });
    }
}
