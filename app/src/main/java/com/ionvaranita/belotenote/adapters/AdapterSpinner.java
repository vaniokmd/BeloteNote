package com.ionvaranita.belotenote.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.entity.PuncteCastigatoareGlobalBean;

import java.util.List;
public class AdapterSpinner extends ArrayAdapter<PuncteCastigatoareGlobalBean> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private List<PuncteCastigatoareGlobalBean> values;

    public AdapterSpinner(Context context, int textViewResourceId,
                          List<PuncteCastigatoareGlobalBean> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
        this.setDropDownViewResource(R.layout.item_spinner_dropdown_puncte_castigatoare);
    }

    @Override
    public int getCount(){
        return values.size();
    }

    @Override
    public PuncteCastigatoareGlobalBean getItem(int position){
        return values.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values.get(position).getPuncteCastigatoare().toString());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getPuncteCastigatoare().toString());

        return label;
    }
}

