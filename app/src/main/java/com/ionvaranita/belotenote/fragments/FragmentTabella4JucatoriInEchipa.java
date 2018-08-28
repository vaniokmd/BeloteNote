package com.ionvaranita.belotenote.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.adapters.AdapterTabella4JucatoriinEchipa;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.Scor4JucatoriInEchipaEntityBean;
import com.ionvaranita.belotenote.entity.TurnManagement4GiocatoriInSquadra;

import java.util.List;
import java.util.logging.Logger;

public class FragmentTabella4JucatoriInEchipa extends Fragment {

    private AppDatabase db;
    private TextView noiPatruJucatoriInEchipa;
    private TextView voiPatruJucatoriInEchipa;
    private List<Punti4GiocatoriInSquadraEntityBean> lista4JucatoriInEchipa;
    private RecyclerView patruJucatoriInEchipaRecycleView;

    private Integer idPartida;
    private Integer idGioco;
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
        LOG.info("" + this.getClass().getCanonicalName() + "->onCreateView()");

        View fragmentTabella4JucatoriInEchipa = inflater.inflate(R.layout.fragment_tabella_4_jucatori_in_echipa, container, false);


        db = AppDatabase.getPersistentDatabase(fragmentTabella4JucatoriInEchipa.getContext());

        noiPatruJucatoriInEchipa = (TextView) fragmentTabella4JucatoriInEchipa.findViewById(R.id.noi_patru_jucatori_in_echipa);

        voiPatruJucatoriInEchipa = (TextView) fragmentTabella4JucatoriInEchipa.findViewById(R.id.voi_patru_jucatori_in_echipa);

        if (idGioco != null) {
            TurnManagement4GiocatoriInSquadra turulBean = db.turnManagement4GiocatoriInSquadraDao().selectTurn4JucatoriInEchipaByIdJoc(idGioco);

            String turulPresent = turulBean.getTurnoPresente();


            int colorTurNoi = Color.rgb(163, 123, 69);
            int colorTurVoi = Color.rgb(49, 112, 88);

            if (turulPresent.equals(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.toString())) {
                String bulletString = "\u2022" + noiPatruJucatoriInEchipa.getText();
                noiPatruJucatoriInEchipa.setText(bulletString);
                noiPatruJucatoriInEchipa.setTextColor(colorTurNoi);
//            noiPatruJucatoriInEchipa.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            } else if (turulPresent.equals(Turnul4GiocatoriInSquadraEnum.TURNUL_VOI.toString())) {
                voiPatruJucatoriInEchipa.setTextColor(colorTurVoi);
//            voiPatruJucatoriInEchipa.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                String bulletString = "\u2022" + voiPatruJucatoriInEchipa.getText();
                voiPatruJucatoriInEchipa.setText(bulletString);

            }

            try {
                if (idGioco == 0) throw new Exception();
                lista4JucatoriInEchipa = db.tabellaPunti4GiocatoriInSquadraDao().selectAllPunti4GiocatoriInSquadraByIdGioco(idGioco);
            } catch (Exception e) {
                e.printStackTrace();
            }


            patruJucatoriInEchipaRecycleView = (RecyclerView) fragmentTabella4JucatoriInEchipa.findViewById(com.ionvaranita.belotenote.R.id.recycler_view_items_tabella_4_jucatori_in_echipa);


            Scor4JucatoriInEchipaEntityBean scor4JucatoriInEchipaEntityBean = db.scor4JucatoriInEchipaDao().selectScorBean4JucatoriInEchipaByIdJoc(idGioco);

            if (scor4JucatoriInEchipaEntityBean == null) {
                scor4JucatoriInEchipaEntityBean = new Scor4JucatoriInEchipaEntityBean();
                scor4JucatoriInEchipaEntityBean.setIdGioco(idGioco);
                scor4JucatoriInEchipaEntityBean.setScorNoi(0);
                scor4JucatoriInEchipaEntityBean.setScorVoi(0);
                db.scor4JucatoriInEchipaDao().insertScor4JucatoriInEchipa(scor4JucatoriInEchipaEntityBean);
            }


            TextView scorNoi = (TextView) fragmentTabella4JucatoriInEchipa.findViewById(com.ionvaranita.belotenote.R.id.scor_noi);

            scorNoi.setText(
                    Integer.toString(scor4JucatoriInEchipaEntityBean.getScorNoi())
            );

            scorNoi.setTextColor(colorTurNoi);

            TextView numePartida = (TextView) fragmentTabella4JucatoriInEchipa.findViewById(com.ionvaranita.belotenote.R.id.nume_partida_patru_jucatori_in_echipa);


            numePartida.setText(db.joc4JucatoriInEchipaDao().selectJocByIdJoc(idGioco).getNumeGioco());

            TextView scorVoi = (TextView) fragmentTabella4JucatoriInEchipa.findViewById(com.ionvaranita.belotenote.R.id.scor_voi);

            scorVoi.setTextColor(colorTurVoi);

            scorVoi.setText(Integer.toString(scor4JucatoriInEchipaEntityBean.getScorVoi()));


            AdapterTabella4JucatoriinEchipa adapterTabella4JucatoriinEchipa = new AdapterTabella4JucatoriinEchipa(fragmentTabella4JucatoriInEchipa.getContext(), lista4JucatoriInEchipa,idGioco);

            patruJucatoriInEchipaRecycleView.setAdapter(adapterTabella4JucatoriinEchipa);

            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentTabella4JucatoriInEchipa.getContext());


            patruJucatoriInEchipaRecycleView.setLayoutManager(linearLayoutManager);

            patruJucatoriInEchipaRecycleView.getLayoutManager().scrollToPosition(lista4JucatoriInEchipa.size() - 1);
        }
        // Inflate the layout for this fragment
        return fragmentTabella4JucatoriInEchipa;
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


    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }
}
