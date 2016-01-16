package com.nilhcem.droidcontn.ui.settings;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.utils.Intents;

public class SettingsFragment extends PreferenceFragmentCompat implements SettingsView {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    private SettingsPresenter mPresenter;
    private CheckBoxPreference mNotifySessions;
    private Preference mAppVersion;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        bindPreferences();
        initPresenter();
        mNotifySessions.setOnPreferenceChangeListener((preference, newValue) ->
                mPresenter.onNotifySessionsChange((Boolean) newValue));
    }

    @Override
    public void setNotifySessionsCheckbox(boolean checked) {
        mNotifySessions.setChecked(checked);
    }

    @Override
    public void setAppVersion(CharSequence version) {
        mAppVersion.setSummary(version);
    }

    private void initPresenter() {
        DroidconApp.get(getContext()).component().inject(this);
        mPresenter = new SettingsPresenter(this);
        mPresenter.onCreate();
    }

    private void bindPreferences() {
        addPreferencesFromResource(R.xml.settings);
        mNotifySessions = findPreference(R.string.settings_notify_key);
        mAppVersion = findPreference(R.string.settings_version_key);
        initPreferenceLink(R.string.settings_conf_key);
        initPreferenceLink(R.string.settings_github_key);
        initPreferenceLink(R.string.settings_developer_key);
    }

    private <T extends Preference> T findPreference(@StringRes int resId) {
        return (T) findPreference(getString(resId));
    }

    private void initPreferenceLink(@StringRes int resId) {
        findPreference(resId).setOnPreferenceClickListener(preference -> {
            Intents.startExternalUrl(getContext(), preference.getSummary().toString());
            return true;
        });
    }
}
