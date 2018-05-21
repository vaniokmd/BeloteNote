package com.ionvaranita.belotenote.popup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.TabellaPunti;
import com.ionvaranita.belotenote.adapters.AdapterSpinner;
import com.ionvaranita.belotenote.borders.BorderedEditText;
import com.ionvaranita.belotenote.business.BusinessInserimento4GiocatoriInSquadra;
import com.ionvaranita.belotenote.campo.factory.impl.CampiInserimentoNuovoGiocoImpl;
import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.PuncteCastigatoareGlobalBean;
import com.ionvaranita.belotenote.infogn.InfoGiocoNuovo4GiocatoriInSquadra;
import com.ionvaranita.belotenote.utils.IntegerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ionvaranita on 12/04/18.
 */

public class PopupPuncteCastigatoare {
    private Map<Integer, BorderedEditText> mappaCampi;
    private Integer puncteCastigatoare;
    private Integer actionCode;
    private List<PuncteCastigatoareGlobalBean> puncteCastigatoareGlobalList;
    private AppDatabase db;
    private RecyclerView itemsMenuRecyclerView;
    private PopupWindow popupWindow;
    private View mainView;
    private boolean isNomeGiocoMostrabile;
    private final Context contesto;
    private Button okButtonPopup;
    private Button cancelButtonPopup;
    private BorderedEditText puncteCastigatoareGlobalInserimanto;
    private View popupViewPuncteCastigatoare;
    ;
    private Switch switchButton;
    private LayoutInflater layoutInflater;
    private Integer idPartida;
    private Integer idGioco;
    private TableRow nomeGiocoFooterTableRow;
    private TextView textViewWinnerPoints;
    private TableRow lastMatchWinnerPointsTableRow;
    private TableRow winnerPointsTableRow;
    private TextView lastMatchWinnerPointsValueTextViewl;
    private Integer latMatchWinnerPoints;

    private Spinner spinnerPuncteCastigatoarePrecedente;

    private AdapterSpinner adapterSpinner;

    public PopupPuncteCastigatoare(ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup, Integer idGioco, Integer idPartida) {
        this(parametersPuncteCastigatoarePopup);
        this.idGioco = idGioco;
        this.idPartida = idPartida;

    }

    public PopupPuncteCastigatoare(ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup) {
        this.actionCode = parametersPuncteCastigatoarePopup.getActioCode();
        this.mainView = parametersPuncteCastigatoarePopup.getMainView();
        this.contesto = mainView.getContext();
        isNomeGiocoMostrabile = parametersPuncteCastigatoarePopup.isNomeGiocoMostrabile();




        layoutInflater = ((LayoutInflater) contesto.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        popupViewPuncteCastigatoare = layoutInflater.inflate(R.layout.popup_puncte_castigatoare_global, null);
        textViewWinnerPoints = popupViewPuncteCastigatoare.findViewById(R.id.textview_winner_points);

        spinnerPuncteCastigatoarePrecedente = popupViewPuncteCastigatoare.findViewById(R.id.spinner_puncte_castigatoare_precedente);


        switchButton = popupViewPuncteCastigatoare.findViewById(R.id.switch_button_active_puncte_castigatoare_global);

        nomeGiocoFooterTableRow = popupViewPuncteCastigatoare.findViewById(R.id.nome_gioco_global_table_row);

        lastMatchWinnerPointsTableRow = popupViewPuncteCastigatoare.findViewById(R.id.tablerow_last_match_winner_points);

        winnerPointsTableRow = popupViewPuncteCastigatoare.findViewById(R.id.tablerow_winner_ponts);

        puncteCastigatoareGlobalInserimanto = popupViewPuncteCastigatoare.findViewById(R.id.puncte_castigatoare_global_inserimento);
        lastMatchWinnerPointsValueTextViewl = popupViewPuncteCastigatoare.findViewById(R.id.textview_last_match_winner_points_value);

        this.latMatchWinnerPoints = parametersPuncteCastigatoarePopup.getLastMatchWinnerpoints();

        if (isNomeGiocoMostrabile) {
            nomeGiocoFooterTableRow.setVisibility(View.VISIBLE);
            lastMatchWinnerPointsTableRow.setVisibility(View.INVISIBLE);
            winnerPointsTableRow.setVisibility(View.VISIBLE);
            puncteCastigatoareGlobalInserimanto.setVisibility(View.INVISIBLE);



        } else if (!isNomeGiocoMostrabile) {
            nomeGiocoFooterTableRow.setVisibility(View.INVISIBLE);
            lastMatchWinnerPointsTableRow.setVisibility(View.VISIBLE);
            winnerPointsTableRow.setVisibility(View.INVISIBLE);
            puncteCastigatoareGlobalInserimanto.setVisibility(View.VISIBLE);
            puncteCastigatoareGlobalInserimanto.requestFocus();
            lastMatchWinnerPointsValueTextViewl.setText(""+latMatchWinnerPoints);


        }

        itemsMenuRecyclerView = (RecyclerView) mainView.findViewById(R.id.lista_jocuri_recycleview);

        db = AppDatabase.getPersistentDatabase(contesto);

        popolaRecyclerViewPuncteCastigatoareGlobal();

        setMostraONascondiInputPuncteCastigatoare();

        setCancelAndOkButton();


        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            gestisci4GiocatoriInSquadra();
        }


    }


    private void gestisci4GiocatoriInSquadra() {
        CampiInserimentoNuovoGiocoImpl campiInserimentoNuovoGioco = new CampiInserimentoNuovoGiocoImpl();
        campiInserimentoNuovoGioco.popolaRigaCampiNuovoGioco4giocatoriInSquadra(nomeGiocoFooterTableRow);
        mappaCampi = campiInserimentoNuovoGioco.getMappaCampi();


    }

    private void popolaRecyclerViewPuncteCastigatoareGlobal() {

        puncteCastigatoareGlobalList = db.puncteCastigatoareGlobalDao().selectAllPuncteCastigatoareGlobalOrderByData();

        adapterSpinner = new AdapterSpinner(contesto, R.layout.item_spinner_puncte_castigatoare, puncteCastigatoareGlobalList);

        spinnerPuncteCastigatoarePrecedente.setAdapter(adapterSpinner);
        if(isNomeGiocoMostrabile){
            popupWindow = new PopupWindow(popupViewPuncteCastigatoare,
                    itemsMenuRecyclerView.getWidth(), itemsMenuRecyclerView.getHeight());
        }
        else{
            popupWindow = new PopupWindow(popupViewPuncteCastigatoare, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        }



    }

    private void setCancelAndOkButton() {
        okButtonPopup = popupViewPuncteCastigatoare.findViewById(R.id.ok_button_puncte_castigatoare_global_popup);


        okButtonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserisciJocNouSauPartidaNuova();
            }
        });

        cancelButtonPopup = popupViewPuncteCastigatoare.findViewById(R.id.cancel_button_puncte_castigatoare_global_popup);

        cancelButtonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    private void setMostraONascondiInputPuncteCastigatoare() {

        spinnerPuncteCastigatoarePrecedente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                switchButton.setChecked(false);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView selectedView = (TextView)spinnerPuncteCastigatoarePrecedente.getSelectedView();
                if (isChecked) {
                    puncteCastigatoareGlobalInserimanto.setVisibility(View.VISIBLE);
                    puncteCastigatoareGlobalInserimanto.requestFocus();
                    spinnerPuncteCastigatoarePrecedente.setEnabled(false);

                    selectedView.setTextColor(Color.GRAY);
                    textViewWinnerPoints.setTextColor(Color.GRAY);

                } else {
                    puncteCastigatoareGlobalInserimanto.setVisibility(View.INVISIBLE);
                    spinnerPuncteCastigatoarePrecedente.setEnabled(true);
                    textViewWinnerPoints.setEnabled(true);
                    textViewWinnerPoints.setTextColor(Color.BLACK);
                    selectedView.setTextColor(Color.BLACK);

                    InputMethodManager imm = (InputMethodManager)contesto.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(puncteCastigatoareGlobalInserimanto.getWindowToken(), 0);


                }
            }
        });
    }

    public void showPopup() {

        popupWindow.setFocusable(true);

        if(isNomeGiocoMostrabile){
            int[] location = new int[2];
            itemsMenuRecyclerView.getLocationOnScreen(location);
            popupWindow.showAtLocation(itemsMenuRecyclerView, Gravity.NO_GRAVITY,
                    location[0], location[1]);
        }
        else{
            popupWindow.showAtLocation(mainView,Gravity.CENTER,0,0);

        }

    }

    public void inserisciJocNouSauPartidaNuova() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            if (verificaIntegrita()) {

                InfoGiocoNuovo4GiocatoriInSquadra infoGiocoNuovo4GiocatoriInSquadra = new InfoGiocoNuovo4GiocatoriInSquadra(contesto);

                if(isNomeGiocoMostrabile){
                    BorderedEditText nomeGioco = mappaCampi.get(ConstantiGlobal.ID_NOME_GIOCO);
                    infoGiocoNuovo4GiocatoriInSquadra.setNomeGioco(nomeGioco.getText().toString());
                }
                infoGiocoNuovo4GiocatoriInSquadra.setPuncteCastigatoare(puncteCastigatoare);
                infoGiocoNuovo4GiocatoriInSquadra.setIdGioco(idGioco);


                BusinessInserimento4GiocatoriInSquadra businessInserimento4GiocatoriInSquadra = new BusinessInserimento4GiocatoriInSquadra(contesto);

                businessInserimento4GiocatoriInSquadra.inserisciPrimaVoltaNelDatabase(infoGiocoNuovo4GiocatoriInSquadra);

                idGioco = businessInserimento4GiocatoriInSquadra.getIdGioco();

                vaiNellaTabellaPunti();
            }


        }


    }

    private boolean verificaIntegrita() {

        if(latMatchWinnerPoints!=null){
            return verificaPuncteCastigatoare();
        }

        return verificaCampiFooter() && verificaPuncteCastigatoare();
    }

    private boolean verificaCampiFooter() {
        String errore = contesto.getResources().getString(R.string.error_game_name);
        List<BorderedEditText> listaCampi = new ArrayList<>(mappaCampi.values());
        for (BorderedEditText campo : listaCampi) {
            if (campo.getText() == null || (campo.getText() != null && campo.getText().toString().length() < 1)) {
                campo.setError(errore);
                campo.showError();
                return false;
            }
        }

        return true;
    }


    private boolean verificaPuncteCastigatoare() {
        if(latMatchWinnerPoints!=null){
            puncteCastigatoare = Integer.parseInt(puncteCastigatoareGlobalInserimanto.getText().toString());
            return puncteCastigatoare > latMatchWinnerPoints;
        }
        if (puncteCastigatoareGlobalInserimanto.getText() != null && IntegerUtils.isInteger(puncteCastigatoareGlobalInserimanto.getText().toString())) {
            puncteCastigatoare = Integer.parseInt(puncteCastigatoareGlobalInserimanto.getText().toString());
            return true;
        }
        if (spinnerPuncteCastigatoarePrecedente.isEnabled()) {

            PuncteCastigatoareGlobalBean puncteCastigatoareGlobalBean = (PuncteCastigatoareGlobalBean) spinnerPuncteCastigatoarePrecedente.getSelectedItem();

            puncteCastigatoare = puncteCastigatoareGlobalBean.getPuncteCastigatoare();
            return true;
        }

        String errore = contesto.getResources().getString(R.string.error_puncte_castigatoare);
        puncteCastigatoareGlobalInserimanto.setError(errore);
        puncteCastigatoareGlobalInserimanto.showError();

        return false;
    }


    private void vaiNellaTabellaPunti() {
        Intent intent = new Intent(contesto, TabellaPunti.class);
        intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), idGioco);
        intent.putExtra(ConstantiGlobal.ACTION_CODE, actionCode);

        contesto.startActivity(intent);


    }

}
