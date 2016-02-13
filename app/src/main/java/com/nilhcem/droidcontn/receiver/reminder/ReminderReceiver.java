package com.nilhcem.droidcontn.receiver.reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.ui.sessions.details.SessionDetailsActivity;
import com.nilhcem.droidcontn.ui.sessions.details.SessionDetailsActivityIntentBuilder;
import com.nilhcem.droidcontn.utils.App;
import com.nilhcem.droidcontn.utils.Downloader;
import com.nilhcem.droidcontn.utils.Preconditions;

import rx.schedulers.Schedulers;
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
        Schedulers.io().createWorker().schedule(() -> {
            PendingIntent pendingIntent = TaskStackBuilder.create(context)
                    .addParentStack(SessionDetailsActivity.class)
                    .addNextIntent(new SessionDetailsActivityIntentBuilder(session).build(context))
                    .getPendingIntent(session.getId(), PendingIntent.FLAG_UPDATE_CURRENT);

            android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.reminder_about_to_start, session.getTitle())))
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true);

            String url = App.getPhotoUrl(session);
            if (!TextUtils.isEmpty(url)) {
                Bitmap bitmap = Downloader.downloadBitmap(url);
                if (bitmap != null) {
                    builder.setLargeIcon(bitmap);
                }
            }

            Notification notification = builder.build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(session.getId(), notification);
        });
    }
}
