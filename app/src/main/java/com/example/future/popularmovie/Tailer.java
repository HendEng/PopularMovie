package com.example.future.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by future on 23/01/2016.
 */
public class Tailer implements Parcelable {

    private String TailerID;
    private String TailerName;

    public String getTailerName() {
        return TailerName;
    }

    public void setTailerName(String tailerName) {
        TailerName = tailerName;
    }

    protected Tailer(Parcel in) {
        TailerID = in.readString(); TailerName = in.readString();
    }

    public static final Creator<Tailer> CREATOR = new Creator<Tailer>() {
        @Override
        public Tailer createFromParcel(Parcel in) {
            return new Tailer(in);
        }

        @Override
        public Tailer[] newArray(int size) {
            return new Tailer[size];
        }
    };

    public String getTailerID() {
        return TailerID;
    }

    public void setTailerID(String tailerID) {
        TailerID = tailerID;
    }

    public Tailer() {
    }

    public Tailer(String TailerID,String TailerName) {
        this.TailerID = TailerID;
        this.TailerName=TailerName;


    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TailerID); dest.writeString(TailerName);
    }
}
