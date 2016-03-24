package com.example.future.popularmovie;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by future on 09/01/2016.
 */
public class aflamko implements Parcelable {
    private String poster;
    private String title;
    private String view;
    private String vote;
    private String release;
    private String ID;
    private int mData;
    ArrayList<Review> moviehd;
    ArrayList<Tailer>moviefd;

    public int describeContents() {
        return 0;
    }


    public ArrayList<Review> getMoviehd() {
        return moviehd;
    }

    public void setMoviehd(ArrayList<Review> moviehd) {
        this.moviehd = moviehd;
    }

    public void setMoviefd(ArrayList<Tailer> moviefd) {
        this.moviefd = moviefd;
    }

    public ArrayList<Tailer> getMoviefd() {
        return moviefd;
    }

    public void writeToParcel(Parcel out, int flags) {

        out.writeString(ID);
        out.writeString(poster);
        out.writeString(title);
        out.writeString(view);
        out.writeString(vote);
        out.writeString(release);


    }

    private void readFromParcel(Parcel in) {

        ID = in.readString();
        poster = in.readString();
        title = in.readString();
        view = in.readString();
        vote = in.readString();
        release = in.readString();

    }

    public static final Parcelable.Creator<aflamko> CREATOR
            = new Parcelable.Creator<aflamko>() {
        public aflamko createFromParcel(Parcel in) {
            return new aflamko(in);
        }

        public aflamko[] newArray(int size) {
            return new aflamko[size];
        }
    };

    public aflamko(Parcel in) {
        ID = in.readString();

        poster = in.readString();
        title = in.readString();
        view = in.readString();
        vote = in.readString();
        release = in.readString();
    }


    public String getView() {
        return view;
    }

    public String getPoster() {
        return poster;
    }

    public String getVote() {
        return vote;
    }

    public String getRelease() {
        return release;
    }

    public String getTitle() {
        return title;
    }

    public aflamko() {
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getmData() {
        return mData;
    }

    public void setmData(int mData) {
        this.mData = mData;
    }

    public aflamko(String ID, String poster, String title, String view, String vote, String release) {
        this.release = release;
        this.view = view;
        this.vote = vote;
        this.title = title;
        this.poster = poster;
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
    }



