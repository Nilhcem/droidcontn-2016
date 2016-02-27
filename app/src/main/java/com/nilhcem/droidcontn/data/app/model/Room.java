package com.nilhcem.droidcontn.data.app.model;

import android.support.annotation.NonNull;

public enum Room {

    NONE(0, ""),
    MAIN_CONFERENCE(1, "Main Conference Room \"Hannibal\""),
    AMPH_CESAR(2, "Amph césar"),
    CESAR_1(3, "César 1"),
    OLIVIER(4, "Olivier"),
    CESAR_2(5, "César 2 (Workshops)"),
    ROOM_1_GAMING(6, "Room 1 (Gaming)"),
    ROOM_1_INTEL(7, "Room 1 (Intel)"),
    ROOM_3(8, "Room 3");

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
