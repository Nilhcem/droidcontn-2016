package com.nilhcem.droidcontn.core.dagger;

import com.nilhcem.droidcontn.receiver.BootReceiver;
import com.nilhcem.droidcontn.ui.drawer.DrawerActivity;
import com.nilhcem.droidcontn.ui.schedule.day.ScheduleDayFragment;
import com.nilhcem.droidcontn.ui.schedule.pager.SchedulePagerFragment;
import com.nilhcem.droidcontn.ui.sessions.details.SessionDetailsActivity;
import com.nilhcem.droidcontn.ui.sessions.list.SessionsListActivity;
import com.nilhcem.droidcontn.ui.settings.SettingsFragment;
import com.nilhcem.droidcontn.ui.speakers.details.SpeakerDetailsDialogFragment;
import com.nilhcem.droidcontn.ui.speakers.list.SpeakersListFragment;

/**
 * A common interface implemented by both the internal and production flavored components.
 */
public interface AppGraph {

    void inject(DrawerActivity activity);

    void inject(SchedulePagerFragment fragment);

    void inject(ScheduleDayFragment fragment);

    void inject(SessionsListActivity activity);

    void inject(SpeakersListFragment fragments);

    void inject(SessionDetailsActivity activity);

    void inject(SpeakerDetailsDialogFragment fragment);

    void inject(SettingsFragment fragment);

    void inject(BootReceiver receiver);
}
