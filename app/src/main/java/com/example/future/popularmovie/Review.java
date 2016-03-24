package com.example.future.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by future on 23/01/2016.
 */
public class Review implements Parcelable{

    private String outher;
    private String Content;

    public Review(){
    }

    protected Review(Parcel in) {
        outher = in.readString();
        Content = in.readString();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public void setOuther(String outher) {
        this.outher = outher;
    }
    public void setContent(String Content) {
        Content = Content;
    }
    public String getOuther() {
        return outher;
    }
    public String getContent() {
        return Content;
    }
    public Review (String outher ,String Content  ){
    this.outher=outher;
    this.Content=Content;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(outher);
        dest.writeString(Content);
    }
}
