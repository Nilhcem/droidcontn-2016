package com.nilhcem.droidcontn.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;

import icepick.Icepick;

public abstract class BaseActivityPresenter<V> {

    protected final V mView;

    public BaseActivityPresenter(V view) {
        mView = view;
    }

    public void onPostCreate(Bundle savedInstanceState) {
        // Nothing to do by default
    }

    public void onSaveInstanceState(Bundle outState) {
        Icepick.saveInstanceState(this, outState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    public void onNavigationItemSelected(@IdRes int itemId) {
        // Nothing to do by default
    }

    public boolean onBackPressed() {
        return false;
    }
}
