package com.ionvaranita.belotenote.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ionvaranita.belotenote.R;

import java.util.logging.Logger;


public class FragmentInserisciUnTurnNou4JucatoriInEchipa extends Fragment {
    private final Logger LOG = Logger.getLogger(this.getClass().getSimpleName());
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LOG.info("onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG.info("onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LOG.info("onCreateView: ");
        return inflater.inflate(R.layout.fragment_inserisci_un_turn_nou4_jucatori_in_echipa, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LOG.info("onViewCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        LOG.info("onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        LOG.info("onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        LOG.info("onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        LOG.info("onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOG.info("onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LOG.info("onDestroyView: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LOG.info("onDetach: ");
    }
}
