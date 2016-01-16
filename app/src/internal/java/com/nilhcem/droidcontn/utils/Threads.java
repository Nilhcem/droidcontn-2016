package com.nilhcem.droidcontn.utils;

import timber.log.Timber;

public final class Threads {

    private Threads() {
        throw new UnsupportedOperationException();
    }

    public static void silentSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Timber.e(e, "Sleeping error");
        }
    }
}
