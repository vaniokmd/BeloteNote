package com.ionvaranita.belotenote.popup;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ionvaranita.belotenote.KeyboardFragment;
import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.TabellaPunti;
import com.ionvaranita.belotenote.borders.BorderedEditText;
import com.ionvaranita.belotenote.business.BusinessInserimento4GiocatoriInSquadra;
import com.ionvaranita.belotenote.campo.factory.impl.CampiInserimentoPuntiImpl;
import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiInserimento;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.info.InfoCineACistigat;
import com.ionvaranita.belotenote.utils.GlobalButtonOnClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ionvaranita on 19/04/18.
 */

public class PopupWindowInserimentoPunti extends PopupWindow {
    private FragmentManager fragmentManager;
    private Integer actionCode;
    private Integer idGioco;
    private Integer idPartida;
    private LinearLayout keyboardLinearLayout;
    private View popupLayout;
    Map<Integer, BorderedEditText> mappaCampiInseriti;
    private Context context;
    private TableRow tableRow;
    List<BorderedEditText> listaCampi;

    private void init(View popupLayout) {
        context = popupLayout.getContext();
        tableRow = popupLayout.findViewById(R.id.inserimento_table_row);
    }

    private void inizializzaICampi4JucatoriInEchipa() {
        CampiInserimentoPuntiImpl inserimentoCampi = new CampiInserimentoPuntiImpl();

        inserimentoCampi.popolaRigaInserimento4GiocatoriInSquadra(tableRow);

        mappaCampiInseriti = inserimentoCampi.getMappaCampi();
        listaCampi = new ArrayList<>(mappaCampiInseriti.values());

    }

    public PopupWindowInserimentoPunti(Integer actionCode, Integer idGioco, View popupLayout, FragmentManager fragmentManager, int width, int height) {
        super(popupLayout, width, height);
        this.popupLayout = popupLayout;
        this.actionCode = actionCode;
        this.fragmentManager = fragmentManager;
        this.idGioco = idGioco;
        init(popupLayout);

        if (this.actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            gestisci4JucatorioInEchipa(popupLayout);
        }


    }

    private void gestisci4JucatorioInEchipa(View view) {

        inizializzaICampi4JucatoriInEchipa();
        final BorderedEditText inserimentoGioco = mappaCampiInseriti.get(ConstantiGlobal.ID_PUNTI_GIOCO_INSERIMENTO);
        inserimentoGioco.requestFocus();
        for (int i = 0; i < listaCampi.size(); i++) {
            final BorderedEditText campo = listaCampi.get(i);

            campo.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d("clicked campo con id", campo.getId() + "");
                    int inType = campo.getInputType(); // backup the input type
                    campo.setInputType(InputType.TYPE_NULL); // disable soft input
                    campo.onTouchEvent(event); // call native handler
                    campo.setInputType(inType); // disable soft input
                    campo.setSelection(campo.getText().toString().length());
                    campo.performClick();
                    return true; // consume touch even
                }
            });
            campo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (campo.isFocused()) {
                        Log.d("campo isFocused:", campo.isFocused() + "");
                        attivaLaKeyboardPerQuestoCampo(campo);
                    }
                }
            });
            if (campo.getId() == ConstantiGlobal.ID_PUNTI_GIOCO_INSERIMENTO) {
                campo.callOnClick();
            }
        }

        TextView calculatorPopup = view.findViewById(R.id.calculator_popup);
        final String stringaInizialeCalculatorAutomat = context.getResources().getString(R.string.note) + " " + context.getResources().getString(R.string.note_introduce) + " <b>" + context.getResources().getString(R.string.puncte_joaca) + "</b> " + context.getResources().getString(R.string.note_pentru_a_activa_calculatorul) + ".";

        calculatorPopup.setText(Html.fromHtml(stringaInizialeCalculatorAutomat));

    }

    private void attivaLaKeyboardPerQuestoCampo(final BorderedEditText campo) {
        KeyboardFragment keyboardFragment = (KeyboardFragment) fragmentManager.findFragmentById(R.id.container_keyboard_4_jucatori_in_echipa);

        keyboardLinearLayout = (LinearLayout) keyboardFragment.getView();
        int nrTabele = keyboardLinearLayout.getChildCount();
        for (int i = 0; i < nrTabele; i++) {
            TableLayout tabella = (TableLayout) keyboardLinearLayout.getChildAt(i);
            int nrRighe = tabella.getChildCount();
            for (int j = 0; j < nrRighe; j++) {
                TableRow riga = (TableRow) tabella.getChildAt(j);
                int nrButtoni = riga.getChildCount();
                for (int k = 0; k < nrButtoni; k++) {
                    final Button buttonKey = (Button) riga.getChildAt(k);

                    final String del = tableRow.getContext().getResources().getString(R.string.del);
                    final String done = tableRow.getContext().getResources().getString(R.string.done);
                    final String meno_10 = tableRow.getContext().getString(R.string.meno_10);
                    buttonKey.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new GlobalButtonOnClickListener(context).vibrate();
                            String valoreButton = buttonKey.getText().toString();


                            if (valoreButton.equals(done)) {
                                if (actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
                                    if(verificaIntegrita()){
                                        inserisciPuntiNelDatabase();

                                    }
                                }
                            } else if (valoreButton.equals(del)) {

                                if (campo.getText().toString().length() > 0) {
                                    if(campo.getText().toString().equals(meno_10)){
                                        campo.setText("");
                                    }
                                    else{
                                        campo.setText(campo.getText().toString().substring(0, campo.getText().toString().length() - 1));
                                        if(campo.getText().toString().isEmpty()){
                                            campoRimasto.setHint("");
                                        }
                                    }
                                }
                                EsitoCampoRimasto esitoCampoRimasto = calcolaCampoRimasto();
                                if(esitoCampoRimasto.isRimasto1Campo()){
                                    Log.d(esitoCampoRimasto.getCampoRimasto().toString(),"e ultimo rimasto: "+esitoCampoRimasto.isRimasto1Campo()+" campo precedente is focused and empty"+esitoCampoRimasto.isCampoFocusedAndEmpty());
                                    completaIlCampoRimasto(campo);
                                }



                            } else if (valoreButton.equals(meno_10)) {
                                if(campo.getId()!=ConstantiGlobal.ID_PUNTI_GIOCO_INSERIMENTO){
                                    campo.setText(buttonKey.getText().toString());
                                    calcolaCampoRimasto();

                                }
                            } else {
                                campo.setText(campo.getText().toString() + buttonKey.getText().toString());
                                EsitoCampoRimasto esitoCampoRimasto = calcolaCampoRimasto();
                                if(esitoCampoRimasto.isRimasto1Campo()){
                                    Log.d(esitoCampoRimasto.getCampoRimasto().toString(),"e ultimo rimasto: "+esitoCampoRimasto.isRimasto1Campo()+" campo precedente is focused and empty"+esitoCampoRimasto.isCampoFocusedAndEmpty());
                                }
                            }
                            campo.setSelection(campo.getText().toString().length());

                        }
                    });
                }
            }

        }

    }

    private void completaIlCampoRimasto(BorderedEditText campo) {

    }

    private void inizializzaCampoRimasto(BorderedEditText campo){

    }
    private BorderedEditText campoRimasto=null;


    private EsitoCampoRimasto calcolaCampoRimasto(){
        EsitoCampoRimasto esitoCampoRimasto = new EsitoCampoRimasto();

        List<BorderedEditText> listaCampi = new ArrayList<>(mappaCampiInseriti.values());


        Integer puntiGioco = null;
        int nrCampiCompletati = 0;
        boolean campoIsFocusedAndEmpty=false;

        Integer sommaAltriCampi = 0;

        for(BorderedEditText campo:listaCampi){
            if(campo.getId()==ConstantiGlobal.ID_PUNTI_GIOCO_INSERIMENTO){
                if(!campo.getText().toString().isEmpty()){
                    nrCampiCompletati++;
                    puntiGioco = Integer.parseInt(campo.getText().toString());
                }
            }
            else if(!campo.getText().toString().isEmpty()||campo.isFocused()){
                if(campo.isFocused()&&campo.getText().toString().isEmpty()){
                    campoIsFocusedAndEmpty=true;
                    campo.setHint("");
                    if(campoRimasto!=null){
                        campoRimasto.setHint("");
                    }
                }
                else{
                    sommaAltriCampi+=Integer.parseInt(fixMeno10(campo.getText().toString()));
                }
                nrCampiCompletati++;
            }
            else{
                campoRimasto=campo;
            }
        }

        boolean isRimasto1Campo = (listaCampi.size()-nrCampiCompletati)==1&&puntiGioco!=null;
        esitoCampoRimasto.setRimasto1Campo(isRimasto1Campo);
        if(isRimasto1Campo){

            esitoCampoRimasto.setCampoRimasto(campoRimasto);

            esitoCampoRimasto.setCampoFocusedAndEmpty(campoIsFocusedAndEmpty);
            if(!campoIsFocusedAndEmpty)campoRimasto.setHint(Integer.toString(puntiGioco-sommaAltriCampi));

        }
        return esitoCampoRimasto;

    }
    private String fixMeno10(String numero){
        if(numero.equals(context.getResources().getString(R.string.meno_10))){
            return "0";
        }
        return numero;
    }

    private void inserisciPuntiNelDatabase() {
        AppDatabase db = AppDatabase.getPersistentDatabase(context);

        if(actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA){
            Punti4GiocatoriInSquadraEntityBean entity =db.tabellaPunti4GiocatoriInSquadraDao().getLastRecordPunti4GiocatoriInSquadraByIdGioco(idGioco);

            String puntiGiocoString = mappaCampiInseriti.get(ConstantiGlobal.ID_PUNTI_GIOCO_INSERIMENTO).getText().toString();
            Integer puntiGioco = Integer.parseInt(puntiGiocoString);

            String puntiNoiString = mappaCampiInseriti.get(IdsCampiInserimento.ID_PUNTI_NOI_INSERIMENTO).getText().toString();
            Integer puntiNoi = Integer.parseInt(puntiNoiString);

            String puntiVoiString = mappaCampiInseriti.get(IdsCampiInserimento.ID_PUNTI_VOI_INSERIMENTO).getText().toString();
            Integer puntiVoi = Integer.parseInt(puntiVoiString);

            entity.setId(null);
            entity.setPuntiGioco(puntiGioco);
            entity.setPuntiNoi(puntiNoi);
            entity.setPuntiVoi(puntiVoi);
            BusinessInserimento4GiocatoriInSquadra businessInserimento4GiocatoriInSquadra = new BusinessInserimento4GiocatoriInSquadra(context,popupLayout);
            businessInserimento4GiocatoriInSquadra.setIdGioco(idGioco);
            businessInserimento4GiocatoriInSquadra.inserisciBeanNelDatabase(entity);
            idPartida = businessInserimento4GiocatoriInSquadra.getIdPartida();
            idGioco = businessInserimento4GiocatoriInSquadra.getIdGioco();

        }
    }

    private boolean verificaIntegrita() {
        BorderedEditText editTextPuncteJoaca = mappaCampiInseriti.get(ConstantiGlobal.ID_PUNTI_GIOCO_INSERIMENTO);
        String textPuncteJoaca = editTextPuncteJoaca.getText().toString();
        if (isInteger(textPuncteJoaca, editTextPuncteJoaca.getId())) {
            Integer puncteJoaca = Integer.parseInt(textPuncteJoaca);
            for (int i = 0; i < listaCampi.size(); i++) {
                BorderedEditText campo = listaCampi.get(i);
                if (campo.getId() != ConstantiGlobal.ID_PUNTI_GIOCO_INSERIMENTO) {
                    if (isInteger(campo.getText().toString(), campo.getId())) {
                        puncteJoaca -= Integer.parseInt(campo.getText().toString());
                    } else {
                        return false;
                    }
                }
            }
            return puncteJoaca == 0;
        }
        return false;

    }

    private void vaiNellaTabellaPunti(){
        Intent intent = new Intent(context, TabellaPunti.class);
        intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), idGioco);
        intent.putExtra(ConstantiGlobal.ACTION_CODE, actionCode);
        context.startActivity(intent);
    }

    private boolean isInteger(String numero, Integer idCampo) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (Exception e) {
            //TODO gestisco con un messaggio all'utente
            return false;
        }
    }


}
