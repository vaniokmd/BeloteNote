package com.ionvaranita.belotenote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ionvaranita.belotenote.adapters.AdapterJocuri4JucatoriInEchipa;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.TurnManagement4GiocatoriInSquadra;

import java.util.List;
import java.util.logging.Logger;


public class FragmentMenuPartide4JucatoriInEchipa extends Fragment {

    private ImageButton inserisciNuovaPartida;
    List<Gioco4GiocatoriInSquadra> listaPartideBean;
    AppDatabase db;
    //TODO idJoc
    private Integer idJoc;
    private Integer idPartida;
    private final Logger LOG = Logger.getLogger(this.getClass().getSimpleName());
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LOG.info("onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG.info("onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LOG.info("onCreateView: ");
        View fragmentMenuPartide4JucatoriInEchipa = inflater.inflate(R.layout.fragment_menu_partide_4_jucatori_in_echipa, container, false);
        db = AppDatabase.getPersistentDatabase(inflater.getContext());
        listaPartideBean = db.joc4JucatoriInEchipaDao().selectAllJocuri4JucatoriInEchipa();

        inserisciNuovaPartida = (ImageButton)fragmentMenuPartide4JucatoriInEchipa.findViewById(R.id.inserisci_partida_4_jucatori_in_echipa_button_from_fragment);
        final Context context = this.getContext();
        inserisciNuovaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Inserimento nuova partida",Toast.LENGTH_LONG).show();
                inserisciUnaNuovaPartida4JucatoriInEchipa(view);
            }
        });

        AdapterJocuri4JucatoriInEchipa adapterPatruJucatoriinEchipa = new AdapterJocuri4JucatoriInEchipa(fragmentMenuPartide4JucatoriInEchipa.getContext(), listaPartideBean);

        RecyclerView itemsMenuPatruJucatoriinEchipaRecyclerView = (RecyclerView) fragmentMenuPartide4JucatoriInEchipa.findViewById(com.ionvaranita.belotenote.R.id.lista_jocuri_recycleview);

        itemsMenuPatruJucatoriinEchipaRecyclerView.setAdapter(adapterPatruJucatoriinEchipa);
        //idPartida=intent.getIntExtra(Turnul4GiocatoriInSquadraEnum.ID_PARTIDA.getDescrizione(),0);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentMenuPartide4JucatoriInEchipa.getContext());
//        linearLayoutManager.setReverseLayout(true);

//        linearLayoutManager.setStackFromEnd(true);

        itemsMenuPatruJucatoriinEchipaRecyclerView.setLayoutManager(linearLayoutManager);
        return fragmentMenuPartide4JucatoriInEchipa;
    }
    public void inserisciUnaNuovaPartida4JucatoriInEchipa(View view) {
        final Context context = this.getContext();
        final EditText input = new EditText(context);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setView(input);

        int maxLength = 11;
        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(maxLength);

        InputFilter filtertxt = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (source.charAt(i) == '\n') {
                        return "";
                    }
                }
                return source;
            }
        };

        fArray[1] = filtertxt;

        input.setFilters(fArray);
        input.setImeActionLabel("OK", KeyEvent.KEYCODE_ENTER);

        input.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                LOG.info("OnEditorActionPressed");
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    LOG.info("Enter pressed");
                    vaiNellaPaginaPatruJucatoriInEchipa(input);
                }
                return false;
            }
        });


/*
        input.setOnKeyListener(new EditText.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                LOG.info("onKey method call");
                LOG.info("KeyEvent keyCode: "+event.getKeyCode());
                LOG.info("int keyCode :"+keyCode);
                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        keyCode == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    LOG.info("ENTER key pressed");
                    vaiNellaPaginaPatruJucatoriInEchipa(input);
                    return true;

                }
                return  false; //Pass to the next listener


            }
        });
*/


        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vaiNellaPaginaPatruJucatoriInEchipa(input);

            }

        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
        input.setOnFocusChangeListener(new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                input.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
        input.requestFocus();
    }

    private void vaiNellaPaginaPatruJucatoriInEchipa(TextView input) {
        Gioco4GiocatoriInSquadra partidaPatruJucatoriInEchipaEntityBean = new Gioco4GiocatoriInSquadra();

        partidaPatruJucatoriInEchipaEntityBean.setNumeGioco(input.getText().toString());

        db.joc4JucatoriInEchipaDao().insertJoc4JucatoriInEchipa(partidaPatruJucatoriInEchipaEntityBean);
        Gioco4GiocatoriInSquadra lastPartida = db.joc4JucatoriInEchipaDao().selectLastJocInserit4JucatoriInEchipa();

        Integer idPartida = lastPartida.getIdGioco();

        Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean = new Punti4GiocatoriInSquadraEntityBean();

        punti4GiocatoriInSquadraEntityBean.setIdGioco(idPartida);

        db.tabellaPunti4GiocatoriInSquadraDao().inserisciPunti4GiocatoriInSquadra(punti4GiocatoriInSquadraEntityBean);


        TurnManagement4GiocatoriInSquadra turnManagement4GiocatoriInSquadra = new TurnManagement4GiocatoriInSquadra();

        turnManagement4GiocatoriInSquadra.setIdGioco(idJoc);

        turnManagement4GiocatoriInSquadra.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.toString());

        db.turnManagement4GiocatoriInSquadraDao().insertTurnManagement4JucatoriInEchipa(turnManagement4GiocatoriInSquadra);

        Intent intent = new Intent(this.getActivity(), TabellaPunti.class);

        intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_PARTIDA.toString(), idPartida);

        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LOG.info("onViewCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        LOG.info("onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        LOG.info("onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        LOG.info("onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        LOG.info("onStop: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOG.info("onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LOG.info("onDestroyView: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LOG.info("onDetach: ");
    }
}
