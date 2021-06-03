package com.example.myfragment.ui;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.myfragment.MainActivity;
import com.example.myfragment.R;
import com.example.myfragment.data.CardData;
import com.example.myfragment.observer.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;


public class CardFragment extends Fragment {

    private static final String ARG_CARD_DATA = "Param_CardData";
    private CardData cardData; // Данные по карточке
    private Publisher publisher; // Паблишер, с его помощью обмениваемся данными
    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;

    // Для редактирования данных
    public static CardFragment newInstance(CardData cardData) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CARD_DATA, cardData);
        fragment.setArguments(args);
        return fragment;
    }

    // Для добавления новых данных
    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cardData = getArguments().getParcelable(ARG_CARD_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_card, container, false);
        initView(view);
// если cardData пустая, то это добавление
        if (cardData != null) {
            populateView();
        }
        return view;
    }

    // Здесь соберём данные из views
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onStop() {
        super.onStop();
        cardData = collectCardData();
    }

    // Здесь передадим данные в паблишер
    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(cardData);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private CardData collectCardData() {
        String title = this.title.getText().toString();
        Date date = getDateFromDatePicker();
        int picture;
        boolean like;
        if (cardData != null) {
            picture = cardData.getPicture();
        } else {
            picture = R.drawable.mars;
        }
        return new CardData(title,  picture, date);
    }

    // Получение даты из DatePicker
    @RequiresApi(api = Build.VERSION_CODES.N)
    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        datePicker = view.findViewById(R.id.inputDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void populateView() {
        title.setText(cardData.getNotes());
        initDatePicker(cardData.getDate());
    }

    // Установка даты в DatePicker
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }
}
}