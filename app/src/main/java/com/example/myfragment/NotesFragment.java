package com.example.myfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfragment.ui.NotesAdapter;
import com.squareup.otto.Subscribe;

import com.example.myfragment.event_bus.EventBus;
import com.example.myfragment.event_bus.events.ButtonClickedEvent;

public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        String[] data = getResources().getStringArray(R.array.notes);
        initRecyclerView(recyclerView, data);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initList(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getBus().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getBus().unregister(this);
        super.onStop();
    }

    private void initRecyclerView(RecyclerView recyclerView, String[] data){

        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        NotesAdapter adapter = new NotesAdapter(data);
        recyclerView.setAdapter(adapter);
    }


    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);


        for (int i = 0; i < notes.length; i++) {
            String note = notes[i];
            TextView tv = new TextView(getContext());

            tv.setText(note);
            tv.setTextSize(30);
            layoutView.addView(tv);
            tv.setTextColor(getResources().getColor(R.color.blue));
            final int fi = i;
            registerForContextMenu(tv);
            tv.setOnClickListener(v -> {
                currentNote = new Note(fi, getResources().getStringArray(R.array.notes)[fi], getResources().getStringArray(R.array.notesBody)[fi], getResources().getStringArray(R.array.notesDate)[fi]);
                showBody(currentNote);
            });
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;


        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(0, getResources().getStringArray(R.array.notes)[0], getResources().getStringArray(R.array.notesBody)[0], getResources().getStringArray(R.array.notesDate)[0]);
        }


        if (isLandscape) {
            showLandBody(currentNote);
        }
    }

    private void showBody(Note currentCity) {
        if (isLandscape) {
            showLandBody(currentNote);
        } else {
            showPortBody(currentNote);
        }
    }


    private void showLandBody(Note currentCity) {
        NotesBodyFragment detail = NotesBodyFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body, detail);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortBody(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NotesBodyActivity.class);
        intent.putExtra(NotesBodyFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }

    @Subscribe
    public void onButtonClickedEvent(ButtonClickedEvent event) {
        Toast.makeText(requireContext(), String.valueOf(event.count),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.popup, menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item1_popup:
                Toast.makeText(getContext(), "Send", Toast.LENGTH_SHORT)
                        .show();
                return true;
            case R.id.item2_popup:
                Toast.makeText(getContext(), "Add photo", Toast.LENGTH_SHORT)
                        .show();
                return true;
        }
        return super.onContextItemSelected(item);
    }
}