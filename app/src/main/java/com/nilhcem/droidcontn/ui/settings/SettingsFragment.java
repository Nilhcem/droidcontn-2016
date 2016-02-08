package com.nilhcem.droidcontn.ui.settings;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.nilhcem.droidcontn.DroidconApp;
import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.receiver.reminder.SessionsReminder;
import com.nilhcem.droidcontn.utils.Intents;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat implements SettingsView {

    @Inject SessionsReminder sessionsReminder;

    private SettingsPresenter presenter;
    private CheckBoxPreference notifySessions;
    private Preference appVersion;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        bindPreferences();
        initPresenter();
        notifySessions.setOnPreferenceChangeListener((preference, newValue) ->
                presenter.onNotifySessionsChange((Boolean) newValue));
    }

    @Override
    public void setNotifySessionsCheckbox(boolean checked) {
        notifySessions.setChecked(checked);
    }

    @Override
    public void setAppVersion(CharSequence version) {
        appVersion.setSummary(version);
    }

    private void initPresenter() {
        DroidconApp.get(getContext()).component().inject(this);
        presenter = new SettingsPresenter(this, sessionsReminder);
        presenter.onCreate();
    }

    private void bindPreferences() {
        addPreferencesFromResource(R.xml.settings);
        notifySessions = findPreference(R.string.settings_notify_key);
        appVersion = findPreference(R.string.settings_version_key);
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
