package com.nilhcem.droidcontn;

import android.app.Application;
import android.content.Context;

import com.nilhcem.droidcontn.core.dagger.AppComponent;

public class DroidconApp extends Application {

    private AppComponent mComponent;

    public static DroidconApp get(Context context) {
        return (DroidconApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGraph();
    }

    public AppComponent component() {
        return mComponent;
    }

    private void initGraph() {
        mComponent = AppComponent.Initializer.init(this);
    }
}
