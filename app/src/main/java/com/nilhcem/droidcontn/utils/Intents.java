package com.nilhcem.droidcontn.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.BundleCompat;
import android.support.v4.content.ContextCompat;

import com.nilhcem.droidcontn.R;

public final class Intents {

    private Intents() {
        throw new UnsupportedOperationException();
    }

    public static void openExternalUrl(@NonNull Context context, @NonNull String url) {
        int primaryColor = ContextCompat.getColor(context, R.color.primary);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        Bundle extras = new Bundle();
        BundleCompat.putBinder(extras, "android.support.customtabs.extra.SESSION", null);
        intent.putExtras(extras);
        intent.putExtra("android.support.customtabs.extra.TOOLBAR_COLOR", primaryColor);

        context.startActivity(intent);
    }
}
