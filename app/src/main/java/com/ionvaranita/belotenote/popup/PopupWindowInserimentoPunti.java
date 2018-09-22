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
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.WhoPlayEntityBean;
import com.ionvaranita.belotenote.info.ParametersPopupWindowInserimentoPunti;
import com.ionvaranita.belotenote.utils.GlobalButtonOnClickListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.ionvaranita.belotenote.constanti.ConstantiGlobal.STRING_BOLT;

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
    private Map<Integer, BorderedEditText> mappaCampiInseriti;
    private Context context;
    private TableRow tableRow;
    private List<BorderedEditText> listaCampiInserimento;

    private boolean ceQualcunoCheGioca;

    private TextView calculatorPopup;

    private void init(View popupLayout) {
        context = popupLayout.getContext();
        tableRow = popupLayout.findViewById(R.id.inserimento_table_row);
    }

    private void inizializzaICampi4JucatoriInEchipa() {
        CampiInserimentoPuntiImpl inserimentoCampi = new CampiInserimentoPuntiImpl();

        inserimentoCampi.popolaRigaInserimento4GiocatoriInSquadra(tableRow);

        mappaCampiInseriti = inserimentoCampi.getMappaCampi();
        listaCampiInserimento = new ArrayList<>(mappaCampiInseriti.values());

    }

    public PopupWindowInserimentoPunti(ParametersPopupWindowInserimentoPunti parametersPopupWindowInserimentoPunti) {
        super(parametersPopupWindowInserimentoPunti.getPopupLayout(),parametersPopupWindowInserimentoPunti.getWidth(),parametersPopupWindowInserimentoPunti.getHeight());
        this.popupLayout = parametersPopupWindowInserimentoPunti.getPopupLayout();
        this.actionCode = parametersPopupWindowInserimentoPunti.getActionCode();
        this.fragmentManager = parametersPopupWindowInserimentoPunti.getFragmentManager();
        this.idGioco = parametersPopupWindowInserimentoPunti.getIdGioco();
        this.ceQualcunoCheGioca = parametersPopupWindowInserimentoPunti.isGiocaQualcuno();
        init(popupLayout);

        if (this.actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA) {
            gestisci4JucatorioInEchipa(popupLayout);
        }


    }


    private void gestisci4JucatorioInEchipa(View view) {

        inizializzaICampi4JucatoriInEchipa();
        final BorderedEditText inserimentoGioco = mappaCampiInseriti.get(IdsCampiStampa.ID_PUNTI_GIOCO);
        inserimentoGioco.requestFocus();
        for (int i = 0; i < listaCampiInserimento.size(); i++) {

            final BorderedEditText campo = listaCampiInserimento.get(i);

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
            if (campo.getId() == IdsCampiStampa.ID_PUNTI_GIOCO) {
                campo.callOnClick();
            }
        }

        calculatorPopup = view.findViewById(R.id.calculator_popup);
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
                    final String bolt = tableRow.getContext().getString(R.string.bolt);
                    if(buttonKey.getText().toString().equals(meno_10)){
                        buttonKey.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                AppDatabase db = AppDatabase.getPersistentDatabase(context);
                                WhoPlayEntityBean whoPlayEntityBean = db.whoPlayedDao().getWhoPlayedByIdGioco(idGioco);
                                if(whoPlayEntityBean!=null){
                                    Integer idCampoCheGioca = whoPlayEntityBean.getIdPersona();
                                    if(campo.getId()!=IdsCampiStampa.ID_PUNTI_GIOCO&&campo.getId()==idCampoCheGioca){
                                        new GlobalButtonOnClickListener(context).vibrate();
                                        campo.setText(STRING_BOLT);
                                        calcolaCampoRimasto();
                                        if(multimeaCampiRimasti.size()==1){
                                            multimeaCampiRimasti.iterator().next().setHint(hintDifferenza.toString());
                                        }
                                    }
                                }
                                return true;
                            }
                        });
                    }
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

                                    }
                                }
                                calcolaCampoRimasto();

                                if(campo.getText().toString().isEmpty()){
                                    if(multimeaCampiRimasti.size()>1){
                                        cancellaHintCampiRimasti();
                                    }
                                }
                                else{
                                    if(multimeaCampiRimasti.size()==1){
                                        multimeaCampiRimasti.iterator().next().setHint(hintDifferenza.toString());
                                    }
                                }

                            } else if (valoreButton.equals(meno_10)) {
                                if(campo.getId()!=IdsCampiStampa.ID_PUNTI_GIOCO){
                                    campo.setText("-10");
                                    calcolaCampoRimasto();

                                }
                            } else {
                                campo.setText(campo.getText().toString() + buttonKey.getText().toString());
                                calcolaCampoRimasto();
                                if(multimeaCampiRimasti.size()==1){
                                    multimeaCampiRimasti.iterator().next().setHint(hintDifferenza.toString());
                                }

                            }
                            campo.setSelection(campo.getText().toString().length());

                        }
                    });
                }
            }

        }

    }

    private void cancellaHintCampiRimasti(){
        Iterator<BorderedEditText> iteratorCampi =multimeaCampiRimasti.iterator();
        while (iteratorCampi.hasNext()){
            iteratorCampi.next().setHint("");
        }
        hintDifferenza=0;
    }

    Set<BorderedEditText> multimeaCampiRimasti = new HashSet<>();
    private Integer hintDifferenza = 0;
    Integer puntiGioco = null;

    private void calcolaCampoRimasto(){


        List<BorderedEditText> listaCampi = new ArrayList<>(mappaCampiInseriti.values());

        multimeaCampiRimasti.clear();

        int contenitore = 0;

        for(BorderedEditText campo:listaCampi){
            if(campo.getId()==IdsCampiStampa.ID_PUNTI_GIOCO){
                if(!campo.getText().toString().isEmpty()){

                    puntiGioco = Integer.parseInt(campo.getText().toString());
                }
                else{
                    puntiGioco=null;
                    multimeaCampiRimasti.add(campo);

                }
            }
            else if(!campo.getText().toString().isEmpty()||campo.isFocused()){
                if(campo.isFocused()&&campo.getText().toString().isEmpty()){
                    multimeaCampiRimasti.add(campo);
                }
                else{
                    Integer idCampoBoltPResente = isBoltPresente();
                    if (idCampoBoltPResente != null) {

                    }
                    contenitore+=Integer.parseInt(fixBoltOrMeno10(campo.getText().toString()));

                }

            }
            else{
                multimeaCampiRimasti.add(campo);
            }
        }
        if(puntiGioco!=null&&multimeaCampiRimasti.size()==1){
            hintDifferenza = puntiGioco - contenitore;
        }
        else if(puntiGioco==null&&multimeaCampiRimasti.size()==1){
            hintDifferenza = contenitore;
        }

    }

    private String fixBoltOrMeno10(String numero){
        boolean isMeno10 = numero.equals("-10");
        boolean isBolt = numero.equals("B");
        if(isMeno10||isBolt){
            return "0";
        }
        return numero;
    }
    private Integer isBoltPresente(){
        for (Integer idCampo:mappaCampiInseriti.keySet()
             ) {
            BorderedEditText campo = mappaCampiInseriti.get(idCampo);
            String valoreCampo = campo.getText().toString();
            if(valoreCampo.equals(STRING_BOLT)){
                return idCampo;
            }
        }
        return null;
    }




    private void inserisciPuntiNelDatabase() {
        AppDatabase db = AppDatabase.getPersistentDatabase(context);

        if(actionCode == ActionCode.GIOCATORI_4_IN_SQUADRA){
            Punti4GiocatoriInSquadraEntityBean entity =db.tabellaPunti4GiocatoriInSquadraDao().getLastRecordPunti4GiocatoriInSquadraByIdGioco(idGioco);

            String puntiGiocoString = mappaCampiInseriti.get(IdsCampiStampa.ID_PUNTI_GIOCO).getText().toString();
            Integer puntiGioco = Integer.parseInt(puntiGiocoString);

            String puntiNoiString = mappaCampiInseriti.get(IdsCampiStampa.ID_PUNTI_NOI).getText().toString();
            Integer puntiNoi = Integer.parseInt(fixBoltOrMeno10(puntiNoiString));

            String puntiVoiString = mappaCampiInseriti.get(IdsCampiStampa.ID_PUNTI_VOI).getText().toString();
            Integer puntiVoi = Integer.parseInt(fixBoltOrMeno10(puntiVoiString));


            Integer idCampoBolt = isBoltPresente();

            entity.setId(null);
            entity.setPuntiGioco(puntiGioco);
            entity.setPuntiNoi(puntiNoi);
            entity.setPuntiVoi(puntiVoi);
            BusinessInserimento4GiocatoriInSquadra businessInserimento4GiocatoriInSquadra = new BusinessInserimento4GiocatoriInSquadra(context,idGioco);

            businessInserimento4GiocatoriInSquadra.inserisciBeanNelDatabase(entity,idCampoBolt);
            idPartida = businessInserimento4GiocatoriInSquadra.getIdPartida();
            idGioco = businessInserimento4GiocatoriInSquadra.getIdGioco();




        }
    }

    private boolean verificaIntegrita() {
        if(ceQualcunoCheGioca){
            BorderedEditText editTextPuncteJoaca = mappaCampiInseriti.get(IdsCampiStampa.ID_PUNTI_GIOCO);
            String textPuncteJoaca = editTextPuncteJoaca.getText().toString();
            if (isInteger(textPuncteJoaca, editTextPuncteJoaca.getId())) {
                Integer puncteJoaca = Integer.parseInt(textPuncteJoaca);
                for (int i = 0; i < listaCampiInserimento.size(); i++) {
                    BorderedEditText campo = listaCampiInserimento.get(i);
                    if (campo.getId() != IdsCampiStampa.ID_PUNTI_GIOCO) {
                        String valoreCampo = fixBoltOrMeno10(campo.getText().toString());
                        if (isInteger(valoreCampo, campo.getId())) {
                            puncteJoaca -= Integer.parseInt(valoreCampo);
                        } else {
                            return false;
                        }
                    }
                }
                return puncteJoaca == 0;
            }
        }
        else{
            calculatorPopup.setText("Tieni premuto per qulache secondo la squadra che sta giocando");

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
