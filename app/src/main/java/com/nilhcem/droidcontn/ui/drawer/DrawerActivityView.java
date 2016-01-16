package com.nilhcem.droidcontn.ui.drawer;

import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

public interface DrawerActivityView {

    boolean isNavigationDrawerOpen();

    void closeNavigationDrawer();

    void updateToolbarTitle(@StringRes int resId);

    void showFragment(Fragment fragment);

    void selectFirstDrawerEntry();
}
