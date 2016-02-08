package com.nilhcem.droidcontn.ui.sessions.details;

import android.os.Bundle;

import com.nilhcem.droidcontn.R;
import com.nilhcem.droidcontn.data.app.model.Session;
import com.nilhcem.droidcontn.data.database.dao.SessionsDao;
import com.nilhcem.droidcontn.receiver.reminder.SessionsReminder;
import com.nilhcem.droidcontn.ui.BaseActivityPresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SessionDetailsPresenter extends BaseActivityPresenter<SessionDetailsView> {

    private final Session session;
    private final SessionsDao sessionsDao;
    private final SessionsReminder sessionsReminder;

    public SessionDetailsPresenter(SessionDetailsView view, Session session, SessionsDao sessionsDao, SessionsReminder sessionsReminder) {
        super(view);
        this.session = session;
        this.sessionsDao = sessionsDao;
        this.sessionsReminder = sessionsReminder;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        view.bindSessionDetails(session);
        sessionsDao.isSelected(session)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSelected -> view.updateFabButton(isSelected, false),
                        throwable -> Timber.e(throwable, "Error getting selected session state"));
    }

    public void onFloatingActionButtonClicked() {
        sessionsDao.toggleSelectedState(session)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSelected -> {
                            if (isSelected) {
                                sessionsReminder.removeSessionReminder(session);
                                view.showSnackbarMessage(R.string.session_details_removed);
                            } else {
                                sessionsReminder.addSessionReminder(session);
                                view.showSnackbarMessage(R.string.session_details_added);
                            }
                            view.updateFabButton(!isSelected, true);
                        },
                        throwable -> Timber.e(throwable, "Error getting selected session state"));
    }
}
