package com.ionvaranita.belotenote.adapters;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Switch;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.borders.BorderedEditText;
import com.ionvaranita.belotenote.entity.PuncteCastigatoareGlobalBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ionvaranita on 02/04/18.
 */

public class AdapterPuncteCastigatoareGlobal extends RecyclerView.Adapter<AdapterPuncteCastigatoareGlobal.ViewHolder> {
    private BorderedEditText inserimentoPuncteCastigatoare;
    private RadioButton lastCheckedPuncteCastigatoare = null;
    private Switch switchButton;

    private List<PuncteCastigatoareGlobalBean> puncteCastigatoareGlobalBeans;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public AdapterPuncteCastigatoareGlobal(Context context, Switch switchButton, List<PuncteCastigatoareGlobalBean> puncteCastigatoareGlobalBeans) {
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (lastCheckedPuncteCastigatoare != null) {
                        lastCheckedPuncteCastigatoare.setChecked(false);
                        lastCheckedPuncteCastigatoare = null;
                    }
                    inserimentoPuncteCastigatoare.setVisibility(View.VISIBLE);
                    inserimentoPuncteCastigatoare.callOnClick();

                } else {
                    inserimentoPuncteCastigatoare.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.switchButton = switchButton;
        this.puncteCastigatoareGlobalBeans = puncteCastigatoareGlobalBeans;
        mContext = context;

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public AdapterPuncteCastigatoareGlobal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemsPatruJucatoriInEchipaView = inflater.inflate(R.layout.items_puncte_castigatoare_4_jucatori_in_echipa, parent, false);

        // Return a new holder instance
        AdapterPuncteCastigatoareGlobal.ViewHolder viewHolder = new AdapterPuncteCastigatoareGlobal.ViewHolder(itemsPatruJucatoriInEchipaView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterPuncteCastigatoareGlobal.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        PuncteCastigatoareGlobalBean puncteCastigatoareGlobalBean = puncteCastigatoareGlobalBeans.get(position);
//            TextView turul=viewHolder.turul;

//            turul.setText(integerToString(patruJucatoriInEchipa.getTurnul()));
        final RadioButton puncteCastigatoare2 = viewHolder.puncteCastigatoare;

        if (puncteCastigatoareGlobalBean != null) {
            puncteCastigatoare2.setText(puncteCastigatoareGlobalBean.getPuncteCastigatoare().toString());
        }


        puncteCastigatoare2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton group, boolean isChecked) {
                if (isChecked) {
                    if (lastCheckedPuncteCastigatoare
                            != null) {
                        lastCheckedPuncteCastigatoare.setChecked(false);
                    }

                    switchButton.setChecked(false);
                }
                lastCheckedPuncteCastigatoare = puncteCastigatoare2;
            }

        });

    }

    public static String integerToString(Integer integer) {

        if (integer == null) {
            return "-";
        }
        return Integer.toString(integer);
    }


    @Override
    public int getItemCount() {
        return puncteCastigatoareGlobalBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RadioButton puncteCastigatoare;

        public ViewHolder(View itemView) {
            super(itemView);
            puncteCastigatoare = (RadioButton) itemView.findViewById(R.id.radio_button_puncte_castigatoare_4_jucatori_in_echipa);
//                turul = (TextView) itemView.findViewById(R.id.item_turul_patru_jucatori_in_echipa);
            //puncteCastigatoare = (RadioButton) itemView.findViewById(R.id.radio_button_puncte_castigatoare_4_jucatori_in_echipa);

        }
    }
    public BorderedEditText getInserimentoPuncteCastigatoare() {
        return inserimentoPuncteCastigatoare;
    }

    public void setInserimentoPuncteCastigatoare(BorderedEditText inserimentoPuncteCastigatoare) {
        this.inserimentoPuncteCastigatoare = inserimentoPuncteCastigatoare;
    }
    public RadioButton getLastCheckedPuncteCastigatoare() {
        return lastCheckedPuncteCastigatoare;
    }

    public void setLastCheckedPuncteCastigatoare(RadioButton lastCheckedPuncteCastigatoare) {
        this.lastCheckedPuncteCastigatoare = lastCheckedPuncteCastigatoare;
    }
}
