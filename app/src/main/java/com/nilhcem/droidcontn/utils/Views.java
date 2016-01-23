package com.nilhcem.droidcontn.utils;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public final class Views {

    private Views() {
        throw new UnsupportedOperationException();
    }

    @TargetApi(JELLY_BEAN)
    @SuppressWarnings("deprecation")
    public static void setBackground(View view, @DrawableRes int drawableRes) {
        setBackground(view, ContextCompat.getDrawable(view.getContext(), drawableRes));
    }

    @TargetApi(JELLY_BEAN)
    @SuppressWarnings("deprecation")
    public static void setBackground(View view, Drawable drawable) {
        if (AppUtils.isCompatible(JELLY_BEAN)) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    @TargetApi(JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (AppUtils.isCompatible(JELLY_BEAN)) {
            viewTreeObserver.removeOnGlobalLayoutListener(listener);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(listener);
        }
    }
}
