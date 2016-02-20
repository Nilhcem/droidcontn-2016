package com.nilhcem.droidcontn;

import android.os.Build;

import com.frogermcs.dagger2metrics.Dagger2Metrics;
import com.nilhcem.droidcontn.core.dagger.AppComponent;
import com.nilhcem.droidcontn.debug.lifecycle.ActivityProvider;
import com.nilhcem.droidcontn.debug.stetho.StethoInitializer;

import javax.inject.Inject;

import jp.wasabeef.takt.Takt;

public class InternalDroidconApp extends DroidconApp {

    /**
     * Change it manually when you want to display the FPS.
     * Useful to test the frame rate.
     */
    private static final boolean DISPLAY_FPS = false;

    /**
     * Change it manually when you want to enable Dagger 2 metrics
     */
    private static final boolean ENABLE_DAGGER2_METRICS = false;

    @Inject StethoInitializer stetho;
    @Inject ActivityProvider activityProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent.Initializer.init(this).inject(this);
        displayFps(true);
        initDagger2Metrics();
        stetho.init();
        activityProvider.init(this);
    }

    @Override
    public void onTerminate() {
        displayFps(false);
        super.onTerminate();
    }

    private void displayFps(boolean enable) {
        if (DISPLAY_FPS && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (enable) {
                Takt.stock(this).play();
            } else {
                Takt.finish();
            }
        }
    }

    private void initDagger2Metrics() {
        if (ENABLE_DAGGER2_METRICS) {
            Dagger2Metrics.enableCapturing(this);
        }
    }
}
