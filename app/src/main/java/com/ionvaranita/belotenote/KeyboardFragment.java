package com.ionvaranita.belotenote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class KeyboardFragment extends Fragment {

    public KeyboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View keyboardView = inflater.inflate(R.layout.fragment_keyboard, container, false);
        return keyboardView;
    }
}
