package com.nilhcem.droidcontn.ui.sessions.details;

import com.nilhcem.droidcontn.data.database.dao.SelectedSessionsDao;
import com.nilhcem.droidcontn.ui.BaseActivityPresenter;

public class SessionDetailsPresenter extends BaseActivityPresenter<SessionDetailsView> {

    public SessionDetailsPresenter(SessionDetailsView view, SelectedSessionsDao selectedSessionsDao) {
        super(view);
    }
}
