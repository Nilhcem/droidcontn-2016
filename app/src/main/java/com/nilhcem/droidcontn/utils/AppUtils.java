package com.nilhcem.droidcontn.utils;

import com.nilhcem.droidcontn.BuildConfig;

import java.util.Locale;

public final class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getVersion() {
        String version = String.format(Locale.US, "%s (#%d)", BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE);
        if (BuildConfig.INTERNAL_BUILD) {
            version = String.format(Locale.US, "%s — commit %s", version, BuildConfig.GIT_SHA);
        }
        return version;
    }
}
