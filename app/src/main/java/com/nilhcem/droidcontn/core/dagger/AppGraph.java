package com.nilhcem.droidcontn.core.dagger;

import com.nilhcem.droidcontn.ui.drawer.DrawerActivity;

/**
 * A common interface implemented by both the Release and Debug flavored components.
 */
public interface AppGraph {

    void inject(DrawerActivity activity);
}
