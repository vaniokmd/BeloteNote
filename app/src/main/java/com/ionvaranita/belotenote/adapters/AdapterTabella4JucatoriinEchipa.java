package com.ionvaranita.belotenote.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.dao.VisualizzazioneBoltDao;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.BoltEntityBean;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.VisualizzazioneBoltEntityBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ionvaranita on 25/11/2017.
 */


// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class AdapterTabella4JucatoriinEchipa extends
        RecyclerView.Adapter<AdapterTabella4JucatoriinEchipa.ViewHolder> {

    private AppDatabase db;
    private List<VisualizzazioneBoltEntityBean> listaBeanBolt;
    private Map<Integer,VisualizzazioneBoltEntityBean> mappaIdRigaBean = new HashMap<>();
    private Integer idGioco;

    // ... view holder defined above...

    // Store a member variable for the contacts

    private List<Punti4GiocatoriInSquadraEntityBean> listaTabella4JucatoriInEchipa;
    // Store the context for easy access
    private Context mContext;

    private TextView noi;
    private TextView voi;

    // Pass in the contact array into the constructor
    public AdapterTabella4JucatoriinEchipa(Context context, List<Punti4GiocatoriInSquadraEntityBean> listaTabella4JucatoriInEchipa, Integer idGioco) {
        this.listaTabella4JucatoriInEchipa = listaTabella4JucatoriInEchipa;
        this.idGioco = idGioco;
        mContext = context;
        db = AppDatabase.getPersistentDatabase(mContext);
        VisualizzazioneBoltDao visualizzazioneBoltDao = db.visualizzazioneBoltDao();
        listaBeanBolt = visualizzazioneBoltDao.getListaBoltByIdGioco(this.idGioco);
        if (listaBeanBolt != null) {
            for (VisualizzazioneBoltEntityBean bean : listaBeanBolt) {
                mappaIdRigaBean.put(bean.getIdRiga(),bean);
            }
        }

    }
    private void valorizzaICampiBolt(){
        Set<Integer> insiemeRigheBolt = mappaIdRigaBean.keySet();
        for (Punti4GiocatoriInSquadraEntityBean bean:
             listaTabella4JucatoriInEchipa) {
            Integer idRigaBean = bean.getId();
            if(insiemeRigheBolt.contains(idRigaBean)){
                //Per la performance!
                insiemeRigheBolt.remove(idRigaBean);

                VisualizzazioneBoltEntityBean visualizzazioneBoltEntityBean = mappaIdRigaBean.get(idRigaBean);

                Integer idPersonaFromBean = visualizzazioneBoltEntityBean.getIdPersona();

                if(idPersonaFromBean==IdsCampiStampa.ID_PUNTI_NOI){
                    bean.setPuntiNoi(ConstantiGlobal.STRING_BOLT);
                }
            }
        }
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemsPatruJucatoriInEchipaView = inflater.inflate(R.layout.items_tabella_4_jucatori_in_echipa, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemsPatruJucatoriInEchipaView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterTabella4JucatoriinEchipa.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Punti4GiocatoriInSquadraEntityBean patruJucatoriInEchipa = listaTabella4JucatoriInEchipa.get(position);
//            TextView turul=viewHolder.turul;

//            turul.setText(integerToString(patruJucatoriInEchipa.getTurno()));

        Integer idRigaBean = patruJucatoriInEchipa.getId();
        noi = viewHolder.noi;
        noi.setText(fixNullNumber(patruJucatoriInEchipa.getPuntiNoi()));
        voi = viewHolder.voi;
        voi.setText(fixNullNumber(patruJucatoriInEchipa.getPuntiVoi()));
        TextView joaca = viewHolder.joaca;
        joaca.setText(fixNullNumber(patruJucatoriInEchipa.getPuntiGioco()));


    }

    private boolean stampaBoltSeEsisteBolt(Integer idRiga){
        VisualizzazioneBoltEntityBean bean = mappaIdRigaBean.get(idRiga);

        if(bean!=null){
            Integer idPersona = bean.getIdPersona();
            if(idPersona==IdsCampiStampa.ID_PUNTI_NOI){
                noi.setText(ConstantiGlobal.STRING_BOLT);
            }
            else if(idPersona==IdsCampiStampa.ID_PUNTI_VOI){
                voi.setText(ConstantiGlobal.STRING_BOLT);
            }
            return true;
        }

    }

    private String fixNullNumber(Integer numero) {
        if (numero == null) {
            return "-";
        } else if (numero.equals(ConstantiGlobal.BOLT_DECIMAL_VALUE)) {
            return "B";
        }
        return numero.toString();
    }


    @Override
    public int getItemCount() {
        return listaTabella4JucatoriInEchipa.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView turul;
        public TextView noi;
        public TextView voi;
        public TextView joaca;

        public ViewHolder(View itemView) {
            super(itemView);
//                turul = (TextView) itemView.findViewById(R.id.item_turul_patru_jucatori_in_echipa);
            noi = (TextView) itemView.findViewById(R.id.item_noi);
            voi = (TextView) itemView.findViewById(R.id.item_voi);
            joaca = (TextView) itemView.findViewById(R.id.item_joaca_patru_jucatori_in_echipa);
        }
    }
}


