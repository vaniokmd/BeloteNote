package com.ionvaranita.belotenote;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TableRow;

import com.ionvaranita.belotenote.adapters.AdapterPuncteCastigatoareGlobal;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private EditText puncteCastigatoareGlobalInserimanto;
    private View popupViewPuncteCastigatoare;
    private RecyclerView recyclerViewPuncteCastigatoareGlobal;
    private AdapterPuncteCastigatoareGlobal adapterPuncteCastigatoareGlobal;
    private Switch activeFieldPuncteCastigatoare;
    private LayoutInflater layoutInflater;
    private Integer idPartida;
    private Integer idGioco;
    private TableRow nomeGiocoFooterTableRow;
    private BorderedEditText puncteCastigatoareInserimento;

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

        nomeGiocoFooterTableRow = popupViewPuncteCastigatoare.findViewById(R.id.nome_gioco_global_table_row);

        if (isNomeGiocoMostrabile) {
            nomeGiocoFooterTableRow.setVisibility(View.VISIBLE);

        } else if (!isNomeGiocoMostrabile) {
            nomeGiocoFooterTableRow.setVisibility(View.INVISIBLE);
        }

        puncteCastigatoareInserimento = popupViewPuncteCastigatoare.findViewById(R.id.puncte_castigatoare_global_inserimento);

        itemsMenuRecyclerView = (RecyclerView) mainWindow.findViewById(R.id.lista_jocuri_recycleview);


        db = AppDatabase.getPersistentDatabase(contesto);

        popolaRecyclerViewPuncteCastigatoareGlobal();

        setCancelAndOkButton();

        setMostraONascondiInputPuncteCastigatoare();


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
        recyclerViewPuncteCastigatoareGlobal = popupViewPuncteCastigatoare.findViewById(R.id.recycler_view_puncte_castigatoare_global);

        puncteCastigatoareGlobalList = db.puncteCastigatoareGlobalDao().selectAllPuncteCastigatoareGlobalOrderByData();

        adapterPuncteCastigatoareGlobal = new AdapterPuncteCastigatoareGlobal(contesto, puncteCastigatoareGlobalList);

        recyclerViewPuncteCastigatoareGlobal.setAdapter(adapterPuncteCastigatoareGlobal);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(contesto);
        recyclerViewPuncteCastigatoareGlobal.setLayoutManager(linearLayoutManager);

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
        activeFieldPuncteCastigatoare = popupViewPuncteCastigatoare.findViewById(R.id.toggle_button_active_puncte_castigatoare_global);

        activeFieldPuncteCastigatoare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    puncteCastigatoareGlobalInserimanto.setVisibility(View.VISIBLE);
                    puncteCastigatoareGlobalInserimanto.requestFocus();
                    if (adapterPuncteCastigatoareGlobal.getPuncteCastigatoareChecked() != null)
                        adapterPuncteCastigatoareGlobal.getPuncteCastigatoareChecked().setChecked(false);
                    // The toggle is enabled
                } else {
                    puncteCastigatoareGlobalInserimanto.setVisibility(View.INVISIBLE);
                    InputMethodManager imm = (InputMethodManager) contesto.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(buttonView.getWindowToken(), 0);
                    recyclerViewPuncteCastigatoareGlobal.requestFocus();
                }
            }
        });

        Set<RadioButton> multimeaRadioButton = adapterPuncteCastigatoareGlobal.getMultimeaRadioButton();


        Iterator iterator = multimeaRadioButton.iterator();
        while (iterator.hasNext()) {
            ((RadioButton) iterator.next()).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activeFieldPuncteCastigatoare.setChecked(false);
                }
            });
        }


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
            }
            vaiNellaTabellaPunti();

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
        } else if (adapterPuncteCastigatoareGlobal.getPuncteCastigatoareChecked() != null && adapterPuncteCastigatoareGlobal.getPuncteCastigatoareChecked().isChecked()) {
            puncteCastigatoare = Integer.parseInt(adapterPuncteCastigatoareGlobal.getPuncteCastigatoareChecked().getText().toString());
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

    private static RadioButton cercaRadioButtonByPuncteCastigatoare(Set<RadioButton> puncteCastigatoareGlobalBeansRadioButton, Integer valoreDaCercare) {
        for (RadioButton radioButton :
                puncteCastigatoareGlobalBeansRadioButton) {
            if (Integer.parseInt(radioButton.getText().toString()) == valoreDaCercare) {
                return radioButton;
            }

        }
        return null;
    }

}
