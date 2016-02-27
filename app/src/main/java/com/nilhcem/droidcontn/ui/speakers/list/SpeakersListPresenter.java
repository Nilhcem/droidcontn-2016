package com.nilhcem.droidcontn.ui.speakers.list;

import com.nilhcem.droidcontn.data.app.DataProvider;
import com.nilhcem.droidcontn.data.app.model.Speaker;
import com.nilhcem.droidcontn.ui.BaseFragmentPresenter;

import java.util.ArrayList;
import java.util.Collections;

import icepick.State;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class SpeakersListPresenter extends BaseFragmentPresenter<SpeakersListView> {

    @State ArrayList<Speaker> speakers;

    private final DataProvider dataProvider;
    private Subscription speakersSubscription;

    public SpeakersListPresenter(SpeakersListView view, DataProvider dataProvider) {
        super(view);
        this.dataProvider = dataProvider;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (speakers == null) {
            loadData();
        } else {
            this.view.displaySpeakers(speakers);
        }
    }

    @Override
    public void onStop() {
        if (speakersSubscription != null) {
            speakersSubscription.unsubscribe();
        }
        super.onStop();
    }

    public void reloadData() {
        loadData();
    }

    private void loadData() {
        speakersSubscription = dataProvider.getSpeakers()
                .map(speakers -> {
                    if (speakers != null) {
                        Collections.shuffle(speakers);
                    }
                    return speakers;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(speakers -> {
                    this.speakers = new ArrayList<>(speakers);
                    view.displaySpeakers(speakers);
                }, throwable -> Timber.e(throwable, "Error getting speakers"), () -> {
                    if (speakers == null) {
                        view.displayLoadingError();
                    }
                });
    }
}
