package com.nilhcem.droidcontn.debug.stetho;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.data.network.ApiEndpoint;
import com.nilhcem.droidcontn.service.reminder.ReminderReceiver;
import com.nilhcem.droidcontn.ui.drawer.DrawerActivity;
import com.nilhcem.droidcontn.utils.App;
import com.nilhcem.droidcontn.utils.Threads;

import org.threeten.bp.LocalDateTime;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AppDumperPlugin implements DumperPlugin {

    private final Context context;
    private final ApiEndpoint endpoint;

    @Inject
    public AppDumperPlugin(Application app, ApiEndpoint endpoint) {
        this.context = app;
        this.endpoint = endpoint;
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
            case "alarmManager":
                doAlarmManager(writer, args);
                break;
            case "appInfo":
                displayAppInfo(writer);
                break;
            case "endpoint":
                changeEndpoint(writer, args);
                break;
            case "reminder":
                doReminder(writer, args);
                break;
            default:
                doUsage(writer);
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void doAlarmManager(PrintStream writer, List<String> args) {
        if (args.size() != 1) {
            doUsage(writer);
        } else if (args.get(0).equals("next")) {
            //TODO: http://stackoverflow.com/questions/4556670/how-to-check-if-alarmmanager-already-has-an-alarm-set
            //TODO: http://stackoverflow.com/questions/6522792/get-list-of-active-pendingintents-in-alarmmanager
        }
    }

    private void displayAppInfo(PrintStream writer) {
        writer.println(context.getString(R.string.app_name) + " " + App.getVersion());
    }

    private void changeEndpoint(PrintStream writer, List<String> args) {
        if (args.size() < 1) {
            doUsage(writer);
        } else {
            switch (args.get(0)) {
                case "get":
                    writer.println(String.format(Locale.US, "Endpoint: %s", endpoint));
                    break;
                case "set":
                    if (args.size() < 2) {
                        doUsage(writer);
                    } else {
                        String arg = args.get(1);

                        try {
                            ApiEndpoint endpoint = ApiEndpoint.valueOf(arg.toUpperCase(Locale.US));
                            ApiEndpoint.persist(context, endpoint);
                        } catch (IllegalArgumentException e) {
                            ApiEndpoint.persist(context, arg);
                        }
                        restartApp(writer);
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    private void doReminder(PrintStream writer, List<String> args) {
        if (args.size() != 1) {
            doUsage(writer);
        } else if (args.get(0).equals("test")) {
            List<Speaker> speakers = Arrays.asList(new Speaker(42, "Nilhcem", "Developer", "Procrastinator", "nilhcem.com", "Nilhcem", "Nilhcem", ""));
            LocalDateTime now = LocalDateTime.now();
            Session session = new Session(42, "Room #1", speakers, "The 2016 Android Developer Toolbox", "Boring stuff", now.plusMinutes(3), now.plusHours(1));
            new ReminderReceiver().onReceive(context, ReminderReceiver.createReceiverIntent(context, session));
        }
    }

    private void doUsage(PrintStream writer) {
        writer.println("usage: dumpapp [arg]");
        writer.println();
        writer.println("arg:");
        writer.println("* alarmManager next: Display next alarm");
        writer.println("* appInfo: Display current app build info");
        writer.println("* endpoint get: Display current api endpoint");
        writer.println("* endpoint set (PROD|MOCK|\"https?://<url>\"): Change api endpoint");
        writer.println("* reminder test: Test a notification reminder");
    }

    private void restartApp(PrintStream writer) {
        writer.println("Restarting app...");

        // Restart app after a few delay to make sure stetho can print the previous message.
        new Thread(() -> {
            Threads.silentSleep(500);
            ProcessPhoenix.triggerRebirth(context, new Intent(context, DrawerActivity.class));
        }).start();
    }
}
