package com.nilhcem.droidcontn.utils;

import com.nilhcem.droidcontn.BuildConfig;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class AppUtilsTest {

    @Test
    public void should_return_true_when_api_is_compatible() {
        // Given
        int apiLevelCompatible = android.os.Build.VERSION.SDK_INT;
        int apiLevelBelow = android.os.Build.VERSION.SDK_INT - 1;

        // When
        boolean result1 = AppUtils.isCompatible(apiLevelCompatible);
        boolean result2 = AppUtils.isCompatible(apiLevelBelow);

        // Then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }

    @Test
    public void should_return_false_when_api_is_incompatible() {
        // Given
        int apiLevelIncompatible = android.os.Build.VERSION.SDK_INT + 1;

        // When
        boolean result = AppUtils.isCompatible(apiLevelIncompatible);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    public void should_return_formatted_string_version() {
        // Given
        String expected = BuildConfig.VERSION_NAME + " (#" + BuildConfig.VERSION_CODE + ")";

        // When
        String version = AppUtils.getVersion();

        // Then
        assertThat(version).isEqualTo(expected);
    }
}
