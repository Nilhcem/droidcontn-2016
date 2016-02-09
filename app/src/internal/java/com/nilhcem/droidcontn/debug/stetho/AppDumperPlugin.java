package com.nilhcem.droidcontn.debug.stetho;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import com.facebook.stetho.dumpapp.DumpException;
import com.facebook.stetho.dumpapp.DumperContext;
import com.facebook.stetho.dumpapp.DumperPlugin;
import com.jakewharton.processphoenix.ProcessPhoenix;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.database.dao.SessionsDao;
import com.nilhcem.droidcontn.data.network.ApiEndpoint;
import com.nilhcem.droidcontn.receiver.BootReceiver;
import com.nilhcem.droidcontn.receiver.reminder.ReminderReceiver;
import com.nilhcem.droidcontn.ui.drawer.DrawerActivity;
import com.nilhcem.droidcontn.utils.App;
import com.nilhcem.droidcontn.utils.Threads;

import org.threeten.bp.format.DateTimeFormatter;

import java.io.PrintStream;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppDumperPlugin implements DumperPlugin {

    private final Context context;
    private final ApiEndpoint endpoint;
    private final SessionsDao sessionsDao;

    @Inject
    public AppDumperPlugin(Application app, ApiEndpoint endpoint, SessionsDao sessionsDao) {
        this.context = app;
        this.endpoint = endpoint;
        this.sessionsDao = sessionsDao;
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
            case "alarms":
                displayAlarms(writer);
                break;
            case "appInfo":
                displayAppInfo(writer);
                break;
            case "bootReceiver":
                displayBootReceiverState(writer);
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
    private void displayAlarms(PrintStream writer) {
        sessionsDao.getSessions()
                .flatMap(Observable::from)
                .map(session -> {
                    Intent intent = ReminderReceiver.createReceiverIntent(context, session);
                    PendingIntent broadcast = PendingIntent.getBroadcast(context, session.getId(), intent, PendingIntent.FLAG_NO_CREATE);
                    if (broadcast != null) {
                        return String.format(Locale.US, "%s - Session(id=%d, title=%s)", session.getFromTime().format(DateTimeFormatter.ISO_DATE_TIME), session.getId(), session.getTitle());
                    }
                    return null;
                })
                .filter(id -> id != null)
                .toList()
                .subscribe(activeAlarms -> {
                    writer.println(Integer.toString(activeAlarms.size()) + " active alarm(s)");
                    for (String activeAlarm : activeAlarms) {
                        writer.println(activeAlarm);
                    }
                });
    }

    private void displayAppInfo(PrintStream writer) {
        writer.println(context.getString(R.string.app_name) + " " + App.getVersion());
    }

    private void displayBootReceiverState(PrintStream writer) {
        ComponentName componentName = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        writer.print("Boot receiver state: ");
        int state = pm.getComponentEnabledSetting(componentName);
        switch (state) {
            case PackageManager.COMPONENT_ENABLED_STATE_DEFAULT:
                writer.println("default");
                break;
            case PackageManager.COMPONENT_ENABLED_STATE_ENABLED:
                writer.println("enabled");
                break;
            case PackageManager.COMPONENT_ENABLED_STATE_DISABLED:
                writer.println("disabled");
                break;
            case PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER:
                writer.println("disabled by user");
                break;
            default:
                writer.println(state);
                break;
        }
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
            sessionsDao.getSessions()
                    .flatMap(Observable::from)
                    .filter(session -> session.getSpeakers() != null)
                    .first()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(session -> {
                        new ReminderReceiver().onReceive(context, ReminderReceiver.createReceiverIntent(context, session));
                    });
        }
    }

    private void doUsage(PrintStream writer) {
        writer.println("usage: dumpapp [arg]");
        writer.println();
        writer.println("arg:");
        writer.println("* alarms: Display AlarmManager active alarms");
        writer.println("* appInfo: Display current app build info");
        writer.println("* bootReceiver: Display boot receiver state");
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
