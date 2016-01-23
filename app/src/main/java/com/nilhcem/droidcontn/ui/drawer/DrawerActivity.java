package com.nilhcem.droidcontn.ui.drawer;

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

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import lombok.val;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DrawerActivity extends BaseActivity<DrawerPresenter> implements DrawerActivityView {

    @Bind(R.id.drawer_toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.drawer_navigation) NavigationView navigationView;

    // TODO: Remove all these
    @Inject DroidconService service;
    public List<Speaker> speakers;

    @Override
    protected DrawerPresenter newPresenter() {
        return new DrawerPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer);
        setSupportActionBar(toolbar);

        val toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            presenter.onNavigationItemSelected(item.getItemId());
            return true;
        });

        // Testing injection. TODO: Remove
        DroidconApp.get(this).component().inject(this);
        service.loadSpeakers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(speakers -> {
                    this.speakers = speakers;
                    Toast.makeText(this, "data loaded", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void updateToolbarTitle(@StringRes int resId) {
        getSupportActionBar().setTitle(resId);
    }

    @Override
    public boolean isNavigationDrawerOpen() {
        return drawer.isDrawerOpen(GravityCompat.START);
    }

    @Override
    public void closeNavigationDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_fragments_container, fragment)
                .commit();
    }

    @Override
    public void selectFirstDrawerEntry() {
        navigationView.setCheckedItem(R.id.drawer_nav_schedule);
    }
}
