package com.example.myfragment.data;

public interface CardsSource {
    CardData getCardData(int position);
    int size();
}