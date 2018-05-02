package com.ionvaranita.belotenote;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableRow;

import com.ionvaranita.belotenote.adapters.AdapterSpinner;
import com.ionvaranita.belotenote.borders.BorderedEditText;
import com.ionvaranita.belotenote.business.BusinessInserimentoNuovoGioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.campo.factory.impl.CampiInserimentoNuovoGiocoImpl;
import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.PuncteCastigatoareGlobalBean;
import com.ionvaranita.belotenote.infogn.InfoGiocoNuovo4GiocatoriInSquadra;
import com.ionvaranita.belotenote.popup.ParametersPuncteCastigatoarePopup;
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
    private Window mainWindow;
    private boolean isNomeGiocoMostrabile;
    private final Context contesto;
    private Button okButtonPopup;
    private Button cancelButtonPopup;
    private BorderedEditText puncteCastigatoareGlobalInserimanto;
    private View popupViewPuncteCastigatoare;;
    private Switch switchButton;
    private LayoutInflater layoutInflater;
    private Integer idPartida;
    private Integer idGioco;
    private TableRow nomeGiocoFooterTableRow;

    private Spinner spinnerPuncteCastigatoarePrecedente;

    private AdapterSpinner adapterSpinner;

    public PopupPuncteCastigatoare(ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup, Integer idGioco, Integer idPartida) {
        this(parametersPuncteCastigatoarePopup);
        this.idGioco = idGioco;
        this.idPartida = idPartida;

    }

    public PopupPuncteCastigatoare(ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup) {
        this.actionCode = parametersPuncteCastigatoarePopup.getActioCode();
        mainWindow = parametersPuncteCastigatoarePopup.getMainWindow();
        this.contesto = mainWindow.getContext();
        isNomeGiocoMostrabile = parametersPuncteCastigatoarePopup.isNomeGiocoMostrabile();


        layoutInflater = ((LayoutInflater) contesto.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        popupViewPuncteCastigatoare = layoutInflater.inflate(R.layout.popup_puncte_castigatoare_global, null);

        spinnerPuncteCastigatoarePrecedente = popupViewPuncteCastigatoare.findViewById(R.id.spinner_puncte_castigatoare_precedente);



        switchButton = popupViewPuncteCastigatoare.findViewById(R.id.switch_button_active_puncte_castigatoare_global);

        nomeGiocoFooterTableRow = popupViewPuncteCastigatoare.findViewById(R.id.nome_gioco_global_table_row);

        if (isNomeGiocoMostrabile) {
            nomeGiocoFooterTableRow.setVisibility(View.VISIBLE);

        } else if (!isNomeGiocoMostrabile) {
            nomeGiocoFooterTableRow.setVisibility(View.INVISIBLE);
        }

        itemsMenuRecyclerView = (RecyclerView) mainWindow.findViewById(R.id.lista_jocuri_recycleview);

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

        adapterSpinner = new AdapterSpinner(contesto,R.layout.item_spinner_puncte_castigatoare,puncteCastigatoareGlobalList);

        spinnerPuncteCastigatoarePrecedente.setAdapter(adapterSpinner);

        popupWindow = new PopupWindow(popupViewPuncteCastigatoare,
                itemsMenuRecyclerView.getWidth(), itemsMenuRecyclerView.getHeight());

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
        puncteCastigatoareGlobalInserimanto = popupViewPuncteCastigatoare.findViewById(R.id.puncte_castigatoare_global_inserimento);
        spinnerPuncteCastigatoarePrecedente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                switchButton.setChecked(false);

            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    puncteCastigatoareGlobalInserimanto.setVisibility(View.VISIBLE);
                    puncteCastigatoareGlobalInserimanto.requestFocus();
                    puncteCastigatoareGlobalInserimanto.callOnClick();
                    spinnerPuncteCastigatoarePrecedente.setEnabled(false);
                }
                else{
                    puncteCastigatoareGlobalInserimanto.setVisibility(View.INVISIBLE);
                    spinnerPuncteCastigatoarePrecedente.setEnabled(true);
                }
            }
        });
    }
    public void showPopup() {

        popupWindow.setFocusable(true);
        int[] location = new int[2];
        itemsMenuRecyclerView.getLocationOnScreen(location);
        popupWindow.showAtLocation(itemsMenuRecyclerView, Gravity.NO_GRAVITY,
                location[0], location[1]);
    }

    public void inserisciJocNouSauPartidaNuova() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            if (verificaIntegrita()) {
                BorderedEditText nomeGioco = mappaCampi.get(ConstantiGlobal.ID_NOME_GIOCO);

                InfoGiocoNuovo4GiocatoriInSquadra infoGiocoNuovo4GiocatoriInSquadra = new InfoGiocoNuovo4GiocatoriInSquadra(contesto);

                infoGiocoNuovo4GiocatoriInSquadra.setIdGioco(idGioco);
                infoGiocoNuovo4GiocatoriInSquadra.setNomeGioco(nomeGioco.getText().toString());
                infoGiocoNuovo4GiocatoriInSquadra.setPuncteCastigatoare(puncteCastigatoare);


                BusinessInserimentoNuovoGioco4GiocatoriInSquadra businessInserimentoNuovoGioco4GiocatoriInSquadra = new BusinessInserimentoNuovoGioco4GiocatoriInSquadra();

                businessInserimentoNuovoGioco4GiocatoriInSquadra.inserisciPrimaVoltaNelDatabase(infoGiocoNuovo4GiocatoriInSquadra);

                idGioco = businessInserimentoNuovoGioco4GiocatoriInSquadra.getIdGioco();

                vaiNellaTabellaPunti();
            }


        }


    }

    private boolean verificaIntegrita() {
        return verificaCampiFooter() && verificaPuncteCastigatoare();
    }

    private boolean verificaCampiFooter() {
        List<BorderedEditText> listaCampi = new ArrayList<>(mappaCampi.values());
        for (BorderedEditText campo : listaCampi) {
            if (campo.getText() == null) {
                return false;
            }
        }
        return true;
    }


    private boolean verificaPuncteCastigatoare() {
        if (puncteCastigatoareGlobalInserimanto.getText() != null && IntegerUtils.isInteger(puncteCastigatoareGlobalInserimanto.getText().toString())) {
            puncteCastigatoare = Integer.parseInt(puncteCastigatoareGlobalInserimanto.getText().toString());
            return true;
        }
        if(spinnerPuncteCastigatoarePrecedente.isEnabled()){

            PuncteCastigatoareGlobalBean puncteCastigatoareGlobalBean = (PuncteCastigatoareGlobalBean)spinnerPuncteCastigatoarePrecedente.getSelectedItem();

            puncteCastigatoare = puncteCastigatoareGlobalBean.getPuncteCastigatoare();
            return true;
        }
        return false;
    }


    private void vaiNellaTabellaPunti() {
        Intent intent = new Intent(contesto, TabellaPunti.class);
        intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), idGioco);
        intent.putExtra(ConstantiGlobal.ACTION_CODE, actionCode);

        contesto.startActivity(intent);


    }

}
