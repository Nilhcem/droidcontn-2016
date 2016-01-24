package com.nilhcem.droidcontn.core.dagger;

import com.nilhcem.droidcontn.ui.drawer.DrawerActivity;
import com.nilhcem.droidcontn.ui.schedule.pager.SchedulePagerFragment;
import com.nilhcem.droidcontn.ui.sessions.list.SessionsListActivity;
import com.nilhcem.droidcontn.ui.settings.SettingsFragment;
import com.nilhcem.droidcontn.ui.speakers.detail.SpeakerDetailDialogFragment;
import com.nilhcem.droidcontn.ui.speakers.list.SpeakersListFragment;

/**
 * A common interface implemented by both the internal and production flavored components.
 */
public interface AppGraph {

    void inject(DrawerActivity activity);

    void inject(SchedulePagerFragment fragment);

    void inject(SessionsListActivity activity);

    void inject(SpeakersListFragment fragments);

    void inject(SpeakerDetailDialogFragment fragment);

    void inject(SettingsFragment fragment);
}
