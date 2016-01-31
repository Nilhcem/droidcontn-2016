package com.nilhcem.droidcontn.core.moshi;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

public class LocalTimeAdapter {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.US);

    @ToJson String toJson(LocalTime time) {
        return time.format(formatter);
    }

    @FromJson LocalTime fromJson(String json) {
        return LocalTime.parse(json, formatter);
    }
}
