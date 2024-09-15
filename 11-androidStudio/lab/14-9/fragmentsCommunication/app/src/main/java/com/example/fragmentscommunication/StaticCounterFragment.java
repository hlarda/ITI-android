package com.example.fragmentscommunication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class StaticCounterFragment extends Fragment {
    private static final String COUNTER_KEY = "counter";
    private int counter = 0;
    private Button counterBtn;

    Communicator communicator;

    public StaticCounterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_static_counter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt(COUNTER_KEY, 0);
        }
        communicator = (Communicator) getActivity();
        counterBtn = view.findViewById(R.id.counterBtn);

        counterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                communicator.respond(counter);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNTER_KEY, counter);
    }

    public int getCounter() {
        return counter;
    }
}
