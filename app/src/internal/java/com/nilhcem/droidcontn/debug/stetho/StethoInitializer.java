package com.nilhcem.droidcontn.debug.stetho;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.DumperPluginsProvider;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.facebook.stetho.timber.StethoTree;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

public class StethoInitializer implements DumperPluginsProvider {

    private final Context context;
    private final AppDumperPlugin appDumper;

    @Inject
    public StethoInitializer(Application application, AppDumperPlugin appDumper) {
        this.context = application;
        this.appDumper = appDumper;
    }

    public void init() {
        Timber.plant(new StethoTree());
        Stetho.initialize(
                Stetho.newInitializerBuilder(context)
                        .enableDumpapp(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context))
                        .build());
    }

    @Override
    public Iterable<DumperPlugin> get() {
        List<DumperPlugin> plugins = new ArrayList<>();
        for (DumperPlugin plugin : Stetho.defaultDumperPluginsProvider(context).get()) {
            plugins.add(plugin);
        }
        plugins.add(appDumper);
        return plugins;
    }
}
