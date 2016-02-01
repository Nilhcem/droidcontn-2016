package com.nilhcem.droidcontn.data.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Value;

@Value
public class Session implements Parcelable {

    public static final Parcelable.Creator<Session> CREATOR = new Parcelable.Creator<Session>() {
        public Session createFromParcel(Parcel source) {
            return new Session(source);
        }

        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    int id;
    int slotId;
    String room;
    List<Speaker> speakers;
    String title;
    String description;

    public Session(int id, int slotId, String room, List<Speaker> speakers, String title, String description) {
        this.id = id;
        this.slotId = slotId;
        this.room = room;
        this.speakers = speakers;
        this.title = title;
        this.description = description;
    }

    protected Session(Parcel in) {
        id = in.readInt();
        slotId = in.readInt();
        room = in.readString();
        speakers = in.createTypedArrayList(Speaker.CREATOR);
        title = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(slotId);
        dest.writeString(room);
        dest.writeTypedList(speakers);
        dest.writeString(title);
        dest.writeString(description);
    }
}
