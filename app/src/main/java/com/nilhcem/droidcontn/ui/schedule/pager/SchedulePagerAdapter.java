package com.nilhcem.droidcontn.ui.schedule.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nilhcem.droidcontn.data.model.ScheduleDay;
import com.nilhcem.droidcontn.ui.schedule.item.ScheduleItemFragment;

import java.util.List;

public class SchedulePagerAdapter extends FragmentPagerAdapter {

    private final List<ScheduleDay> scheduleDays;

    public SchedulePagerAdapter(FragmentManager fm, List<ScheduleDay> scheduleDays) {
        super(fm);
        this.scheduleDays = scheduleDays;
    }

    @Override
    public Fragment getItem(int position) {
        return ScheduleItemFragment.newInstance(scheduleDays.get(position));
    }

    @Override
    public int getCount() {
        return scheduleDays.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return scheduleDays.get(position).getDay();
    }
}
