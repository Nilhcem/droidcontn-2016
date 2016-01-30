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

    int slotId;
    int roomId;
    int sessionId;
    List<Speaker> speakers;
    String title;
    String description;

    public Session(int slotId, int roomId, int sessionId, List<Speaker> speakers, String title, String description) {
        this.slotId = slotId;
        this.roomId = roomId;
        this.sessionId = sessionId;
        this.speakers = speakers;
        this.title = title;
        this.description = description;
    }

    protected Session(Parcel in) {
        slotId = in.readInt();
        roomId = in.readInt();
        sessionId = in.readInt();
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
        dest.writeInt(slotId);
        dest.writeInt(roomId);
        dest.writeInt(sessionId);
        dest.writeTypedList(speakers);
        dest.writeString(title);
        dest.writeString(description);
    }
}
