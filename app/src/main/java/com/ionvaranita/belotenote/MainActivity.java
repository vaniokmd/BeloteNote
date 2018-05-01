package com.ionvaranita.belotenote;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ionvaranita.belotenote.fragments.EmptyFragment;
import com.ionvaranita.belotenote.utils.DeviceUtils;

import java.util.List;
import java.util.logging.Logger;


import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;

public class MainActivity extends AppCompatActivity {
    private final Logger LOG = Logger.getLogger(this.getClass().getCanonicalName());
    private List<Fragment> fragmentList;

    private Fragment fragmentWelcome;
    private Fragment fragmentPagina4Jucatori;
    private Fragment fragmentMainActivity;
    private Fragment fragmentMenuPartide4JucatoriInEchipa;
    private EmptyFragment emptyFragment;

    private boolean isTablet;

    private FragmentManager fragmentManager;
    boolean tabletLandscape;

    //private Fragment fragmentTabella4JucatoriInEchipa;

    private int butonulApasat = MainActivityButtonChooser.NONE;

    @Override
    protected void onStart() {
        LOG.info("Activity onStart");
        super.onStart();

    }

    @Override
    protected void onResume() {
        LOG.info("Activity onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        LOG.info("Activity onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        LOG.info("Activity onStop");
        super.onStop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG.info("Activity onCreate");
        setContentView(com.ionvaranita.belotenote.R.layout.activity_main);
        if (savedInstanceState != null) {
            butonulApasat = savedInstanceState.getInt(MainActivityButtonChooser.BUTONUL_APASAT, MainActivityButtonChooser.NONE);
        } else {
            butonulApasat = getIntent().getIntExtra(MainActivityButtonChooser.BUTONUL_APASAT, MainActivityButtonChooser.NONE);

        }

        //  getActionBar().setDisplayHomeAsUpEnabled(true);
        emptyFragment = new EmptyFragment();

//        fragmentWelcome = (FragmentWelcome) getSupportFragmentManager().findFragmentById(R.id.fragment_welcome_pagina_patru_jucatori);

        fragmentWelcome = new FragmentWelcome();

        fragmentPagina4Jucatori = new FragmentPagina4Jucatori();

//        fragmentPagina4Jucatori = (FragmentPagina4Jucatori) getSupportFragmentManager().findFragmentById(R.id.fragment_pagina_patru_jucatori);
//      fragmentMainActivity = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment);
//        fragmentMenuPartide4JucatoriInEchipa = (FragmentMenuPartide4JucatoriInEchipa) getSupportFragmentManager().findFragmentById(R.id.fragment_menu_partide_4_jucatori_in_echipa);
        //fragmentTabella4JucatoriInEchipa = getSupportFragmentManager().findFragmentById(R.id.fragment_tabella_4_jucatori_in_echipa);

        fragmentMenuPartide4JucatoriInEchipa = new FragmentMenuPartide4JucatoriInEchipa();


        fragmentMainActivity = new MainActivityFragment();

        isTablet = DeviceUtils.isTablet(this);


        fragmentManager = getSupportFragmentManager();

        fragmentList = fragmentManager.getFragments();
        tabletLandscape = isTablet && this.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;


        //Se ci troviamo su un tablet in posizione LANDSCAPE
        if (isTablet) {
            if (tabletLandscape) {
                LOG.info("Tablet Landscape!");
                if (butonulApasat == MainActivityButtonChooser.PATRU_JUCATORI) {
                    LOG.info("Tablet LANDSCAPE - buton apasat PATRU_JUCATORI");
                    tabletLandscapePagina4Jucatori();
                } else if (butonulApasat == MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA) {
                    LOG.info("Butonul apasat:>>" + butonulApasat);
                    tabletLandscapePagina4JucatoriInEchipa();
                } else {
                    LOG.info("Tablet LANDSCAPE - launcher");
                    tabletLandscapeLauncher();

                }
            } else {

                if (butonulApasat == MainActivityButtonChooser.PATRU_JUCATORI) {
                    vaiNellaPagina4Jucatori();
                } else if (butonulApasat == MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA) {
                    vaiNelMenuPartide4JucatoriInEchipa();
                }
            }
            } else{
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                if (butonulApasat == MainActivityButtonChooser.PATRU_JUCATORI) {
                    vaiNellaPagina4Jucatori();
                } else if (butonulApasat == MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA) {
                    vaiNelMenuPartide4JucatoriInEchipa();
                }
            }
        }

    private void tabletLandscapePagina4Jucatori() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container1, fragmentMainActivity);
        fragmentTransaction.replace(R.id.fragment_container2, fragmentPagina4Jucatori);
        fragmentTransaction.replace(R.id.fragment_container3, emptyFragment);
        fragmentTransaction.commit();
    }

    private void tabletLandscapePagina4JucatoriInEchipa() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container1, fragmentMainActivity);
        fragmentTransaction.replace(R.id.fragment_container2, fragmentPagina4Jucatori);
        fragmentTransaction.replace(R.id.fragment_container3, fragmentMenuPartide4JucatoriInEchipa);
        fragmentTransaction.commit();
    }

    private void tabletLandscapeLauncher() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container1, fragmentMainActivity);
        fragmentTransaction.add(R.id.fragment_container2, fragmentWelcome);
        fragmentTransaction.commit();
    }

    public void apasaButonul4Jucatori(View view) {
        LOG.info("apasaButonul4Jucatori ");
        if (butonulApasat != MainActivityButtonChooser.PATRU_JUCATORI && butonulApasat != MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA) {
            butonulApasat = MainActivityButtonChooser.PATRU_JUCATORI;
            vaiNellaPagina4Jucatori();
        }
    }

    private void vaiNellaPagina4Jucatori() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //Se ci troviamo su un tablet in posizione LANDSCAPE
        if (isTablet) {
            if (tabletLandscape) {
                fragmentTransaction.replace(R.id.fragment_container2, fragmentPagina4Jucatori);
                fragmentTransaction.commit();
            } else {
                Intent intent = new Intent(this, Pagina4Jucatori.class);
                startActivity(intent);
            }

        } else {
            Intent intent = new Intent(this, Pagina4Jucatori.class);
            startActivity(intent);
        }

    }

    public void apasaButonul4JucatoriInEchipa(View view) {
        LOG.info("apasaButonul4InEchipaJucatori");
        if (butonulApasat != MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA) {
            butonulApasat = MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA;
            vaiNelMenuPartide4JucatoriInEchipa();
        }

    }

    private void vaiNelMenuPartide4JucatoriInEchipa() {
        if (tabletLandscape) {
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container3, fragmentMenuPartide4JucatoriInEchipa);
            fragmentTransaction.commit();
        } else {
            Intent intent = new Intent(this, MenuJocuri.class);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        LOG.info("MAIN ACTIVITY onSavedInstance called!!!!!!!!");
        super.onSaveInstanceState(outState);
        outState.putInt(MainActivityButtonChooser.BUTONUL_APASAT, butonulApasat);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        LOG.info("MAIN ACTIVITY onRestoreInstanceState called!!!!!!");
        super.onRestoreInstanceState(savedInstanceState);
        butonulApasat = savedInstanceState.getInt(MainActivityButtonChooser.BUTONUL_APASAT);
    }

    public void apasaButonul3Jucatori(View view) {

    }

    public void apasaButonul2Jucatori(View view) {
        Intent intent = new Intent(this, Pagina2Jucatori.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
       /* FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (tabletLandscape) {
            //workflow di 4 jucatori
            if (butonulApasat==MainActivityButtonChooser.PATRU_JUCATORI) {
                butonulApasat = MainActivityButtonChooser.NONE;
                nrVolteButonulApasat4Jucatori = 0;
                super.onBackPressed();
            }
            else if(butonulApasat==MainActivityButtonChooser.PATRU_JUCATORI_IN_ECHIPA){
                butonulApasat = MainActivityButtonChooser.PATRU_JUCATORI;
                nrVolteButonulApasat4JucatoriInEchipa = 0;
                super.onBackPressed();
            }
            else{
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        }
        else if(tabletPortrait){

        }
        else{

        }*/
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }

    private void backToMainActivityTabletLandScape() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        butonulApasat = MainActivityButtonChooser.NONE;
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof MainActivityFragment || fragment instanceof FragmentWelcome) {
                fragmentTransaction.show(fragment);
                continue;
            }
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
    }

    private void backToPaginaPatruJucatoriInEchipaTabletLandScape() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container3, null);
        fragmentTransaction.commit();
        butonulApasat = MainActivityButtonChooser.PATRU_JUCATORI;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
