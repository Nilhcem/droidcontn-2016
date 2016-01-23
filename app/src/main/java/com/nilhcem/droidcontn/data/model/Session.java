package com.nilhcem.droidcontn.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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

    int roomId;
    List<Integer> speakersId;
    String title;
    String description;

    protected Session(Parcel in) {
        roomId = in.readInt();
        speakersId = new ArrayList<>();
        in.readList(speakersId, List.class.getClassLoader());
        title = in.readString();
        description = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(roomId);
        dest.writeList(speakersId);
        dest.writeString(title);
        dest.writeString(description);
    }
}
