package com.nilhcem.droidcontn.ui.schedule.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nilhcem.droidcontn.data.model.SessionDay;
import com.nilhcem.droidcontn.ui.schedule.item.ScheduleItemFragment;

public class SchedulePagerAdapter extends FragmentPagerAdapter {

    public SchedulePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ScheduleItemFragment.newInstance(SessionDay.values()[position]);
    }

    @Override
    public int getCount() {
        return SessionDay.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return SessionDay.values()[position].label;
    }
}
