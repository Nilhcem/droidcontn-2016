package com.nilhcem.droidcontn.debug.stetho;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.api.ApiEndpoint;
import com.nilhcem.droidcontn.ui.drawer.DrawerActivity;
import com.nilhcem.droidcontn.utils.AppUtils;
import com.nilhcem.droidcontn.utils.Threads;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AppDumperPlugin implements DumperPlugin {

    private final Context mContext;
    private final ApiEndpoint mEndpoint;

    @Inject
    public AppDumperPlugin(Application app, ApiEndpoint endpoint) {
        mContext = app;
        mEndpoint = endpoint;
    }

    @Override
    public String getName() {
        return "droidcontn";
    }

    @Override
    public void dump(DumperContext dumpContext) throws DumpException {
        final PrintStream writer = dumpContext.getStdout();
        List<String> args = dumpContext.getArgsAsList();
        String commandName = (args.isEmpty()) ? "" : args.remove(0);

        switch (commandName) {
            case "appInfo":
                displayAppInfo(writer);
                break;
            case "endpoint":
                changeEndpoint(writer, args);
                break;
            default:
                doUsage(writer);
                break;
        }
    }

    private void displayAppInfo(PrintStream writer) {
        writer.println(mContext.getString(R.string.app_name) + " " + AppUtils.getVersion());
    }

    private void changeEndpoint(PrintStream writer, List<String> args) {
        if (args.size() < 1) {
            doUsage(writer);
        } else {
            switch (args.get(0)) {
                case "get":
                    writer.println(String.format(Locale.US, "Endpoint: %s", mEndpoint));
                    break;
                case "set":
                    if (args.size() < 2) {
                        doUsage(writer);
                    } else {
                        String arg = args.get(1);

                        try {
                            ApiEndpoint endpoint = ApiEndpoint.valueOf(arg.toUpperCase(Locale.US));
                            ApiEndpoint.persist(mContext, endpoint);
                        } catch (IllegalArgumentException e) {
                            ApiEndpoint.persist(mContext, arg);
                        }
                        restartApp(writer);
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    private void doUsage(PrintStream writer) {
        writer.println("usage: dumpapp [arg]");
        writer.println();
        writer.println("arg:");
        writer.println("* appInfo: Display current app build info");
        writer.println("* endpoint get: Display current api endpoint");
        writer.println("* endpoint set (PROD|MOCK|\"https?://<url>\"): Change api endpoint");
    }

    private void restartApp(PrintStream writer) {
        writer.println("Restarting app...");

        // Restart app after a few delay to make sure stetho can print the previous message.
        new Thread(() -> {
            Threads.silentSleep(500);
            ProcessPhoenix.triggerRebirth(mContext, new Intent(mContext, DrawerActivity.class));
        }).start();
    }
}
