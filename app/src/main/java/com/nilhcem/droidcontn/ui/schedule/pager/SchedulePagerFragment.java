package com.nilhcem.droidcontn.ui.schedule.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.ui.BaseFragment;
import com.nilhcem.droidcontn.ui.drawer.DrawerActivity;

import butterknife.Bind;

public class SchedulePagerFragment extends BaseFragment<SchedulePagerPresenter> implements SchedulePagerView {

    @Bind(R.id.schedule_viewpager) ViewPager viewPager;

    public static SchedulePagerFragment newInstance() {
        return new SchedulePagerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.schedule_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setAdapter(new SchedulePagerAdapter(getChildFragmentManager()));
        ((DrawerActivity) getActivity()).setupTabLayoutWithViewPager(viewPager);
    }

    @Override
    protected SchedulePagerPresenter newPresenter() {
        return new SchedulePagerPresenter(this);
    }
}
