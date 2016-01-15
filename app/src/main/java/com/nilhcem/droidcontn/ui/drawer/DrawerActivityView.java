package com.nilhcem.droidcontn.ui.drawer;

import android.support.annotation.StringRes;

import com.nilhcem.droidcontn.ui.BaseFragment;

public interface DrawerActivityView {

    boolean isNavigationDrawerOpen();

    void closeNavigationDrawer();

    void updateToolbarTitle(@StringRes int resId);

    void showFragment(BaseFragment fragment);

    void selectFirstDrawerEntry();
}
