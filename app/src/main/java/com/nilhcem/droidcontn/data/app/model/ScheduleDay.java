package com.nilhcem.droidcontn.data.app.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.Value;

@Value
public class ScheduleDay implements Parcelable {

    public static final Parcelable.Creator<ScheduleDay> CREATOR = new Parcelable.Creator<ScheduleDay>() {
        public ScheduleDay createFromParcel(Parcel source) {
            return new ScheduleDay(source);
        }

        public ScheduleDay[] newArray(int size) {
            return new ScheduleDay[size];
        }
    };

    String day;
    List<Slot> slots;

    public ScheduleDay(String day, List<Slot> slots) {
        this.day = day;
        this.slots = slots;
    }

    protected ScheduleDay(Parcel in) {
        day = in.readString();
        slots = in.createTypedArrayList(Slot.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeTypedList(slots);
    }
}
