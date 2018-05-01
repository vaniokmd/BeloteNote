package com.ionvaranita.belotenote;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.ionvaranita.belotenote.adapters.AdapterJocuri4JucatoriInEchipa;
import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.popup.ParametersPuncteCastigatoarePopup;
import com.ionvaranita.belotenote.utils.DeviceUtils;

import java.util.List;
import java.util.logging.Logger;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * Created by ionvaranita on 11/12/2017.
 */

public class MenuJocuri extends AppCompatActivity {
    private int actionCode;

    private Integer puncteCastigatoare;
    private static final Logger LOG = Logger.getLogger(MenuJocuri.class.getCanonicalName());
    private List<Gioco4GiocatoriInSquadra> listaPuncteCastigatoare4JucatoriInEchipa;
    private AppDatabase db;
    private RecyclerView itemsMenuRecyclerView;
    private boolean tabletLandscape;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(com.ionvaranita.belotenote.R.layout.menu_jocuri);

        isTablet = DeviceUtils.isTablet(this);

        tabletLandscape = isTablet && this.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
        if (isTablet) {
            if (tabletLandscape) {
                this.onBackPressed();
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        actionCode = getIntent().getIntExtra(ConstantiGlobal.ACTION_CODE,-1);
        if(actionCode==-1){
            Integer.parseInt("action code");
        }

        db = AppDatabase.getPersistentDatabase(this);
        listaPuncteCastigatoare4JucatoriInEchipa = db.joc4JucatoriInEchipaDao().selectAllJocuri4JucatoriInEchipa();

        AdapterJocuri4JucatoriInEchipa adapterPatruJucatoriinEchipa = new AdapterJocuri4JucatoriInEchipa(this, listaPuncteCastigatoare4JucatoriInEchipa);

        itemsMenuRecyclerView = (RecyclerView) findViewById(com.ionvaranita.belotenote.R.id.lista_jocuri_recycleview);

        itemsMenuRecyclerView.setAdapter(adapterPatruJucatoriinEchipa);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        itemsMenuRecyclerView.setLayoutManager(linearLayoutManager);
    }
    public Integer getPuncteCastigatoare() {
        return puncteCastigatoare;
    }

    public void setPuncteCastigatoare(Integer puncteCastigatoare) {
        this.puncteCastigatoare = puncteCastigatoare;
    }

    public void inserisciJocNou(View view){
        ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup = new ParametersPuncteCastigatoarePopup();
        parametersPuncteCastigatoarePopup.setActioCode(ActionCode.GIOCATORI_4_IN_SQUADRA);
        parametersPuncteCastigatoarePopup.setMainWindow(this.getWindow());
        parametersPuncteCastigatoarePopup.setNomeGiocoMostrabile(true);
        parametersPuncteCastigatoarePopup.setActioCode(actionCode);


        PopupPuncteCastigatoare popupPuncteCastigatoare =  new PopupPuncteCastigatoare(parametersPuncteCastigatoarePopup);
        popupPuncteCastigatoare.showPopup();
    }


    @Override
    public void onBackPressed() {
        Intent vaiIndietro = new Intent(this, Pagina4Jucatori.class);
        if (tabletLandscape) {
            vaiIndietro = new Intent(this, MainActivity.class);
            vaiIndietro.putExtra(MainActivityButtonChooser.BUTONUL_APASAT, MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA);
        }
        startActivity(vaiIndietro);
    }
}
