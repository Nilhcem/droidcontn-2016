package com.nilhcem.droidcontn.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewTreeObserver;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

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
        if (App.isCompatible(JELLY_BEAN)) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }

    @TargetApi(JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (App.isCompatible(JELLY_BEAN)) {
            viewTreeObserver.removeOnGlobalLayoutListener(listener);
        } else {
            viewTreeObserver.removeGlobalOnLayoutListener(listener);
        }
    }

    @TargetApi(LOLLIPOP)
    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(Context context, @DrawableRes int id) {
        if (App.isCompatible(LOLLIPOP)) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }

    public static int getActionBarSize(Context context) {
        if (context == null) {
            return 0;
        }

        Resources.Theme curTheme = context.getTheme();
        if (curTheme == null) {
            return 0;
        }

        int[] attrs = {android.R.attr.actionBarSize};
        TypedArray att = curTheme.obtainStyledAttributes(attrs);
        if (att == null) {
            return 0;
        }

        float size = att.getDimension(0, 0);
        att.recycle();
        return (int) size;
    }
}
