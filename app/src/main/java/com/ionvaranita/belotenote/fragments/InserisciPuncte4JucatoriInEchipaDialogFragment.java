package com.ionvaranita.belotenote.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ionvaranita.belotenote.R;

/**
 * Created by ionvaranita on 17/03/2018.
 */

public class InserisciPuncte4JucatoriInEchipaDialogFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Set a theme on the dialog builder constructor!
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity(), R.style.AppTheme);
        View view = getActivity().getLayoutInflater().inflate(R.layout.popup_window_inserisci_puncte_4_jucatori_in_echipa, null);
        builder.setView(view);
        builder.setTitle("Your title").setMessage("Your message");
        return builder.create();
    }
}
