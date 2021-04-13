package com.example.myfragment;

import android.os.Parcel;
import android.os.Parcelable;



public class Note implements Parcelable {
    private int imageIndex;
    private String noteName;
    private String noteText;
    private String noteDate;

    public Note(int imageIndex, String noteName, String noteText, String noteDate){
        this.imageIndex = imageIndex;
        this.noteName = noteName;
        this.noteText = noteText;
        this.noteDate = noteDate;
    }


    protected Note(Parcel in) {
        imageIndex = in.readInt();
        noteName = in.readString();
        noteText = in.readString();
        noteDate = in.readString();
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

    public String getNoteText() {
        return noteText;
    }

    public String getNoteDate() {
        return noteDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageIndex);
        dest.writeString(noteName);
        dest.writeString(noteText);
        dest.writeString(noteDate);
    }
}
