package com.example.myfragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
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
}