package com.nilhcem.droidcontn.ui.sessions.details;

import android.os.Bundle;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.database.dao.SelectedSessionsDao;
import com.nilhcem.droidcontn.service.reminder.SessionsReminder;
import com.nilhcem.droidcontn.ui.BaseActivityPresenter;

public class SessionDetailsPresenter extends BaseActivityPresenter<SessionDetailsView> {

    private final Session session;
    private final SelectedSessionsDao sessionsDao;
    private final SessionsReminder sessionsReminder;

    public SessionDetailsPresenter(SessionDetailsView view, Session session, SelectedSessionsDao sessionsDao, SessionsReminder sessionsReminder) {
        super(view);
        this.session = session;
        this.sessionsDao = sessionsDao;
        this.sessionsReminder = sessionsReminder;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        view.bindSessionDetails(session);
        view.updateFabButton(sessionsDao.isSelected(session), false);
    }

    public void onFloatingActionButtonClicked() {
        boolean sessionSelected = sessionsDao.isSelected(session);
        if (sessionSelected) {
            sessionsDao.unselect(session);
            sessionsReminder.removeSessionReminder(session);
            view.showSnackbarMessage(R.string.session_details_removed);
        } else {
            sessionsDao.select(session);
            sessionsReminder.addSessionReminder(session);
            view.showSnackbarMessage(R.string.session_details_added);
        }
        view.updateFabButton(!sessionSelected, true);
    }
}
