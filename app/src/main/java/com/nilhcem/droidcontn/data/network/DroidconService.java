package com.nilhcem.droidcontn.data.network;

import com.nilhcem.droidcontn.data.network.model.ScheduleDay;
import com.nilhcem.droidcontn.data.network.model.Speaker;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface DroidconService {

    @GET("schedule")
    Observable<List<ScheduleDay>> loadSchedule();

    @GET("speakers")
    Observable<List<Speaker>> loadSpeakers();
}
