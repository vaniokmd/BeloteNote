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
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;

import static android.graphics.Typeface.BOLD;

/**
 * Created by ionvaranita on 15/04/18.
 */

public class PopupVreiSaContinuiCuOPartidaNoua {
    private Context contesto;
    private Integer actionCode;
    private Integer idJoc;
    private Integer idPartida;
    private View popupViewCineACastigat;
    private String cineACastigatEnum;
    private Button da;
    private Button nu;
    private PopupWindow popupWindow;

    private TableRow risultatoStampatoCineACastigat;
    private TableRow valoriRisultatoCineACastigat;
    private View mainView ;

    private LayoutInflater layoutInflater;

    private TextView testoACastigat;

    private PopupVreiSaContinuiCuOPartidaNoua(Context context, Integer actionCode, Integer idJoc, Integer idPartida, String cineACastigatEnum){
        contesto = context;
        this.actionCode = actionCode;
        this.idJoc = idJoc;
        this.idPartida = idPartida;
        this.cineACastigatEnum = cineACastigatEnum;
        layoutInflater = ((LayoutInflater) contesto.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        popupViewCineACastigat = layoutInflater.inflate(R.layout.popup_cine_a_castigat,null);
        testoACastigat = popupViewCineACastigat.findViewById(R.id.testo_a_castigat);

        mainView = layoutInflater.inflate(R.layout.popup_puncte_castigatoare_global,null);

        risultatoStampatoCineACastigat = popupViewCineACastigat.findViewById(R.id.risultato_stampato_cine_a_castigat);
        valoriRisultatoCineACastigat = popupViewCineACastigat.findViewById(R.id.valori_risultato_stampato_cine_a_castigat);

        popupWindow = new PopupWindow(popupViewCineACastigat, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);

        da = popupViewCineACastigat.findViewById(R.id.da_vreau_sa_continui_cu_o_partida_noua);
        da.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup = new ParametersPuncteCastigatoarePopup();
                parametersPuncteCastigatoarePopup.setActioCode(actionCode);
                parametersPuncteCastigatoarePopup.setMainView(mainView);
                parametersPuncteCastigatoarePopup.setNomeGiocoMostrabile(false);

                PopupPuncteCastigatoare popupPuncteCastigatoare = new PopupPuncteCastigatoare(parametersPuncteCastigatoarePopup,idJoc,idPartida);
                popupPuncteCastigatoare.showPopup();

            }
        });
        nu = popupViewCineACastigat.findViewById(R.id.nu_vreau_sa_continui_cu_o_partida_noua);
        nu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupWindow.dismiss();
            }
        });


    }

    public PopupVreiSaContinuiCuOPartidaNoua(Context context, Integer actionCode, Integer idJoc, Integer idPartida,String cineACastigatEnum,Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean){
        this(context,actionCode,idJoc,idPartida,cineACastigatEnum);

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






        if(cineACastigatEnum.equals(testoNoi)){
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
        else if(cineACastigatEnum.equals(testoVoi)){
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
    public void showPopup(){

        popupWindow.showAtLocation(mainView, Gravity.CENTER,0,0);
    }

    private void vaiNellaTabellaPunti(){
        Intent intent = new Intent(contesto, TabellaPunti.class);
        intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), idJoc);
        intent.putExtra(ConstantiGlobal.ACTION_CODE, actionCode);
        contesto.startActivity(intent);
    }

}
