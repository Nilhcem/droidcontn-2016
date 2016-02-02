package com.nilhcem.droidcontn.utils;

import com.nilhcem.droidcontn.BuildConfig;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.TruthJUnit.assume;

public class AppTest {

    @Test
    public void should_return_true_when_api_is_compatible() {
        // Given
        int apiLevelCompatible = android.os.Build.VERSION.SDK_INT;
        int apiLevelBelow = android.os.Build.VERSION.SDK_INT - 1;

        // When
        boolean result1 = App.isCompatible(apiLevelCompatible);
        boolean result2 = App.isCompatible(apiLevelBelow);

        // Then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }

    @Test
    public void should_return_false_when_api_is_incompatible() {
        // Given
        int apiLevelIncompatible = android.os.Build.VERSION.SDK_INT + 1;

        // When
        boolean result = App.isCompatible(apiLevelIncompatible);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    public void should_return_formatted_string_version() {
        // Given
        assume().withFailureMessage("Do not test internal builds").that(BuildConfig.INTERNAL_BUILD).isFalse();
        String expected = BuildConfig.VERSION_NAME + " (#" + BuildConfig.VERSION_CODE + ")";

        // When
        String version = App.getVersion();

        // Then
        assertThat(version).isEqualTo(expected);
    }
}
