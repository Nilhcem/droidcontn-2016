package com.nilhcem.droidcontn.core.dagger.module;

import com.nilhcem.droidcontn.data.api.DroidconService;
import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.MoshiConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

@Module
public final class ApiModule {

    @Provides @Singleton HttpUrl provideBaseUrl() {
        return HttpUrl.parse("https://raw.githubusercontent.com/Nilhcem/droidcontn-2016/master/assets/api/");
    }

    @Provides @Singleton Retrofit provideRetrofit(HttpUrl baseUrl, OkHttpClient client, Moshi moshi) {
        return new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides @Singleton DroidconService provideDroidconService(Retrofit retrofit) {
        return retrofit.create(DroidconService.class);
    }
}
