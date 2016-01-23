package com.nilhcem.droidcontn.ui.drawer;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.ui.BaseActivityPresenter;
import com.nilhcem.droidcontn.ui.schedule.pager.SchedulePagerFragment;
import com.nilhcem.droidcontn.ui.settings.SettingsFragment;
import com.nilhcem.droidcontn.ui.speakers.list.SpeakersListFragment;
import com.nilhcem.droidcontn.ui.venue.VenueFragment;

import icepick.State;

public class DrawerPresenter extends BaseActivityPresenter<DrawerActivityView> {

    @State @StringRes int toolbarTitle;

    public DrawerPresenter(DrawerActivityView view) {
        super(view);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (savedInstanceState == null) {
            onNavigationItemSelected(R.id.drawer_nav_schedule);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        view.updateToolbarTitle(toolbarTitle);
    }

    @Override
    public void onNavigationItemSelected(@IdRes int itemId) {
        switch (itemId) {
            case R.id.drawer_nav_schedule:
                view.showFragment(SchedulePagerFragment.newInstance());
                toolbarTitle = R.string.drawer_nav_schedule;
                view.setTabLayoutVisibility(View.VISIBLE);
                break;
            case R.id.drawer_nav_speakers:
                view.showFragment(SpeakersListFragment.newInstance());
                toolbarTitle = R.string.drawer_nav_speakers;
                view.setTabLayoutVisibility(View.GONE);
                break;
            case R.id.drawer_nav_venue:
                view.showFragment(VenueFragment.newInstance());
                toolbarTitle = R.string.drawer_nav_venue;
                view.setTabLayoutVisibility(View.GONE);
                break;
            case R.id.drawer_nav_settings:
                view.showFragment(SettingsFragment.newInstance());
                toolbarTitle = R.string.drawer_nav_settings;
                view.setTabLayoutVisibility(View.GONE);
                break;
            default:
                throw new IllegalArgumentException();
        }
        view.updateToolbarTitle(toolbarTitle);
        view.closeNavigationDrawer();
    }

    @Override
    public boolean onBackPressed() {
        if (view.isNavigationDrawerOpen()) {
            view.closeNavigationDrawer();
            return true;
        } else if (toolbarTitle != R.string.drawer_nav_schedule) {
            onNavigationItemSelected(R.id.drawer_nav_schedule);
            view.selectFirstDrawerEntry();
            return true;
        }
        return false;
    }
}
