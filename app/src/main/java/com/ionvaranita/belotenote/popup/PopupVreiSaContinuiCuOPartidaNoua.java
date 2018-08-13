package com.ionvaranita.belotenote.popup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableRow;
import android.widget.TextView;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.TabellaPunti;
import com.ionvaranita.belotenote.business.BusinessInserimento4GiocatoriInSquadra;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.info.InfoCineACistigat;
import com.ionvaranita.belotenote.info.InfoRigaVuota4GiocatoriInSquadra;

import static android.graphics.Typeface.BOLD;

/**
 * Created by ionvaranita on 15/04/18.
 */

public class PopupVreiSaContinuiCuOPartidaNoua {
    private Context contesto;
    private Integer actionCode;
    private Integer idGioco;
    private Integer idPartida;
    private View popupViewCineACastigat;
//    private Button da;
    private Button ok;
    private PopupWindow popupWindow;

    private TableRow risultatoStampatoCineACastigat;
    private TableRow valoriRisultatoCineACastigat;
    private View mainView ;

    private LayoutInflater layoutInflater;

    private TextView testoACastigat;

    private AppDatabase db;

    private InfoCineACistigat infoCineACistigat;

    public PopupVreiSaContinuiCuOPartidaNoua(ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup){
        contesto = parametersPuncteCastigatoarePopup.getContext();
        this.actionCode = parametersPuncteCastigatoarePopup.getActioCode();
        this.idGioco = parametersPuncteCastigatoarePopup.getIdGioco();

        this.db = AppDatabase.getPersistentDatabase(contesto);

        this.idPartida = db.tabellaPunti4GiocatoriInSquadraDao().getLastRecordPunti4GiocatoriInSquadraByIdGioco(idGioco).getIdPartida();


        this.infoCineACistigat = parametersPuncteCastigatoarePopup.getInfoCineACistigat();

        layoutInflater = ((LayoutInflater) contesto.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        popupViewCineACastigat = layoutInflater.inflate(R.layout.popup_cine_a_castigat,null);
        testoACastigat = popupViewCineACastigat.findViewById(R.id.testo_a_castigat);

        mainView = layoutInflater.inflate(R.layout.popup_puncte_castigatoare_global,null);

        risultatoStampatoCineACastigat = popupViewCineACastigat.findViewById(R.id.risultato_stampato_cine_a_castigat);
        valoriRisultatoCineACastigat = popupViewCineACastigat.findViewById(R.id.valori_risultato_stampato_cine_a_castigat);

        popupWindow = new PopupWindow(popupViewCineACastigat, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        int lastMatchWinnerPoints = db.puncteCastigatoare4JucatoriInEchipaDao().selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndIdPartida(idGioco,idPartida).getPuncteCastigatoare();
        /*da = popupViewCineACastigat.findViewById(R.id.da_vreau_sa_continui_cu_o_partida_noua);
        da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup = new ParametersPuncteCastigatoarePopup();
                parametersPuncteCastigatoarePopup.setActioCode(actionCode);
                parametersPuncteCastigatoarePopup.setIdGioco(idGioco);
                parametersPuncteCastigatoarePopup.setContext(contesto);
                if(!infoCineACistigat.aflaCineACistigat().equalsIgnoreCase(ConstantiGlobal.CONTINUA)||!infoCineACistigat.aflaCineACistigat().equalsIgnoreCase(ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI)){
                    parametersPuncteCastigatoarePopup.setNuovaPartida(true);
                }
                if(infoCineACistigat.aflaCineACistigat().equalsIgnoreCase(ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI)){
                    try {
                        throw new Exception("Nu Are ce cauta CONTINUA_CON_AGGIUNTA_PUNTI aici" );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                PopupPuncteCastigatoare popupPuncteCastigatoare = new PopupPuncteCastigatoare(parametersPuncteCastigatoarePopup);
                popupPuncteCastigatoare.showPopup();

            }
        });*/

        ok = popupViewCineACastigat.findViewById(R.id.nu_vreau_sa_continui_cu_o_partida_noua);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(infoCineACistigat.isUnVincitore()){
                    InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra = new InfoRigaVuota4GiocatoriInSquadra();
                    infoRigaVuota4GiocatoriInSquadra.setWinnerPoints(lastMatchWinnerPoints);
                    infoRigaVuota4GiocatoriInSquadra.setIdGioco(idGioco);
                    infoRigaVuota4GiocatoriInSquadra.setIdPartida(idPartida);
                    infoRigaVuota4GiocatoriInSquadra.setInfoCineACistigat(infoCineACistigat);

                    BusinessInserimento4GiocatoriInSquadra businessInserimento4GiocatoriInSquadra = new BusinessInserimento4GiocatoriInSquadra(contesto);
                    businessInserimento4GiocatoriInSquadra.finisciPartida(infoRigaVuota4GiocatoriInSquadra);
                }
                vaiNellaTabellaPunti();
                popupWindow.dismiss();
            }
        });


    }



    public PopupVreiSaContinuiCuOPartidaNoua(ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup,Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean){
        this(parametersPuncteCastigatoarePopup);

        String testoCineACastigat = contesto.getResources().getString(R.string.a_castigat);

        String testoNoi = contesto.getResources().getString(R.string.noi);
        String testoVoi = contesto.getResources().getString(R.string.voi);



        StyleSpan styleSpan = new StyleSpan(BOLD);
        String testoFinale = null;

        TextView noiTexView = new TextView(contesto);
        TextView voiTexView = new TextView(contesto);


        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f);

        noiTexView.setLayoutParams(layoutParams);
        voiTexView.setLayoutParams(layoutParams);

        noiTexView.setGravity(Gravity.CENTER);
        voiTexView.setGravity(Gravity.CENTER);

        noiTexView.setText(R.string.noi);
        voiTexView.setText(R.string.voi);

        noiTexView.setTextColor(contesto.getResources().getColor(R.color.color_noi));
        voiTexView.setTextColor(contesto.getResources().getColor(R.color.color_voi));
        float textSize = contesto.getResources().getDimension(R.dimen.text_size_26);
        noiTexView.setTextSize(textSize);
        voiTexView.setTextSize(textSize);

        TextView puncteNoiTextView = new TextView(contesto);
        TextView puncteVoiTextView = new TextView(contesto);

        puncteNoiTextView.setLayoutParams(layoutParams);
        puncteVoiTextView.setLayoutParams(layoutParams);

        puncteNoiTextView.setGravity(Gravity.CENTER);
        puncteVoiTextView.setGravity(Gravity.CENTER);

        puncteNoiTextView.setTextSize(textSize);
        puncteVoiTextView.setTextSize(textSize);

        puncteNoiTextView.setText(punti4GiocatoriInSquadraEntityBean.getPuntiNoi().toString());
        puncteVoiTextView.setText(punti4GiocatoriInSquadraEntityBean.getPuntiVoi().toString());
        String cineACistigat = infoCineACistigat.aflaCineACistigat();
        if(cineACistigat.equals(InfoCineACistigat.WINNER_IS_WE)){
            noiTexView.setTypeface(null, Typeface.BOLD_ITALIC);
            puncteNoiTextView.setTypeface(null, Typeface.BOLD_ITALIC);

            testoFinale = testoCineACastigat+" "+testoNoi;

            SpannableString spannableString =  new SpannableString(testoFinale);
            int colorNoi = contesto.getResources().getColor(R.color.color_noi);

            puncteNoiTextView.setTextColor(colorNoi);

            spannableString.setSpan(styleSpan,testoCineACastigat.length()+1,testoCineACastigat.length()+1+testoNoi.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(colorNoi),testoCineACastigat.length()+1,testoCineACastigat.length()+1+testoNoi.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

            testoACastigat.setText(spannableString);

        }
        else if(cineACistigat.equals(InfoCineACistigat.WINNER_IS_YOU)){
            puncteVoiTextView.setTypeface(null, Typeface.BOLD_ITALIC);

            voiTexView.setTypeface(null,Typeface.BOLD_ITALIC);
            testoFinale = testoCineACastigat+" "+testoVoi;

            SpannableString spannableString =  new SpannableString(testoFinale);

            int colorVoi = contesto.getResources().getColor(R.color.color_voi);

            puncteVoiTextView.setTextColor(colorVoi);

            spannableString.setSpan(styleSpan,testoCineACastigat.length()+1,testoCineACastigat.length()+1+testoVoi.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(colorVoi),testoCineACastigat.length()+1,testoCineACastigat.length()+1+testoVoi.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            testoACastigat.setText(spannableString);
        }
        risultatoStampatoCineACastigat.addView(noiTexView);
        risultatoStampatoCineACastigat.addView(voiTexView);
        valoriRisultatoCineACastigat.addView(puncteNoiTextView);
        valoriRisultatoCineACastigat.addView(puncteVoiTextView);
    }

    private void setDaOnclickListenerLatMatchWinnerPoints(int lastMatchWinnerPoints){

    }
    public void showPopup(){

        popupWindow.showAtLocation(mainView, Gravity.CENTER,0,0);
    }

    private void vaiNellaTabellaPunti(){
        Intent intent = new Intent(contesto, TabellaPunti.class);
        intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), idGioco);
        intent.putExtra(ConstantiGlobal.ACTION_CODE, actionCode);
        contesto.startActivity(intent);
    }

}
