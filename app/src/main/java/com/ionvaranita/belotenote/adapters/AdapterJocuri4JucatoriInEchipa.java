package com.ionvaranita.belotenote.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.TabellaPunti;
import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.ionvaranita.belotenote.R.id.item_data_partida_patru_jucatori_in_echipa;
import static com.ionvaranita.belotenote.R.id.item_nume_partida_patru_jucatori_in_echipa;

/**
 * Created by ionvaranita on 12/12/2017.
 */

public class AdapterJocuri4JucatoriInEchipa extends
        RecyclerView.Adapter<AdapterJocuri4JucatoriInEchipa.ViewHolder> {


    private List<Gioco4GiocatoriInSquadra> itemsGioco4GiocatoriInSquadras;
    // Store the context for easy access
    private Context mContext;

    public AdapterJocuri4JucatoriInEchipa(Context context, List<Gioco4GiocatoriInSquadra> itemsGioco4GiocatoriInSquadras) {
        this.itemsGioco4GiocatoriInSquadras = itemsGioco4GiocatoriInSquadras;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public AdapterJocuri4JucatoriInEchipa.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemsMenuPatruJucatoriInEchipaView = inflater.inflate(R.layout.items_menu_partide_4_jucatori_in_echipa, parent, false);

        // Return a new holder instance
        AdapterJocuri4JucatoriInEchipa.ViewHolder viewHolder = new AdapterJocuri4JucatoriInEchipa.ViewHolder(itemsMenuPatruJucatoriInEchipaView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterJocuri4JucatoriInEchipa.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        final Gioco4GiocatoriInSquadra partidaPatruJucatoriInEchipaEntityBean = itemsGioco4GiocatoriInSquadras.get(position);

        viewHolder.bind(partidaPatruJucatoriInEchipaEntityBean);


        final TextView numePartida = viewHolder.numePartida;

        numePartida.setText(partidaPatruJucatoriInEchipaEntityBean.getNumeGioco());


        TextView dataPartida = viewHolder.dataPartida;

        long dataInserimentoPartida = partidaPatruJucatoriInEchipaEntityBean.getDataGioco();


        dataPartida.setText(getDataPartidaFormated(dataInserimentoPartida));


    }

    private String getDataPartidaFormated(long dataInserimentoPartida) {
        Calendar cIeri = Calendar.getInstance(); // today
        cIeri.add(Calendar.DAY_OF_YEAR, -1); // yesterday

        Calendar cDatabase = Calendar.getInstance();
        cDatabase.setTime(new Date(dataInserimentoPartida)); // your date

        boolean isIeri = cIeri.get(Calendar.YEAR) == cDatabase.get(Calendar.YEAR)
                && cIeri.get(Calendar.DAY_OF_YEAR) == cDatabase.get(Calendar.DAY_OF_YEAR);

        Calendar cAlaltaieri = Calendar.getInstance();
        cAlaltaieri.add(Calendar.DAY_OF_YEAR, -2);

        boolean isAlaltaieri = cAlaltaieri.get(Calendar.YEAR) == cDatabase.get(Calendar.YEAR)
                && cAlaltaieri.get(Calendar.DAY_OF_YEAR) == cDatabase.get(Calendar.DAY_OF_YEAR);

        Calendar cAstazi = Calendar.getInstance();

        boolean isAstazi = cAstazi.get(Calendar.YEAR) == cDatabase.get(Calendar.YEAR)
                && cAstazi.get(Calendar.DAY_OF_YEAR) == cDatabase.get(Calendar.DAY_OF_YEAR);

        String nomeGiorno = null;
        String data = null;
        if (isAstazi) {
            nomeGiorno = getContext().getString(R.string.astazi);
        } else if (isIeri) {
            nomeGiorno = getContext().getResources().getString(R.string.ieri);
        } else if (isAlaltaieri) {
            nomeGiorno = getContext().getResources().getString(R.string.alaltaieri);
        } else {
            nomeGiorno = new SimpleDateFormat("E").format(new java.util.Date(dataInserimentoPartida));
        }
        data = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(dataInserimentoPartida));


        String dataPartidaFormated = nomeGiorno + "\n" + data;
        return dataPartidaFormated;
    }


    @Override
    public int getItemCount() {
        return itemsGioco4GiocatoriInSquadras.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView numePartida;
        public TextView dataPartida;
        public TableRow tableRow;

        public ViewHolder(View itemView) {
            super(itemView);
            tableRow = (TableRow) itemView.findViewById(R.id.table_row_items_partide_patru_jucatori_in_echipa);
            numePartida = (TextView) itemView.findViewById(item_nume_partida_patru_jucatori_in_echipa);
            dataPartida = (TextView) itemView.findViewById(item_data_partida_patru_jucatori_in_echipa);
        }

        public void bind(final Gioco4GiocatoriInSquadra gioco4GiocatoriInSquadra) {
            tableRow.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(tableRow.getContext(), TabellaPunti.class);
                    intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), gioco4GiocatoriInSquadra.getIdGioco());
                    intent.putExtra(ConstantiGlobal.ACTION_CODE, ActionCode.GIOCATORI_4_IN_SQUADRA);
                    tableRow.getContext().startActivity(intent);
                }
            });
            //tableRow.setTouchscreenBlocksFocus(true);

        }
    }

}

