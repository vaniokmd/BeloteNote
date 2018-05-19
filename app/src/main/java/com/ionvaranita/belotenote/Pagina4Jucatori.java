package com.ionvaranita.belotenote;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.MainActivityButtonChooser;
import com.ionvaranita.belotenote.utils.DeviceUtils;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

/**
 * Created by ionvaranita on 15/01/2018.
 */

public class Pagina4Jucatori extends AppCompatActivity {
    private int actionCode = ActionCode.GIOCATORI_4_IN_SQUADRA;
    private boolean tabletLandscape;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_4_jucatori);
        isTablet = DeviceUtils.isTablet(this);
        tabletLandscape = isTablet && this.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        getSupportActionBar().setCustomView(R.layout.action_bar_main_activity);
        TextView titoloActioBar =getSupportActionBar().getCustomView().findViewById(R.id.action_bar_title);
        titoloActioBar.setText(R.string.patru_jucatori);

        if (isTablet) {
            if (tabletLandscape) {
                this.onBackPressed();
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        }

    }

    public void apasaButonulInEchipa(View view) {
        Intent vai4InEchipa = new Intent(this.getApplicationContext(), MenuJocuri.class);
        vai4InEchipa.putExtra(ConstantiGlobal.ACTION_CODE,actionCode);
        startActivity(vai4InEchipa);
    }

    @Override
    public void onBackPressed() {
        Intent vaiIndietro = new Intent(this, MainActivity.class);

        vaiIndietro.putExtra(MainActivityButtonChooser.BUTONUL_APASAT, MainActivityButtonChooser.NONE_2);
        if (tabletLandscape)vaiIndietro.putExtra(MainActivityButtonChooser.BUTONUL_APASAT, MainActivityButtonChooser.PATRU_JUCATORI);
        startActivity(vaiIndietro);
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
