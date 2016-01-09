package com.nilhcem.droidcontn.ui.drawer;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.ui.BaseActivity;
import com.nilhcem.droidcontn.ui.BaseFragment;

import butterknife.Bind;
import lombok.val;

public class DrawerActivity extends BaseActivity<DrawerPresenter> implements DrawerActivityView {

    @Bind(R.id.drawer_toolbar) Toolbar mToolbar;
    @Bind(R.id.drawer_layout) DrawerLayout mDrawer;
    @Bind(R.id.drawer_navigation) NavigationView mNavigationView;

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
    public void showFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.drawer_fragments_container, fragment)
                .commit();
    }
}
