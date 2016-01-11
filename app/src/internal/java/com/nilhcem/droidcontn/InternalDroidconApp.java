package com.nilhcem.droidcontn;

import com.nilhcem.droidcontn.core.dagger.AppComponent;
import com.nilhcem.droidcontn.debug.stetho.StethoInitializer;

import javax.inject.Inject;

public class InternalDroidconApp extends DroidconApp {

    @Inject StethoInitializer mStetho;

    @Override
    public void onCreate() {
        super.onCreate();
        AppComponent.Initializer.init(this).inject(this);
        mStetho.init();
    }
}
