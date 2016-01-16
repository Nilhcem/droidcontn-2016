package com.nilhcem.droidcontn.ui.drawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.api.DroidconService;
import com.nilhcem.droidcontn.data.model.Speaker;
import com.nilhcem.droidcontn.ui.BaseActivity;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import lombok.val;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DrawerActivity extends BaseActivity<DrawerPresenter> implements DrawerActivityView {

    @Bind(R.id.drawer_toolbar) Toolbar mToolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawer;
    @Bind(R.id.drawer_navigation) NavigationView mNavigationView;

    // Testing injection. TODO: Remove all these
    @Inject SharedPreferences mSharedPrefs;
    @Inject Picasso mPicasso;
    @Inject DroidconService mService;

    @Override
    protected DrawerPresenter newPresenter() {
        return new DrawerPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        setSupportActionBar(mToolbar);

        val toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(item -> {
            mPresenter.onNavigationItemSelected(item.getItemId());
            return true;
        });

        // Testing injection. TODO: Remove
        DroidconApp.get(this).component().inject(this);
        mService.loadSpeakers()
                .flatMap(Observable::<Speaker>from)
                .first()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(speaker -> Toast.makeText(this, speaker.getName(), Toast.LENGTH_SHORT).show(),
                        throwable -> Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show(),
                        () -> Toast.makeText(this, "Completed", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void updateToolbarTitle(@StringRes int resId) {
        getSupportActionBar().setTitle(resId);
    }

    @Override
    public boolean isNavigationDrawerOpen() {
        return mDrawer.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void closeNavigationDrawer() {
        mDrawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_fragments_container, fragment)
                .commit();
    }

    @Override
    public void selectFirstDrawerEntry() {
        mNavigationView.setCheckedItem(R.id.drawer_nav_schedule);
    }
}
