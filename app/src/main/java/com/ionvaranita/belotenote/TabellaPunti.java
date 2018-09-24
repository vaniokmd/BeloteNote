package com.ionvaranita.belotenote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.ActivityInfo;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;


import com.ionvaranita.belotenote.adapters.AdapterTabella4JucatoriinEchipa;
import com.ionvaranita.belotenote.campo.factory.impl.CampiScorImpl;
import com.ionvaranita.belotenote.campo.factory.impl.CampiStampaImpl;
import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.MainActivityButtonChooser;
import com.ionvaranita.belotenote.constanti.StatusGioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.constanti.IdsCampiScor;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.entity.PuncteCastigatoare4JucatoriInEchipaBean;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.Scor4JucatoriInEchipaEntityBean;
import com.ionvaranita.belotenote.entity.TurnManagement4GiocatoriInSquadra;
import com.ionvaranita.belotenote.entity.WhoPlayEntityBean;
import com.ionvaranita.belotenote.info.ParametersPopupWindowInserimentoPunti;
import com.ionvaranita.belotenote.popup.ParametersPuncteCastigatoarePopup;
import com.ionvaranita.belotenote.popup.PopupPuncteCastigatoare;
import com.ionvaranita.belotenote.popup.PopupWindowInserimentoPunti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Created by ionvaranita on 07/09/17.
 */

public class TabellaPunti extends AppCompatActivity {
    private List<Punti4GiocatoriInSquadraEntityBean> listaRecordsTabella4JucatoriInEchipa;

    private Context context;
    private View popupView;
    private PopupWindowInserimentoPunti popupWindowInserimentoPunti;
    private static final Logger LOG = Logger.getLogger(TabellaPunti.class.getCanonicalName());
    private AppDatabase db;
    private AdapterTabella4JucatoriinEchipa adapterTabella4JucatoriinEchipa;

    private RecyclerView paginaPatruJucatoriInEchipaRecycleView;

    private Integer idGioco;
    private Integer statusGioco;

    private int actionCode;

    private TableRow campiScorTableRow;
    private TableRow campiStampaTableRow;

    private LinearLayoutManager linearLayoutManager;

    private Button inserisciButton;
    private StatusGioco4GiocatoriInSquadra statusGioco4GiocatoriInSquadra;

    private boolean partidaFinita;

    private boolean partidaNonFinitaConAggiuntaPunti;

    private  boolean partidaNonFinita;
    private boolean giocaQualcuno;

    private ImageView statusGiocoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(com.ionvaranita.belotenote.R.layout.tabella_punti);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        this.context = this;
        this.inserisciButton = this.findViewById(R.id.inserisci_button_tabella_punti);

        statusGiocoImageView = this.findViewById(R.id.status_gioco);

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




        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            getSupportActionBar().setCustomView(R.layout.action_bar_main_activity);
            TextView titoloActioBar = getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);

            linearLayoutManager = new LinearLayoutManager(this);


            titoloActioBar.setText(R.string.patru_jucatori_in_echipa);
            gestisci4GiocatoriInSquadra();
        }

        setDenominazioneInserisciButton();


    }

    private void setDenominazioneInserisciButton() {

        partidaFinita = statusGioco4GiocatoriInSquadra.CODICE_PARTIDA_FINITA.equals(statusGioco);

        partidaNonFinitaConAggiuntaPunti = statusGioco.equals(statusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA_PROLUNGATA);

        partidaNonFinita = statusGioco.equals(statusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA);

        if(partidaFinita){
            inserisciButton.setText(R.string.insert_new_match);
        }
        else{
            inserisciButton.setEnabled(giocaQualcuno);
        }

    }

    private void gestisci4GiocatoriInSquadra() {

        popolaCamiScor();

        popolaCampoStatusGioco();

        popolaCampiStampa();

        listaRecordsTabella4JucatoriInEchipa = db.tabellaPunti4GiocatoriInSquadraDao().selectAllPunti4GiocatoriInSquadraByIdGioco(idGioco);
        if (listaRecordsTabella4JucatoriInEchipa.size() > 1) {
            listaRecordsTabella4JucatoriInEchipa.remove(0);
        }
        paginaPatruJucatoriInEchipaRecycleView = (RecyclerView) findViewById(com.ionvaranita.belotenote.R.id.recycler_view_items_tabella_4_jucatori_in_echipa);
        adapterTabella4JucatoriinEchipa = new AdapterTabella4JucatoriinEchipa(this, listaRecordsTabella4JucatoriInEchipa,idGioco);
        paginaPatruJucatoriInEchipaRecycleView.setAdapter(adapterTabella4JucatoriinEchipa);

        paginaPatruJucatoriInEchipaRecycleView.setLayoutManager(linearLayoutManager);
        paginaPatruJucatoriInEchipaRecycleView.getLayoutManager().scrollToPosition(listaRecordsTabella4JucatoriInEchipa.size() - 1);


    }

    private void popolaCampoStatusGioco() {
        statusGioco4GiocatoriInSquadra = new StatusGioco4GiocatoriInSquadra(context);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);

        if(statusGioco==StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA){
            statusGiocoImageView.setImageResource(R.mipmap.ic_playing_status);
            statusGiocoImageView.startAnimation(anim);
        }
        else if(statusGioco==StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA_PROLUNGATA){
            statusGiocoImageView.setImageResource(R.mipmap.ic_prolonged_status);
            statusGiocoImageView.startAnimation(anim);
        }
        else if(statusGioco==StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_FINITA){
            statusGiocoImageView.setImageResource(R.mipmap.ic_stop_status);
        }






    }

    private void popolaCamiScor() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            Scor4JucatoriInEchipaEntityBean scorBean = db.scor4JucatoriInEchipaDao().selectScorBean4JucatoriInEchipaByIdJoc(idGioco);
            Gioco4GiocatoriInSquadra giocoBean = db.joc4JucatoriInEchipaDao().selectJocByIdJoc(idGioco);
            statusGioco = giocoBean.getStatus();



            CampiScorImpl campiScorImpl = new CampiScorImpl();
            campiScorImpl.popolaRigaScor4GiocatoriInSquadra(campiScorTableRow);

            Map<Integer, TextView> mappaCampiScor = campiScorImpl.getMappaCampi();

            TextView scorNoi = mappaCampiScor.get(IdsCampiScor.ID_SCOR_NOI);
            scorNoi.setText(scorBean.getScorNoi().toString());

            TextView scorVoi = mappaCampiScor.get(IdsCampiScor.ID_SCOR_VOI);
            scorVoi.setText(scorBean.getScorVoi().toString());

            TextView nomeGioco = mappaCampiScor.get(ConstantiGlobal.ID_NOME_GIOCO);
            nomeGioco.setText(giocoBean.getNumeGioco());
            nomeGioco.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    PuncteCastigatoare4JucatoriInEchipaBean castigatoare4JucatoriInEchipaBean = db.puncteCastigatoare4JucatoriInEchipaDao().selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndMaxIdPartida(idGioco);
                    AlertDialog.Builder builder = new AlertDialog.Builder(nomeGioco.getContext());
                    String infoGioco = nomeGioco.getContext().getString(R.string.games_name);
                    infoGioco = infoGioco + ": " + giocoBean.getNumeGioco().toString() + "\n" +
                            nomeGioco.getContext().getResources().getString(R.string.winner_points) + " " + castigatoare4JucatoriInEchipaBean.getPuncteCastigatoare() + "\n" +
                            "Status: " + giocoBean.getStatus();
                    builder.setTitle(nomeGioco.getContext().getResources().getString(R.string.games_info))
                            .setMessage(infoGioco)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                    return false;
                }
            });

        }
    }

    private void configuraCampiStampaWhoPlay(Map<Integer,TextView> mappaIdCampiCampiStampa){
        List<TextView> listaCampi = new ArrayList<>(mappaIdCampiCampiStampa.values());
        for (Integer idCampoStampa:mappaIdCampiCampiStampa.keySet()){
            TextView campoStampa = mappaIdCampiCampiStampa.get(idCampoStampa);
            if(idCampoStampa!=IdsCampiStampa.ID_PUNTI_GIOCO){
                setOnClickListenerCampoStampaWhoPlayed(listaCampi,campoStampa);
            }
        }
    }

    private void setOnClickListenerCampoStampaWhoPlayed(List<TextView> listaCampi,TextView campoStampa){
        campoStampa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(campoStampa.getId()!=IdsCampiStampa.ID_PUNTI_GIOCO){
                    campoStampa.setBackgroundColor(Color.GRAY);
                    WhoPlayEntityBean whoPlayEntityBean = new WhoPlayEntityBean();
                    whoPlayEntityBean.setIdGioco(idGioco);
                    whoPlayEntityBean.setIdPersona(campoStampa.getId());
                    db.whoPlayedDao().insertOrUpdateWhoPlayed(whoPlayEntityBean);
                    deselezionaGliAltriCampiTranne(listaCampi,campoStampa.getId());
                    giocaQualcuno=true;
                    inserisciButton.setEnabled(giocaQualcuno);
                }

            }
        });
    }
    private void deselezionaGliAltriCampiTranne(List<TextView> listaCampi,Integer idCampoEscluso){
        for (TextView campo:listaCampi){
            if(campo.getId()!=idCampoEscluso){
                campo.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }


    private void popolaCampiStampa() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            CampiStampaImpl campiStampaImpl = new CampiStampaImpl();

            campiStampaImpl.popolaRigaStampa4GiocatoriInSquadra(campiStampaTableRow);

            Map<Integer, TextView> mappaCampiStampa = campiStampaImpl.getMappaCampi();
            configuraCampiStampaWhoPlay(mappaCampiStampa);

            TextView noiStampa = mappaCampiStampa.get(IdsCampiStampa.ID_NOI);
            TextView voiStampa = mappaCampiStampa.get(IdsCampiStampa.ID_VOI);

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
        if (statusGioco != null) {

            ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup = new ParametersPuncteCastigatoarePopup();
            parametersPuncteCastigatoarePopup.setActioCode(ActionCode.GIOCATORI_4_IN_SQUADRA);
            parametersPuncteCastigatoarePopup.setContext(context);
            parametersPuncteCastigatoarePopup.setIdGioco(idGioco);

            if (partidaFinita) {
                parametersPuncteCastigatoarePopup.setNuovaPartida(true);
                PopupPuncteCastigatoare popupPuncteCastigatoare = new PopupPuncteCastigatoare(parametersPuncteCastigatoarePopup);
                popupPuncteCastigatoare.showPopup();

            } else if (partidaNonFinita || partidaNonFinitaConAggiuntaPunti) {
                View footer = this.findViewById(R.id.recycler_view_items_tabella_4_jucatori_in_echipa);
                final TableRow inserisciTableRow = this.findViewById(R.id.inserisci_table_row_4_jucatori_in_echipa);
                ParametersPopupWindowInserimentoPunti parametersPopupWindowInserimentoPunti = new ParametersPopupWindowInserimentoPunti();
                parametersPopupWindowInserimentoPunti.setIdGioco(idGioco);
                parametersPopupWindowInserimentoPunti.setActionCode(actionCode);
                parametersPopupWindowInserimentoPunti.setFragmentManager(getSupportFragmentManager());
                parametersPopupWindowInserimentoPunti.setGiocaQualcuno(giocaQualcuno);
                parametersPopupWindowInserimentoPunti.setHeight(footer.getHeight() + inserisciTableRow.getHeight());
                parametersPopupWindowInserimentoPunti.setWidth(footer.getWidth());
                parametersPopupWindowInserimentoPunti.setPopupLayout(popupView);
                popupWindowInserimentoPunti = new PopupWindowInserimentoPunti(parametersPopupWindowInserimentoPunti);
                popupWindowInserimentoPunti.setFocusable(true);
                //popupWindowInserimentoPunti.setBackgroundDrawable(new BitmapDrawable());
                int location[] = new int[2];
                // Get the View's(the one that was clicked in the Fragment) location
                footer.getLocationOnScreen(location);
                // Using location, the PopupWindow will be displayed right under anchorView
                popupWindowInserimentoPunti.showAtLocation(footer, Gravity.NO_GRAVITY,
                        location[0], location[1]);
            }


        }
    }


    @Override
    public void onBackPressed() {
        LOG.info("onBackPressed method called");
        if (popupWindowInserimentoPunti != null && popupWindowInserimentoPunti.isShowing()) {
            popupWindowInserimentoPunti.dismiss();
        }
        Intent intent = new Intent(this, MenuJocuri.class);
        intent.putExtra(MainActivityButtonChooser.BUTONUL_APASAT, MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA);
        intent.putExtra(ConstantiGlobal.ACTION_CODE, actionCode);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.main_menu:
                Intent vaiIndietro = new Intent(this, MainActivity.class);
                vaiIndietro.putExtra(MainActivityButtonChooser.BUTONUL_APASAT, MainActivityButtonChooser.NONE_2);
                startActivity(vaiIndietro);
                return true;
            case R.id.about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}