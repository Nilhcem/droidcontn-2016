package com.nilhcem.droidcontn.ui.speakers.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nilhcem.droidcontn.data.provider.DataProvider;
import com.nilhcem.droidcontn.ui.BaseFragmentPresenter;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SpeakersListPresenter extends BaseFragmentPresenter<SpeakersListView> {

    private final DataProvider dataProvider;
    private Subscription speakersSubscription;

    public SpeakersListPresenter(SpeakersListView view, DataProvider dataProvider) {
        super(view);
        this.dataProvider = dataProvider;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        speakersSubscription = dataProvider.getSpeakers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.view::displaySpeakers);
    }

    @Override
    public void onDestroyView() {
        speakersSubscription.unsubscribe();
        super.onDestroyView();
    }
}
