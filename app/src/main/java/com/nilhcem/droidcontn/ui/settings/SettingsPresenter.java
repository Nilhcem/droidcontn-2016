package com.nilhcem.droidcontn.ui.settings;

import android.os.Bundle;

import com.nilhcem.droidcontn.ui.BaseFragmentPresenter;
import com.nilhcem.droidcontn.utils.AppUtils;

public class SettingsPresenter extends BaseFragmentPresenter<SettingsView> {

    public SettingsPresenter(SettingsView view) {
        super(view);
    }

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        mView.setAppVersion(AppUtils.getVersion());
    }

    public boolean onNotifySessionsChange(boolean checked) {
        mView.setNotifySessionsCheckbox(checked);
        return true;
    }
}
