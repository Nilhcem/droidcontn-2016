package com.nilhcem.droidcontn.core.dagger;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.core.dagger.module.ApiModule;
import com.nilhcem.droidcontn.core.dagger.module.AppModule;
import com.nilhcem.droidcontn.core.dagger.module.DataModule;
import com.nilhcem.droidcontn.core.dagger.module.DatabaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class, DataModule.class, DatabaseModule.class})
public interface AppComponent extends AppGraph {

    /**
     * An initializer that creates the production graph from an application.
     */
    final class Initializer {

        private Initializer() {
            throw new UnsupportedOperationException();
        }

        public static AppComponent init(DroidconApp app) {
            return DaggerAppComponent.builder()
                    .appModule(new AppModule(app))
                    .apiModule(new ApiModule())
                    .dataModule(new DataModule())
                    .databaseModule(new DatabaseModule())
                    .build();
        }
    }
}
