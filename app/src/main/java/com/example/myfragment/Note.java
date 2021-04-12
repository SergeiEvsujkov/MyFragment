package com.example.myfragment;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {
    private int imageIndex;
    private String noteName;

    public Note(int imageIndex, String noteName){
        this.imageIndex = imageIndex;
        this.noteName = noteName;
    }

    protected Note(Parcel in) {
        imageIndex = in.readInt();
        noteName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getImageIndex());
        dest.writeString(getNoteName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getImageIndex() {
        return imageIndex;
    }

    public String getNoteName() {
        return noteName;
    }
}
