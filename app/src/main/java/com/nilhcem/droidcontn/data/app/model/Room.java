package com.nilhcem.droidcontn.data.app.model;

public enum Room {

    NONE(0, ""),
    ROOM_1(1, "Room #1"),
    ROOM_2(2, "Room #2");

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
}
