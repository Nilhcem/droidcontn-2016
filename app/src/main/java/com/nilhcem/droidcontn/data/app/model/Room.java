package com.nilhcem.droidcontn.data.app.model;

import android.support.annotation.NonNull;

public enum Room {

    NONE(0, ""),
    MAIN_CONFERENCE(1, "Main Conference Room \"Hannibal\""),
    AMPH_CESAR(2, "Cesar Amphitheater"),
    CESAR_1(3, "César 1 Conference Room"),
    OLIVIER(4, "Olivier Conference Room"),
    CESAR_4(5, "César 4 (Workshops)"),
    CESAR_6_GAMING(6, "César 6 (Gaming)"),
    CESAR_6_INTEL(7, "César 6 (Intel)"),
    CESAR_3(8, "César 3");

    public final int id;
    public final String name;

    Room(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Room getFromId(int id) {
        for (Room room : Room.values()) {
            if (room.id == id) {
                return room;
            }
        }
        return NONE;
    }

    public static Room getFromName(@NonNull String name) {
        for (Room room : Room.values()) {
            if (name.equals(room.name)) {
                return room;
            }
        }
        return NONE;
    }
}
