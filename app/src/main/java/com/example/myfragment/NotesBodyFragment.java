package com.example.myfragment;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.geekbrains.cityheraldry.event_bus.EventBus;
import ru.geekbrains.cityheraldry.event_bus.events.ButtonClickedEvent;

public class NotesBodyFragment extends Fragment {

    public static final String ARG_NOTE = "note";
    private Note note;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static NotesBodyFragment newInstance(Note note) {
        NotesBodyFragment f = new NotesBodyFragment();    // создание

        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_notes_body, container, false);
        // найти в контейнере элемент-изображение
        AppCompatImageView imageBody = view.findViewById(R.id.body);
        // Получить из ресурсов массив указателей на изображения гербов
        TypedArray images = getResources().obtainTypedArray(R.array.body_imgs);
        // Выбрать по индексу подходящий
        imageBody.setImageResource(images.getResourceId(note.getImageIndex(), -1));
        // Установить название города
        TextView noteNameView = view.findViewById(R.id.textView);
        noteNameView.setText(note.getNoteName());
        noteNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getBus().post(new ButtonClickedEvent(5));
            }
        });
        return view;
    }
}