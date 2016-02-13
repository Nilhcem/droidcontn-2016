package com.nilhcem.droidcontn.ui.schedule.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.DataProvider;
import com.nilhcem.droidcontn.data.app.model.Schedule;
import com.nilhcem.droidcontn.ui.BaseFragment;
import com.nilhcem.droidcontn.ui.drawer.DrawerActivity;

import javax.inject.Inject;

import butterknife.Bind;

public class SchedulePagerFragment extends BaseFragment<SchedulePagerPresenter> implements SchedulePagerView {

    @Inject DataProvider dataProvider;

    @Bind(R.id.schedule_loading) ProgressBar loading;
    @Bind(R.id.schedule_viewpager) ViewPager viewPager;

    @Override
    protected SchedulePagerPresenter newPresenter() {
        DroidconApp.get(getContext()).component().inject(this);
        return new SchedulePagerPresenter(this, dataProvider);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_pager, container, false);
    }

    @Override
    public void displaySchedule(Schedule schedule) {
        viewPager.setAdapter(new SchedulePagerAdapter(getContext(), getChildFragmentManager(), schedule));
        if (schedule.size() > 1) {
            ((DrawerActivity) getActivity()).setupTabLayoutWithViewPager(viewPager);
        }

        loading.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayLoadingError(Throwable error) {
        loading.setVisibility(View.GONE);
        Snackbar.make(loading, error.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
    }
}
