package com.example.fragmentscommunication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DynamicViewerFragment extends Fragment {

    private TextView counterTxt;

    public DynamicViewerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        counterTxt = view.findViewById(R.id.counterTxt);
    }

    public void updateCounter(int counter) {
        if (counterTxt != null) {
            counterTxt.setText(String.valueOf(counter));
        }
    }
}
