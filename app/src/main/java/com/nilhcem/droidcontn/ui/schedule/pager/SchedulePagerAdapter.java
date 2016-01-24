package com.nilhcem.droidcontn.ui.schedule.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.ui.schedule.day.ScheduleDayFragment;

public class SchedulePagerAdapter extends FragmentPagerAdapter {

    private final Schedule schedule;

    public SchedulePagerAdapter(FragmentManager fm, Schedule schedule) {
        super(fm);
        this.schedule = schedule;
    }

    @Override
    public Fragment getItem(int position) {
        return ScheduleDayFragment.newInstance(schedule.get(position));
    }

    @Override
    public int getCount() {
        return schedule.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return schedule.get(position).getDay();
    }
}
