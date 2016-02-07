package com.nilhcem.droidcontn.ui.settings;

import com.nilhcem.droidcontn.service.reminder.SessionsReminder;
import com.nilhcem.droidcontn.ui.BasePresenter;
import com.nilhcem.droidcontn.utils.App;

public class SettingsPresenter extends BasePresenter<SettingsView> {

    private final SessionsReminder sessionsReminder;

    public SettingsPresenter(SettingsView view, SessionsReminder sessionsReminder) {
        super(view);
        this.sessionsReminder = sessionsReminder;
    }

    public void onCreate() {
        view.setAppVersion(App.getVersion());
    }

    public boolean onNotifySessionsChange(boolean checked) {
        view.setNotifySessionsCheckbox(checked);

        if (checked) {
            sessionsReminder.enableSessionReminder();
        } else {
            sessionsReminder.disableSessionReminder();
        }
        return true;
    }
}
