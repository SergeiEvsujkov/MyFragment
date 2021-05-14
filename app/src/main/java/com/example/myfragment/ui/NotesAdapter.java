package com.example.myfragment.ui;

import android.annotation.SuppressLint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import androidx.appcompat.widget.AppCompatImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfragment.NotesFragment;
import com.example.myfragment.R;
import com.example.myfragment.data.CardData;
import com.example.myfragment.data.CardsSource;

public class NotesAdapter
        extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private OnItemClickListener itemClickListener;
    private final static String TAG = "NotesAdapter";
    private final CardsSource dataSource;


    // Передаём в конструктор источник данных
    // В нашем случае это массив, но может быть и запрос к БД
    public NotesAdapter(CardsSource dataSource) {
        this.dataSource = dataSource;
    }

    // Создать новый элемент пользовательского интерфейса
    // Запускается менеджером
    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);
        Log.d(TAG, "onCreateViewHolder");
        // Здесь можно установить всякие параметры
        return new ViewHolder(v);
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder viewHolder, int i) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder

        viewHolder.setData(dataSource.getCardData(i));
        Log.d(TAG, "onBindViewHolder");

    }

    // Вернуть размер данных, вызывается менеджером
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    // Этот класс хранит связь между данными и элементами View
    // Сложные данные могут потребовать несколько View на один пункт списка
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {



        private final TextView notes;

        private final AppCompatImageView image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            notes = itemView.findViewById(R.id.textView);

            image = itemView.findViewById(R.id.imageView);



            itemView.setOnCreateContextMenuListener(this);
            // Обработчик нажатий на этом ViewHolder
           image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }


        public void setData(CardData cardData){
            notes.setText(cardData.getNotes());
            image.setImageResource(cardData.getPicture());
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.popup, menu);
        }
    }


    // Сеттер слушателя нажатий
    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    // Интерфейс для обработки нажатий, как в ListView
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}

