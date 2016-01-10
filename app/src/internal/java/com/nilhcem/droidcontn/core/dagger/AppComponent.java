package com.nilhcem.droidcontn.core.dagger;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.core.dagger.module.ApiModule;
import com.nilhcem.droidcontn.core.dagger.module.AppModule;
import com.nilhcem.droidcontn.core.dagger.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class, DataModule.class})
public interface AppComponent extends InternalAppGraph {

    /**
     * An initializer that creates the internal graph from an application.
     */
    final class Initializer {
        public static AppComponent init(DroidconApp app) {
            return DaggerAppComponent.builder()
                    .appModule(new AppModule(app))
                    .apiModule(new ApiModule())
                    .dataModule(new DataModule())
                    .build();
        }

        private Initializer() {
            throw new UnsupportedOperationException();
        }
    }
}
