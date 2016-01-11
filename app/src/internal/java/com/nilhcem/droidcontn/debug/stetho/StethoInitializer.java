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

    private final Context mContext;
    private final AppDumperPlugin mAppDumper;

    @Inject
    public StethoInitializer(Application application, AppDumperPlugin appDumper) {
        mContext = application;
        mAppDumper = appDumper;
    }

    public void init() {
        Timber.plant(new StethoTree());
        Stetho.initialize(
                Stetho.newInitializerBuilder(mContext)
                        .enableDumpapp(this)
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(mContext))
                        .build());
    }

    @Override
    public Iterable<DumperPlugin> get() {
        List<DumperPlugin> plugins = new ArrayList<>();
        for (DumperPlugin plugin : Stetho.defaultDumperPluginsProvider(mContext).get()) {
            plugins.add(plugin);
        }
        plugins.add(mAppDumper);
        return plugins;
    }
}
