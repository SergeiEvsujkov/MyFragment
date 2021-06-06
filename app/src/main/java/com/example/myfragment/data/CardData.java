package com.example.myfragment.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CardData implements Parcelable {

    private String id; // идентификатор
    private final String notes;       // заголовок
    private final int picture;        // изображение
    private final String description;
    private Date date; // дата

    public CardData(String notes, int picture, String description, Date date) {

        this.notes = notes;
        this.picture = picture;
        this.description = description;
        this.date = date;

    }


    protected CardData(Parcel in) {

        notes = in.readString();
        picture = in.readInt();
        description = in.readString();
        date = new Date(in.readLong());
    }

    public static final Creator<CardData> CREATOR = new Creator<CardData>() {
        @Override
        public CardData createFromParcel(Parcel in) {
            return new CardData(in);
        }

        @Override
        public CardData[] newArray(int size) {
            return new CardData[size];
        }
    };

    public String getNotes() {
        return notes;
    }

    public int getPicture() {
        return picture;
    }

    public String getDescription() {
        return description;
    }


    public Date getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(notes);
        dest.writeInt(picture);
        dest.writeString(description);
        dest.writeLong(date.getTime());
    }
}
