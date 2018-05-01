package com.ionvaranita.belotenote;

import android.content.Intent;

import android.content.pm.ActivityInfo;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.TableRow;
import android.widget.TextView;


import com.ionvaranita.belotenote.adapters.AdapterTabella4JucatoriinEchipa;
import com.ionvaranita.belotenote.campo.factory.impl.CampiScorImpl;
import com.ionvaranita.belotenote.campo.factory.impl.CampiStampaImpl;
import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.constanti.IdsCampiScor;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.Scor4JucatoriInEchipaEntityBean;
import com.ionvaranita.belotenote.entity.TurnManagement4GiocatoriInSquadra;
import com.ionvaranita.belotenote.popup.PopupWindowInserimentoPunti;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by ionvaranita on 07/09/17.
 */

public class TabellaPunti extends AppCompatActivity {
    private View popupView;
    private PopupWindowInserimentoPunti popupWindowInserimentoPunti;
    private static Turnul4GiocatoriInSquadraEnum urmatorulTurn = Turnul4GiocatoriInSquadraEnum.TURNUL_NOI;
    private static final Logger LOG = Logger.getLogger(TabellaPunti.class.getCanonicalName());
    private AppDatabase db;
    private AdapterTabella4JucatoriinEchipa adapterTabella4JucatoriinEchipa;


    private RecyclerView paginaPatruJucatoriInEchipaRecycleView;

    private Integer idGioco;

    private int actionCode;

    private TableRow campiScorTableRow;
    private TableRow campiStampaTableRow;

    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(com.ionvaranita.belotenote.R.layout.tabella_punti);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        db = AppDatabase.getPersistentDatabase(getApplicationContext());
        actionCode = getIntent().getIntExtra(ConstantiGlobal.ACTION_CODE, -1);

        if (actionCode == -1) {
            Log.e(ConstantiGlobal.ACTION_CODE, "Hai dimenticato di passare il codice azione");
            return;
        }

        campiScorTableRow = this.findViewById(R.id.campi_scor_table_row);
        campiStampaTableRow = this.findViewById(R.id.campi_stampa_table_row);
        popupView = getLayoutInflater().inflate(R.layout.popup_window_inserisci_puncte_4_jucatori_in_echipa, null);
        idGioco = getIntent().getIntExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), -1);

        linearLayoutManager = new LinearLayoutManager(this);

        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            gestisci4GiocatoriInSquadra();
        }


    }

    private void gestisci4GiocatoriInSquadra() {

        popolaCamiScor();

        popolaCampiStampa();

        List<Punti4GiocatoriInSquadraEntityBean> listaRecordsTabella4JucatoriInEchipa = db.tabellaPunti4GiocatoriInSquadraDao().selectAllPunti4GiocatoriInSquadraByIdGioco(idGioco);
        paginaPatruJucatoriInEchipaRecycleView = (RecyclerView) findViewById(com.ionvaranita.belotenote.R.id.recycler_view_items_tabella_4_jucatori_in_echipa);
        adapterTabella4JucatoriinEchipa = new AdapterTabella4JucatoriinEchipa(this, listaRecordsTabella4JucatoriInEchipa);
        paginaPatruJucatoriInEchipaRecycleView.setAdapter(adapterTabella4JucatoriinEchipa);

        paginaPatruJucatoriInEchipaRecycleView.setLayoutManager(linearLayoutManager);
        paginaPatruJucatoriInEchipaRecycleView.getLayoutManager().scrollToPosition(listaRecordsTabella4JucatoriInEchipa.size() - 1);


    }

    private void popolaCamiScor() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            Scor4JucatoriInEchipaEntityBean scorBean = db.scor4JucatoriInEchipaDao().selectScorBean4JucatoriInEchipaByIdJoc(idGioco);

            Gioco4GiocatoriInSquadra giocoBean = db.joc4JucatoriInEchipaDao().selectJocByIdJoc(idGioco);

            CampiScorImpl campiScorImpl = new CampiScorImpl();
            campiScorImpl.popolaRigaScor4GiocatoriInSquadra(campiScorTableRow);

            Map<Integer, TextView> mappaCampiScor = campiScorImpl.getMappaCampi();

            TextView scorNoi = mappaCampiScor.get(IdsCampiScor.ID_SCOR_NOI);
            scorNoi.setText(scorBean.getScorNoi().toString());

            TextView scorVoi = mappaCampiScor.get(IdsCampiScor.ID_SCOR_VOI);
            scorVoi.setText(scorBean.getScorVoi().toString());

            TextView nomeGioco = mappaCampiScor.get(ConstantiGlobal.ID_NOME_GIOCO);
            nomeGioco.setText(giocoBean.getNumeGioco());
        }
    }

    private void popolaCampiStampa() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            CampiStampaImpl campiStampaImpl = new CampiStampaImpl();

            campiStampaImpl.popolaRigaStampa4GiocatoriInSquadra(campiStampaTableRow);

            Map<Integer, TextView> mappaCampiStampa = campiStampaImpl.getMappaCampi();

            TextView noiStampa = mappaCampiStampa.get(IdsCampiStampa.ID_PUNTI_NOI_STAMPA);
            TextView voiStampa = mappaCampiStampa.get(IdsCampiStampa.ID_PUNTI_VOI_STAMPA);

            TurnManagement4GiocatoriInSquadra turulBean = db.turnManagement4GiocatoriInSquadraDao().selectTurn4JucatoriInEchipaByIdJoc(idGioco);

            String turulPresent = turulBean.getTurnoPresente();


            if (turulPresent.equals(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.toString())) {
                String bulletString = "\u2022" + noiStampa.getText();
                noiStampa.setText(bulletString);
            } else if (turulPresent.equals(Turnul4GiocatoriInSquadraEnum.TURNUL_VOI.toString())) {
                String bulletString = "\u2022" + voiStampa.getText();
                voiStampa.setText(bulletString);

            }
        }
    }


    public void createNewRecord(View view) {
        showPopup(view);
    }

    public void showPopup(View anchorView) {
        View footer = this.findViewById(R.id.recycler_view_items_tabella_4_jucatori_in_echipa);

        final TableRow inserisciTableRow = this.findViewById(R.id.inserisci_table_row_4_jucatori_in_echipa);

        popupWindowInserimentoPunti = new PopupWindowInserimentoPunti(actionCode, idGioco, popupView, getSupportFragmentManager(), footer.getWidth(), footer.getHeight() + inserisciTableRow.getHeight());

        // Initialize more widgets from `popup_layout.xml`
        // If the PopupWindow should be focusable
        popupWindowInserimentoPunti.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindowInserimentoPunti.setBackgroundDrawable(new BitmapDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        footer.getLocationOnScreen(location);


        // Using location, the PopupWindow will be displayed right under anchorView
        popupWindowInserimentoPunti.showAtLocation(footer, Gravity.NO_GRAVITY,
                location[0], location[1]);

    }


    @Override
    public void onBackPressed() {
        LOG.info("onBackPressed method called");
        if (popupWindowInserimentoPunti!=null&&popupWindowInserimentoPunti.isShowing()) {
            popupWindowInserimentoPunti.dismiss();
        }
        Intent intent = new Intent(this, Pagina4Jucatori.class);
        intent.putExtra(MainActivityButtonChooser.BUTONUL_APASAT, MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA);
        startActivity(intent);
    }
}