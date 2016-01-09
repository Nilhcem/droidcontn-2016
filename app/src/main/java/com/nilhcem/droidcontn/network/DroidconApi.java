package com.nilhcem.droidcontn.network;

import com.nilhcem.droidcontn.model.Schedule;
import com.nilhcem.droidcontn.model.Speaker;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface DroidconApi {

    @GET("schedule")
    Observable<List<Schedule>> loadSchedule();

    @GET("speakers")
    Observable<List<Speaker>> loadSpeakers();
}
