package com.nilhcem.droidcontn.data.app.model;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class RoomTest {

    @Test
    public void should_get_room_for_a_given_id() {
        // Given
        int id = Room.CESAR_2.id;

        // When
        Room result = Room.getFromId(id);

        // Then
        assertThat(result).isEqualTo(Room.CESAR_2);
    }

    @Test
    public void should_return_the_default_room_for_a_given_invalid_id() {
        // Given
        int id = -1;

        // When
        Room result = Room.getFromId(id);

        // Then
        assertThat(result).isEqualTo(Room.NONE);
    }

    @Test
    public void should_get_room_for_a_given_name() {
        // Given
        String name = Room.CESAR_1.name;

        // When
        Room result = Room.getFromName(name);

        // Then
        assertThat(result).isEqualTo(Room.CESAR_1);
    }

    @Test
    public void should_return_the_default_room_for_a_given_invalid_name() {
        // Given
        String name = "INVALID";

        // When
        Room result = Room.getFromName(name);

        // Then
        assertThat(result).isEqualTo(Room.NONE);
    }
}
