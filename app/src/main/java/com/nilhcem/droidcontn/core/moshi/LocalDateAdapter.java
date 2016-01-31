package com.nilhcem.droidcontn.core.moshi;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

public class LocalDateAdapter {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);

    @ToJson String toJson(LocalDate date) {
        return date.format(formatter);
    }

    @FromJson LocalDate fromJson(String json) {
        return LocalDate.parse(json, formatter);
    }
}
