package com.nilhcem.droidcontn.data.provider;

import com.nilhcem.droidcontn.data.api.DroidconService;
import com.nilhcem.droidcontn.data.model.Speaker;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

@Singleton
public class DataProvider {

    private final DroidconService service;
    private final PublishSubject<List<Speaker>> speakersSubject = PublishSubject.create();
    private List<Speaker> speakers;

    @Inject
    public DataProvider(DroidconService service) {
        this.service = service;
    }

    public Observable<List<Speaker>> getSpeakers() {
        if (speakers == null) {
            service.loadSpeakers()
                    .subscribeOn(Schedulers.io())
                    .subscribe(speakers -> {
                        this.speakers = speakers;
                        speakersSubject.onNext(speakers);
                    });
            return speakersSubject;
        } else {
            return Observable.just(speakers);
        }
    }
}
