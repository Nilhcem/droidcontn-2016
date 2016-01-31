package com.nilhcem.droidcontn.data.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import lombok.Value;

@Value
public class Slot implements Parcelable {

    public static final Parcelable.Creator<Slot> CREATOR = new Parcelable.Creator<Slot>() {
        public Slot createFromParcel(Parcel source) {
            return new Slot(source);
        }

        public Slot[] newArray(int size) {
            return new Slot[size];
        }
    };

    int id;
    LocalDateTime fromTime;
    LocalDateTime toTime;
    List<Session> sessions;

    public Slot(int id, LocalDateTime fromTime, LocalDateTime toTime, List<Session> sessions) {
        this.id = id;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.sessions = sessions;
    }

    protected Slot(Parcel in) {
        id = in.readInt();
        fromTime = (LocalDateTime) in.readSerializable();
        toTime = (LocalDateTime) in.readSerializable();
        sessions = in.createTypedArrayList(Session.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeSerializable(fromTime);
        dest.writeSerializable(toTime);
        dest.writeTypedList(sessions);
    }
}
