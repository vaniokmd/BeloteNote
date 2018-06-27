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
import com.ionvaranita.belotenote.info.InfoGiocoNuovo4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoNuovaPartida4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoWinnerPoints;
import com.ionvaranita.belotenote.utils.IntegerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ionvaranita on 12/04/18.
 */

public class PopupPuncteCastigatoare {
    private Map<Integer, BorderedEditText> mappaCampi;
    private Integer winnerPoints;
    private Integer actionCode;
    private List<PuncteCastigatoareGlobalBean> puncteCastigatoareGlobalList;
    private AppDatabase db;
    private RecyclerView listaJocuriRecyclerView;
    private PopupWindow popupWindow;
    private View anchorView;
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
    private Integer maxPuncte;

    private Spinner spinnerPuncteCastigatoarePrecedente;

    private AdapterSpinner adapterSpinner;
    private boolean nuovaPartidaConAdaus;
    private boolean nuovaPartida;
    private boolean nuovoGioco;

    public PopupPuncteCastigatoare(ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup) {
        this.idGioco = parametersPuncteCastigatoarePopup.getIdGioco();
        this.idPartida = parametersPuncteCastigatoarePopup.getIdPartida();
        this.actionCode = parametersPuncteCastigatoarePopup.getActioCode();
        this.anchorView = parametersPuncteCastigatoarePopup.getAnchorView();
        this.contesto = parametersPuncteCastigatoarePopup.getContext();
        isNomeGiocoMostrabile = parametersPuncteCastigatoarePopup.isNomeGiocoMostrabile();

        layoutInflater = ((LayoutInflater) contesto.getSystemService(Context.LAYOUT_INFLATER_SERVICE));

        nuovaPartidaConAdaus = !isNomeGiocoMostrabile && parametersPuncteCastigatoarePopup.getInfoCineACistigat() != null && parametersPuncteCastigatoarePopup.getInfoCineACistigat().aflaCineACistigat().equals(ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI);

        nuovaPartida = !isNomeGiocoMostrabile;

        nuovoGioco = isNomeGiocoMostrabile;

        if (nuovaPartidaConAdaus) {
            popupViewPuncteCastigatoare = layoutInflater.inflate(R.layout.adauga_puncte_castigatoare_in_plus, null);
            TextView t = popupViewPuncteCastigatoare.findViewById(R.id.x_jucatori_castigatori);
            t.setText("" + parametersPuncteCastigatoarePopup.getInfoCineACistigat().getListaIdVincitori().size() + " " + contesto.getResources().getString(R.string.winner_players));
            maxPuncte = parametersPuncteCastigatoarePopup.getInfoCineACistigat().getMaxValuePunti();
            spinnerPuncteCastigatoarePrecedente = popupViewPuncteCastigatoare.findViewById(R.id.spinner_puncte_castigatoare_precedente);
            puncteCastigatoareGlobalInserimanto = popupViewPuncteCastigatoare.findViewById(R.id.puncte_castigatoare_global_inserimento);
            puncteCastigatoareGlobalInserimanto.setVisibility(View.VISIBLE);
            puncteCastigatoareGlobalInserimanto.requestFocus();

            puncteCastigatoareGlobalInserimanto.setHint(puncteCastigatoareGlobalInserimanto.getHint().toString() + parametersPuncteCastigatoarePopup.getInfoCineACistigat().getMaxValuePunti());
            textViewWinnerPoints = popupViewPuncteCastigatoare.findViewById(R.id.textview_winner_points);
            switchButton = popupViewPuncteCastigatoare.findViewById(R.id.switch_button_active_puncte_castigatoare_global);
            nomeGiocoFooterTableRow = popupViewPuncteCastigatoare.findViewById(R.id.nome_gioco_global_table_row);


        } else if (nuovaPartida) {
            popupViewPuncteCastigatoare = layoutInflater.inflate(R.layout.popup_puncte_castigatoare_global, null);
            textViewWinnerPoints = popupViewPuncteCastigatoare.findViewById(R.id.textview_winner_points);
            puncteCastigatoareGlobalInserimanto = popupViewPuncteCastigatoare.findViewById(R.id.puncte_castigatoare_global_inserimento);
            puncteCastigatoareGlobalInserimanto.setVisibility(View.INVISIBLE);
            switchButton = popupViewPuncteCastigatoare.findViewById(R.id.switch_button_active_puncte_castigatoare_global);
            spinnerPuncteCastigatoarePrecedente = popupViewPuncteCastigatoare.findViewById(R.id.spinner_puncte_castigatoare_precedente);
        } else if (nuovoGioco) {
            popupViewPuncteCastigatoare = layoutInflater.inflate(R.layout.popup_puncte_castigatoare_global, null);
            textViewWinnerPoints = popupViewPuncteCastigatoare.findViewById(R.id.textview_winner_points);
            nomeGiocoFooterTableRow = popupViewPuncteCastigatoare.findViewById(R.id.nome_gioco_global_table_row);
            nomeGiocoFooterTableRow.setVisibility(View.VISIBLE);
            puncteCastigatoareGlobalInserimanto = popupViewPuncteCastigatoare.findViewById(R.id.puncte_castigatoare_global_inserimento);
            puncteCastigatoareGlobalInserimanto.setVisibility(View.INVISIBLE);
            spinnerPuncteCastigatoarePrecedente = popupViewPuncteCastigatoare.findViewById(R.id.spinner_puncte_castigatoare_precedente);
            switchButton = popupViewPuncteCastigatoare.findViewById(R.id.switch_button_active_puncte_castigatoare_global);
        }


        db = AppDatabase.getPersistentDatabase(contesto);

        setOkCancelButton();

        configuraIlPopup();


        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            gestisci4GiocatoriInSquadra();
        }



    }
    private void configuraIlPopup(){
        if (!nuovaPartidaConAdaus) {
            popolaRecyclerViewPuncteCastigatoareGlobal();
            setMostraONascondiInputPuncteCastigatoare();
            listaJocuriRecyclerView = (RecyclerView) anchorView.findViewById(R.id.lista_jocuri_recycleview);
        }

        if (nuovoGioco) {
            popupWindow = new PopupWindow(popupViewPuncteCastigatoare,
                    listaJocuriRecyclerView.getWidth(), listaJocuriRecyclerView.getHeight());
        } else {
            popupWindow = new PopupWindow(popupViewPuncteCastigatoare, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        }
    }

    private void setOkCancelButton() {
        okButtonPopup = popupViewPuncteCastigatoare.findViewById(R.id.ok_button_puncte_castigatoare_global_popup);
        cancelButtonPopup = popupViewPuncteCastigatoare.findViewById(R.id.cancel_button_puncte_castigatoare_global_popup);
        okButtonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nuovoGioco) {
                    inserisciJocNou();
                } else if (nuovaPartida) {
                    inserisciPartidaNuova();
                } else if (nuovaPartidaConAdaus) {
                    inserisciAdausAllaPartida();
                }
            }
        });
        PopupPuncteCastigatoare thisPopup = this;
        cancelButtonPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisPopup.popupWindow.dismiss();
            }
        });
    }


    private void gestisci4GiocatoriInSquadra() {
        CampiInserimentoNuovoGiocoImpl campiInserimentoNuovoGioco = new CampiInserimentoNuovoGiocoImpl();
        if (nuovoGioco) {
            campiInserimentoNuovoGioco.popolaRigaCampiNuovoGioco4giocatoriInSquadra(nomeGiocoFooterTableRow);
            mappaCampi = campiInserimentoNuovoGioco.getMappaCampi();
        }


    }

    private void popolaRecyclerViewPuncteCastigatoareGlobal() {

        puncteCastigatoareGlobalList = db.puncteCastigatoareGlobalDao().selectAllPuncteCastigatoareGlobalOrderByData();

        adapterSpinner = new AdapterSpinner(contesto, R.layout.item_spinner_puncte_castigatoare, puncteCastigatoareGlobalList);

        spinnerPuncteCastigatoarePrecedente.setAdapter(adapterSpinner);
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
                TextView selectedView = (TextView) spinnerPuncteCastigatoarePrecedente.getSelectedView();
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

                    InputMethodManager imm = (InputMethodManager) contesto.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(puncteCastigatoareGlobalInserimanto.getWindowToken(), 0);


                }
            }
        });
    }

    public void showPopup() {

        popupWindow.setFocusable(true);

        if (nuovoGioco) {
            int[] location = new int[2];
            listaJocuriRecyclerView.getLocationOnScreen(location);
            popupWindow.showAtLocation(listaJocuriRecyclerView, Gravity.NO_GRAVITY,
                    location[0], location[1]);
        } else {
            popupWindow.showAtLocation(anchorView,Gravity.CENTER,0,0);

        }

    }

    public void inserisciJocNou() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            if (verificaIntegrita()) {

                InfoGiocoNuovo4GiocatoriInSquadra infoGiocoNuovo4GiocatoriInSquadra = new InfoGiocoNuovo4GiocatoriInSquadra(contesto);

                if (isNomeGiocoMostrabile) {
                    BorderedEditText nomeGioco = mappaCampi.get(ConstantiGlobal.ID_NOME_GIOCO);
                    infoGiocoNuovo4GiocatoriInSquadra.setNomeGioco(nomeGioco.getText().toString());
                    infoGiocoNuovo4GiocatoriInSquadra.setPuncteCastigatoare(winnerPoints);
                    infoGiocoNuovo4GiocatoriInSquadra.setIdGioco(idGioco);


                    BusinessInserimento4GiocatoriInSquadra businessInserimento4GiocatoriInSquadra = new BusinessInserimento4GiocatoriInSquadra(contesto,anchorView);

                    businessInserimento4GiocatoriInSquadra.inserisciPrimaVoltaNelDatabase(infoGiocoNuovo4GiocatoriInSquadra);

                    idGioco = businessInserimento4GiocatoriInSquadra.getIdGioco();
                }
                vaiNellaTabellaPunti();
            }
        }
    }

    public void inserisciPartidaNuova() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            if (verificaIntegrita()) {
                if (!isNomeGiocoMostrabile) {
                    InfoNuovaPartida4GiocatoriInSquadra infoNuovaPartida4GiocatoriInSquadra = new InfoNuovaPartida4GiocatoriInSquadra();
                    infoNuovaPartida4GiocatoriInSquadra.setIdGioco(idGioco);
                    infoNuovaPartida4GiocatoriInSquadra.setWinnerPoints(winnerPoints);
                    BusinessInserimento4GiocatoriInSquadra businessInserimento4GiocatoriInSquadra = new BusinessInserimento4GiocatoriInSquadra(contesto,anchorView);
                    businessInserimento4GiocatoriInSquadra.inserisciNuovaPartidaNeDatabase(infoNuovaPartida4GiocatoriInSquadra);
                }
                vaiNellaTabellaPunti();
            }
        }
    }

    private void inserisciAdausAllaPartida() {
        if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            if (verificaIntegrita()) {
                InfoWinnerPoints infoWinnerPoints = new InfoWinnerPoints();
                infoWinnerPoints.setIdGioco(idGioco);
                infoWinnerPoints.setWinnerPoints(winnerPoints);
                BusinessInserimento4GiocatoriInSquadra businessInserimento4GiocatoriInSquadra = new BusinessInserimento4GiocatoriInSquadra(contesto,anchorView);

                businessInserimento4GiocatoriInSquadra.inserisciAdausAllaPartida(infoWinnerPoints);
            }
        }
    }

    private boolean verificaIntegrita() {

        if (maxPuncte != null || !isNomeGiocoMostrabile) {
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
        if (maxPuncte != null && !isNomeGiocoMostrabile) {
            winnerPoints = Integer.parseInt(puncteCastigatoareGlobalInserimanto.getText().toString());
            return winnerPoints > maxPuncte;
        } else if (puncteCastigatoareGlobalInserimanto.getText() != null && IntegerUtils.isInteger(puncteCastigatoareGlobalInserimanto.getText().toString())) {
            winnerPoints = Integer.parseInt(puncteCastigatoareGlobalInserimanto.getText().toString());
            return true;
        } else if (spinnerPuncteCastigatoarePrecedente.isEnabled()) {

            PuncteCastigatoareGlobalBean puncteCastigatoareGlobalBean = (PuncteCastigatoareGlobalBean) spinnerPuncteCastigatoarePrecedente.getSelectedItem();

            winnerPoints = puncteCastigatoareGlobalBean.getPuncteCastigatoare();
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
