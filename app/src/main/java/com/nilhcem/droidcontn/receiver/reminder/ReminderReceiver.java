package com.nilhcem.droidcontn.receiver.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.ui.sessions.details.SessionDetailsActivity;
import com.nilhcem.droidcontn.utils.Preconditions;

import timber.log.Timber;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String EXTRA_SESSION = "session";

    public static Intent createReceiverIntent(Context context, Session session) {
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(EXTRA_SESSION, session);
        return intent;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("Received session reminder");
        Session session = intent.getParcelableExtra(EXTRA_SESSION);
        Preconditions.checkArgument(session != null);
        showNotification(context, session);
    }

    private void showNotification(Context context, Session session) {
        Intent sessionIntent = SessionDetailsActivity.createIntent(context, session);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, session.getId(), sessionIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.reminder_about_to_start, session.getTitle()))
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(session.getId(), notification);
    }
}
