package com.ionvaranita.belotenote.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.dao.VisualizzazioneBoltDao;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.VincitoreBean;
import com.ionvaranita.belotenote.entity.VisualizzazioneBoltEntityBean;
import com.ionvaranita.belotenote.traduttori.impl.EntityBeanToViewImpl;
import com.ionvaranita.belotenote.view.Punti4GiocatoriInSquadraView;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by ionvaranita on 25/11/2017.
 */


// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class AdapterTabella4JucatoriinEchipa extends
        RecyclerView.Adapter<AdapterTabella4JucatoriinEchipa.ViewHolder> {
    private static final int NESSUNA_PARTIDA=-1;
    private static final int PARTIDA_NON_ANCORA_FINITA=-2;
    private static final Logger LOG = Logger.getLogger(AdapterTabella4JucatoriinEchipa.class.getName());
    private AppDatabase db;
    private Integer idGioco;
    private Integer idPartidaSelector;
    private int nrPartide;


    private List<Punti4GiocatoriInSquadraView> punti4GiocatoriInSquadraViewList;
    // Store the context for easy access
    private Context mContext;

    private TextView noi;
    private TextView voi;
    private  Map<Integer,String> mappaIdPartidaVincitore;

    // Pass in the contact array into the constructor
    public AdapterTabella4JucatoriinEchipa(Context context, List<Punti4GiocatoriInSquadraEntityBean> listaTabella4JucatoriInEchipa, Integer idGioco) {
        this.idGioco = idGioco;
        mContext = context;
        db = AppDatabase.getPersistentDatabase(mContext);
        VisualizzazioneBoltDao visualizzazioneBoltDao = db.visualizzazioneBoltDao();
        List<VisualizzazioneBoltEntityBean> listaBeanBolt = visualizzazioneBoltDao.getListaBoltByIdGioco(this.idGioco);
        EntityBeanToViewImpl entityBeanToView = new EntityBeanToViewImpl();
        List<VincitoreBean> vincitoreBeans = db.vincitoreDao().getListVincitoriByIdGioco(this.idGioco);
        mappaIdPartidaVincitore = entityBeanToView.getMappaVicitori(context,vincitoreBeans);
        punti4GiocatoriInSquadraViewList = entityBeanToView.getAllPunti4GiocatoriInSquadraView(listaTabella4JucatoriInEchipa, listaBeanBolt);

    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    // Usually involves inflating a layout from XML and returning the holder

    private ViewHolder viewHolder = null;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemsPatruJucatoriInEchipaView = inflater.inflate(R.layout.items_tabella_4_jucatori_in_echipa, parent, false);

        // Return a new holder instance
//        if ((viewType != this.idPartidaSelector&&viewType!=PARTIDA_NON_ANCORA_FINITA)) {
//            idPartidaSelector = viewType;
//
//            return new ViewHolder(inflater.inflate(R.layout.item_risultato_partida,parent,false),mappaIdPartidaVincitore.get(idPartidaSelector));
//
//        }

        return new ViewHolder(itemsPatruJucatoriInEchipaView);
    }

    @Override
    public void onBindViewHolder(AdapterTabella4JucatoriinEchipa.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraView = punti4GiocatoriInSquadraViewList.get(position);
        if(idPartidaSelector!=punti4GiocatoriInSquadraView.getIdPartida()){
            if(idPartidaSelector!=null){
                TableRow rigaPunti=viewHolder.rigaPunti;

                Paint paint = new Paint();
                paint.setColor(Color.RED);

                Canvas canvas = new Canvas();

                canvas.drawLine(rigaPunti.getWidth(), 0, rigaPunti.getWidth(), rigaPunti.getHeight(), paint);
                rigaPunti.setBackgroundColor(Color.RED);
                rigaPunti.draw(canvas);
            }
            idPartidaSelector = punti4GiocatoriInSquadraView.getIdPartida();
        }
        String vincitore = viewHolder.vincitore;
        if(vincitore==null){
            noi = viewHolder.noi;
            noi.setText(punti4GiocatoriInSquadraView.getPuntiNoi());
            voi = viewHolder.voi;
            voi.setText(punti4GiocatoriInSquadraView.getPuntiVoi());
            TextView joaca = viewHolder.joaca;
            joaca.setText(punti4GiocatoriInSquadraView.getPuntiGioco());
        }




    }

    @Override
    public int getItemCount() {
        return punti4GiocatoriInSquadraViewList.size();
    }

    @Override
    public int getItemViewType(int position) {

//        Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraView = punti4GiocatoriInSquadraViewList.get(position);
//
//        if(!punti4GiocatoriInSquadraView.getFinePartida()){
//            return PARTIDA_NON_ANCORA_FINITA;
//        }
//        if (punti4GiocatoriInSquadraView != null && punti4GiocatoriInSquadraView.getIdPartida() != null&&punti4GiocatoriInSquadraView.getFinePartida()) {
//            int idPartidaCorrente = punti4GiocatoriInSquadraView.getIdPartida();
//            if(idPartidaSelector!=idPartidaCorrente){
//                idPartidaSelector
//            }
//            return idPartidaCorrente;
//        }
        return NESSUNA_PARTIDA;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TableRow rigaPunti;
        public String vincitore;
        public TextView risultatoPartida;
        public TextView turul;
        public TextView noi;
        public TextView voi;
        public TextView joaca;

        public ViewHolder(View itemView) {
            super(itemView);
//            if(vincitore!=null){
//                risultatoPartida =itemView.findViewById(R.id.tv_risultato_partida);
//                risultatoPartida.setText(risultatoPartida.getText().toString()+vincitore);
//            }

                noi = (TextView) itemView.findViewById(R.id.item_noi);
                voi = (TextView) itemView.findViewById(R.id.item_voi);
                joaca = (TextView) itemView.findViewById(R.id.item_joaca_patru_jucatori_in_echipa);
                rigaPunti=itemView.findViewById(R.id.items_patru_jucatori_in_echipa_tw);


        }
    }
}


