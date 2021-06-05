package com.example.myfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myfragment.data.CardData;
import com.example.myfragment.event_bus.EventBus;

import com.example.myfragment.event_bus.events.ButtonClickedEvent;

public class NotesBodyFragment extends Fragment {

    public static final String ARG_CARD_DATA = "card_data";
    private CardData cardData;

    public static NotesBodyFragment newInstance(CardData cardData) {
        NotesBodyFragment f = new NotesBodyFragment();    // создание
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_body, container, false);
        TextView noteNameView = view.findViewById(R.id.textView);
        TextView noteBodyView = view.findViewById(R.id.textBody);
        TextView noteDateView = view.findViewById(R.id.textDate);
        noteNameView.setText(cardData.getNotes());
        noteBodyView.setText(cardData.getDescription());
        noteDateView.setText(cardData.getDate().toString());

        noteNameView.setOnClickListener(v -> EventBus.getBus().post(new ButtonClickedEvent(5)));
        return view;
    }
}