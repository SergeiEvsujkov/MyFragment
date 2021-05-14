package com.example.myfragment.data;

public class CardData {
    private final String notes;       // заголовок
    private final int picture;        // изображение


    public CardData(String notes, int picture) {
        this.notes = notes;
        this.picture = picture;
    }

    public String getNotes() {
        return notes;
    }


    public int getPicture() {
        return picture;
    }

}
