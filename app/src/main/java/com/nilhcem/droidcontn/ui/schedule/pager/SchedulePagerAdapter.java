package com.nilhcem.droidcontn.ui.schedule.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.ui.schedule.day.ScheduleDayFragment;

import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

public class SchedulePagerAdapter extends FragmentPagerAdapter {

    private final Schedule schedule;
    private final String dayPattern;

    public SchedulePagerAdapter(Context context, FragmentManager fm, Schedule schedule) {
        super(fm);
        this.schedule = schedule;
        dayPattern = context.getString(R.string.schedule_pager_day_pattern);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dayPattern, Locale.getDefault());
        return schedule.get(position).getDay().format(formatter);
    }
}
