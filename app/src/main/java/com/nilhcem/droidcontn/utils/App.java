package com.nilhcem.droidcontn.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;

import com.nilhcem.droidcontn.BuildConfig;

import java.util.Locale;

public final class App {

    private App() {
        throw new UnsupportedOperationException();
    }

    public static boolean isCompatible(int apiLevel) {
        return android.os.Build.VERSION.SDK_INT >= apiLevel;
    }

    public static String getVersion() {
        String version = String.format(Locale.US, "%s (#%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        if (BuildConfig.INTERNAL_BUILD) {
            version = String.format(Locale.US, "%s — commit %s", version, BuildConfig.GIT_SHA);
        }
        return version;
    }

    public static void setExactAlarm(AlarmManager alarmManager, long triggerAtMillis, PendingIntent operation) {
        if (isCompatible(Build.VERSION_CODES.KITKAT)) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
        }
    }
}
