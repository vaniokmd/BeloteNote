package com.ionvaranita.belotenote.business;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.ionvaranita.belotenote.R;
import com.ionvaranita.belotenote.TabellaPunti;
import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.constanti.StatusGioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.dao.Joc4JucatoriInEchipaDao;
import com.ionvaranita.belotenote.dao.Scor4JucatoriInEchipaDao;
import com.ionvaranita.belotenote.dao.TurnManagement4GiocatoriInSquadraDao;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.entity.PuncteCastigatoare4JucatoriInEchipaBean;
import com.ionvaranita.belotenote.entity.PuncteCastigatoareGlobalBean;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.Scor4JucatoriInEchipaEntityBean;
import com.ionvaranita.belotenote.entity.TurnManagement4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoCineACistigat;
import com.ionvaranita.belotenote.info.InfoGiocoNuovo4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoNuovaPartida4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoRigaVuota4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoWinnerPoints;
import com.ionvaranita.belotenote.popup.ParametersPuncteCastigatoarePopup;
import com.ionvaranita.belotenote.popup.PopupPuncteCastigatoare;
import com.ionvaranita.belotenote.popup.PopupVreiSaContinuiCuOPartidaNoua;
import com.ionvaranita.belotenote.utils.IntegerUtils;

import java.util.HashMap;
import java.util.Map;

import static com.ionvaranita.belotenote.constanti.ConstantiGlobal.CONTINUA;


public class BusinessInserimento4GiocatoriInSquadra {
    private InfoCineACistigat infoCineACistigat;
    private Integer idGioco;
    private Integer idPartida;
    private Context context;
    private AppDatabase db;
    private Punti4GiocatoriInSquadraEntityBean lastBean;

    public BusinessInserimento4GiocatoriInSquadra(Context context) {
        this.context=context;
        db = AppDatabase.getPersistentDatabase(context);

    }

    public void inserisciPrimaVoltaNelDatabase(InfoGiocoNuovo4GiocatoriInSquadra infoGiocoNuovo4GiocatoriInSquadra) {
        Gioco4GiocatoriInSquadra giocoBean = new Gioco4GiocatoriInSquadra();

        giocoBean.setNumeGioco(infoGiocoNuovo4GiocatoriInSquadra.getNomeGioco());
        db.joc4JucatoriInEchipaDao().insertJoc4JucatoriInEchipa(giocoBean);

        Gioco4GiocatoriInSquadra giocoInseritoBean = db.joc4JucatoriInEchipaDao().selectLastJocInserit4JucatoriInEchipa();

        idGioco = giocoInseritoBean.getIdGioco();

        Integer idPartida = idGioco + ConstantiGlobal.INTERVALLO_GIOCO_PARTIDA;

        Scor4JucatoriInEchipaEntityBean scorBean = new Scor4JucatoriInEchipaEntityBean();
        scorBean.setIdGioco(idGioco);
        scorBean.setScorNoi(0);
        scorBean.setScorVoi(0);
        db.scor4JucatoriInEchipaDao().insertScor4JucatoriInEchipa(scorBean);

        InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra  = new InfoRigaVuota4GiocatoriInSquadra();
        infoRigaVuota4GiocatoriInSquadra.setIdGioco(idGioco);
        infoRigaVuota4GiocatoriInSquadra.setIdPartida(idPartida);
        infoRigaVuota4GiocatoriInSquadra.setWinnerPoints(infoGiocoNuovo4GiocatoriInSquadra.getPuncteCastigatoare());

        inserisciRigaVuota(infoRigaVuota4GiocatoriInSquadra);

        InfoWinnerPoints infoWinnerPoints = new InfoWinnerPoints();

        infoWinnerPoints.setWinnerPoints(infoGiocoNuovo4GiocatoriInSquadra.getPuncteCastigatoare());
        infoWinnerPoints.setIdGioco(idGioco);

        inserisciOAggiornaWinnerPoints(infoWinnerPoints);

        TurnManagement4GiocatoriInSquadra turnManagement4GiocatoriInSquadra = new TurnManagement4GiocatoriInSquadra();

        turnManagement4GiocatoriInSquadra.setIdGioco(idGioco);
        turnManagement4GiocatoriInSquadra.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.getDescrizione());
        db.turnManagement4GiocatoriInSquadraDao().insertTurnManagement4JucatoriInEchipa(turnManagement4GiocatoriInSquadra);
        StatusGioco4GiocatoriInSquadra statusGioco4GiocatoriInSquadra = new StatusGioco4GiocatoriInSquadra(context);
        setStatusAndNrPartideGioco4GiocatoriInSquadra(statusGioco4GiocatoriInSquadra.getPartidaNonFinita(),0);
    }
    public void inserisciNuovaPartidaNeDatabase(InfoNuovaPartida4GiocatoriInSquadra infoNuovaPartida4GiocatoriInSquadra){
        idGioco = infoNuovaPartida4GiocatoriInSquadra.getIdGioco();
        lastBean = db.tabellaPunti4GiocatoriInSquadraDao().getLastRecordPunti4GiocatoriInSquadraByIdGioco(idGioco);

        idPartida = lastBean.getIdPartida()+ConstantiGlobal.INTERVALLO_GIOCO_PARTIDA;

        PuncteCastigatoare4JucatoriInEchipaBean puncteCastigatoare = new PuncteCastigatoare4JucatoriInEchipaBean();
        puncteCastigatoare.setIdGioco(idGioco);
        puncteCastigatoare.setIdPartida(idPartida);
        puncteCastigatoare.setPuncteCastigatoare(infoNuovaPartida4GiocatoriInSquadra.getWinnerPoints());
        db.puncteCastigatoare4JucatoriInEchipaDao().insertPuncteCastigatoare4JucatoriInEchipa(puncteCastigatoare);

        TurnManagement4GiocatoriInSquadra turnBean = new TurnManagement4GiocatoriInSquadra();
        turnBean.setIdGioco(idGioco);
        turnBean.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.toString());
        db.turnManagement4GiocatoriInSquadraDao().insertTurnManagement4JucatoriInEchipa(turnBean);

        InfoWinnerPoints infoWinnerPoints = new InfoWinnerPoints();
        infoWinnerPoints.setIdGioco(lastBean.getIdGioco());
        infoWinnerPoints.setWinnerPoints(infoNuovaPartida4GiocatoriInSquadra.getWinnerPoints());

        inserisciOAggiornaWinnerPoints(infoWinnerPoints);

        StatusGioco4GiocatoriInSquadra statusGioco4GiocatoriInSquadra = new StatusGioco4GiocatoriInSquadra(context);

        updateStatusAndDeltaNrPartideGioco4GiocatoriInSquadra(statusGioco4GiocatoriInSquadra.getPartidaNonFinita(),1);
    }

    private void inserisciOAggiornaWinnerPoints(InfoWinnerPoints infoWinnerPoints){
        idGioco = infoWinnerPoints.getIdGioco();
        lastBean = db.tabellaPunti4GiocatoriInSquadraDao().getLastRecordPunti4GiocatoriInSquadraByIdGioco(idGioco);
        idPartida = lastBean.getIdPartida();

        PuncteCastigatoareGlobalBean pncteCastigatoareGlobal = new PuncteCastigatoareGlobalBean();
        pncteCastigatoareGlobal.setPuncteCastigatoare(infoWinnerPoints.getWinnerPoints());
        db.puncteCastigatoareGlobalDao().insertPuncteCastigatoareGlobal(pncteCastigatoareGlobal);

        PuncteCastigatoare4JucatoriInEchipaBean puncteCastigatoare = new PuncteCastigatoare4JucatoriInEchipaBean();
        puncteCastigatoare.setIdGioco(idGioco);
        puncteCastigatoare.setIdPartida(idPartida);
        puncteCastigatoare.setPuncteCastigatoare(infoWinnerPoints.getWinnerPoints());
            db.puncteCastigatoare4JucatoriInEchipaDao().insertPuncteCastigatoare4JucatoriInEchipa(puncteCastigatoare);


    }

    public void inserisciBeanNelDatabase(Punti4GiocatoriInSquadraEntityBean bean) {
        lastBean = db.tabellaPunti4GiocatoriInSquadraDao().getLastRecordPunti4GiocatoriInSquadraByIdGioco(idGioco);
        idGioco = lastBean.getIdGioco();
        idPartida = lastBean.getIdPartida();
        Integer lastPuntiNoi = IntegerUtils.integerFix(lastBean.getPuntiNoi());

        Integer lastPuntiVoi = IntegerUtils.integerFix(lastBean.getPuntiVoi());

        bean.setIdGioco(idGioco);

        int puntiNoiAggiornato = lastPuntiNoi + bean.getPuntiNoi();
        int puntiVoiAggiornato = lastPuntiVoi + bean.getPuntiVoi();

        bean.setPuntiNoi(puntiNoiAggiornato);
        bean.setPuntiVoi(puntiVoiAggiornato);

        inserisciNuovoTurno();



        Integer turnoAggiornato = lastBean.getTurno() + 1;


        bean.setTurno(turnoAggiornato);
        bean.setIdPartida(lastBean.getIdPartida());

        db.tabellaPunti4GiocatoriInSquadraDao().inserisciPunti4GiocatoriInSquadra(bean);

        Map<Integer, Integer> mappaIdCampoValore = new HashMap<>();

        mappaIdCampoValore.put(IdsCampiStampa.ID_PUNTI_NOI_STAMPA, puntiNoiAggiornato);
        mappaIdCampoValore.put(IdsCampiStampa.ID_PUNTI_VOI_STAMPA, puntiVoiAggiornato);

        PuncteCastigatoare4JucatoriInEchipaBean puncteCastigatoare4JucatoriInEchipaBean = db.puncteCastigatoare4JucatoriInEchipaDao().selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndIdPartida(idGioco, lastBean.getIdPartida());

        infoCineACistigat =getCineACistigat(puncteCastigatoare4JucatoriInEchipaBean.getPuncteCastigatoare(),mappaIdCampoValore,false);

        boolean qualcunoHaVinto = !infoCineACistigat.aflaCineACistigat().equalsIgnoreCase(ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI)&&!infoCineACistigat.aflaCineACistigat().equalsIgnoreCase(CONTINUA);

        boolean isContinuaConAggiuntaPunti = infoCineACistigat.aflaCineACistigat().equalsIgnoreCase(ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI);

        ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopup = new ParametersPuncteCastigatoarePopup();
        parametersPuncteCastigatoarePopup.setActioCode(ActionCode.GIOCATORI_4_IN_SQUADRA);
        parametersPuncteCastigatoarePopup.setContext(context);
        parametersPuncteCastigatoarePopup.setIdGioco(idGioco);
        parametersPuncteCastigatoarePopup.setIdPartida(idPartida);
        parametersPuncteCastigatoarePopup.setInfoCineACistigat(infoCineACistigat);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final StatusGioco4GiocatoriInSquadra statusGioco4GiocatoriInSquadra = new StatusGioco4GiocatoriInSquadra(context);

        final BusinessInserimento4GiocatoriInSquadra questoOggetto = this;

        if(qualcunoHaVinto){
            parametersPuncteCastigatoarePopup.setNuovaPartida(true);
            InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra = new InfoRigaVuota4GiocatoriInSquadra();
            infoRigaVuota4GiocatoriInSquadra.setIdPartida(idPartida);
            infoRigaVuota4GiocatoriInSquadra.setIdGioco(idGioco);
            infoRigaVuota4GiocatoriInSquadra.setWinnerPoints(puncteCastigatoare4JucatoriInEchipaBean.getPuncteCastigatoare());
            infoRigaVuota4GiocatoriInSquadra.setTipoFinePartida(infoCineACistigat.aflaCineACistigat());

            inserisciRigaVuota(infoRigaVuota4GiocatoriInSquadra);
            updateStatusAndDeltaNrPartideGioco4GiocatoriInSquadra(statusGioco4GiocatoriInSquadra.getPartidaFinita(),1);

            aggiornaScor4GiocatoriInSquadra(infoCineACistigat.aflaCineACistigat());
            PopupVreiSaContinuiCuOPartidaNoua popupVreiSaContinuiCuOPartidaNoua = new PopupVreiSaContinuiCuOPartidaNoua(parametersPuncteCastigatoarePopup,bean);
            popupVreiSaContinuiCuOPartidaNoua.showPopup();




        }

        else if(isContinuaConAggiuntaPunti){

            parametersPuncteCastigatoarePopup.setPartidaProlungata(true);
            lastBean.setFinePartida(ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI);
            db.tabellaPunti4GiocatoriInSquadraDao().inserisciPunti4GiocatoriInSquadra(lastBean);
            PopupPuncteCastigatoare popupPuncteCastigatoare = new PopupPuncteCastigatoare(parametersPuncteCastigatoarePopup);
            popupPuncteCastigatoare.getCancelButtonPopup().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parametersPuncteCastigatoarePopup.setNuovaPartida(true);
                    infoCineACistigat.setUnVincitore(true);
                    parametersPuncteCastigatoarePopup.setInfoCineACistigat(infoCineACistigat);
                    PopupVreiSaContinuiCuOPartidaNoua popupVreiSaContinuiCuOPartidaNoua = new PopupVreiSaContinuiCuOPartidaNoua(parametersPuncteCastigatoarePopup,bean);
                    popupVreiSaContinuiCuOPartidaNoua.showPopup();
                }
            });
            popupPuncteCastigatoare.showPopup();


        }
        else{
            vaiNellaTabellaPunti();
        }

    }

    private void inserisciNuovoTurno() {
        TurnManagement4GiocatoriInSquadraDao turnManagement4GiocatoriInSquadraDao = db.turnManagement4GiocatoriInSquadraDao();
        TurnManagement4GiocatoriInSquadra turnManagement4GiocatoriInSquadra = turnManagement4GiocatoriInSquadraDao.selectTurn4JucatoriInEchipaByIdJoc(idGioco);
        String turnoDelDatabase = turnManagement4GiocatoriInSquadra.getTurnoPresente();
        if(turnoDelDatabase.equalsIgnoreCase(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.getDescrizione())){
            turnManagement4GiocatoriInSquadra.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_VOI.getDescrizione());
        }
        else if(turnoDelDatabase.equalsIgnoreCase(Turnul4GiocatoriInSquadraEnum.TURNUL_VOI.getDescrizione())){
            turnManagement4GiocatoriInSquadra.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.getDescrizione());

        }
        turnManagement4GiocatoriInSquadraDao.insertTurnManagement4JucatoriInEchipa(turnManagement4GiocatoriInSquadra);
    }


    private void aggiornaScor4GiocatoriInSquadra(String cineACistigat){
        Scor4JucatoriInEchipaDao scor4JucatoriInEchipaDao = db.scor4JucatoriInEchipaDao();
        Scor4JucatoriInEchipaEntityBean scor4JucatoriInEchipaEntityBean =scor4JucatoriInEchipaDao.selectScorBean4JucatoriInEchipaByIdJoc(idGioco);

        if(cineACistigat.equals(com.ionvaranita.belotenote.info.InfoCineACistigat.WINNER_IS_WE)){
            scor4JucatoriInEchipaEntityBean.setScorNoi(scor4JucatoriInEchipaEntityBean.getScorNoi()+1);
        }
        else if(cineACistigat.equals(com.ionvaranita.belotenote.info.InfoCineACistigat.WINNER_IS_YOU)){
            scor4JucatoriInEchipaEntityBean.setScorVoi(scor4JucatoriInEchipaEntityBean.getScorVoi()+1);
        }
        scor4JucatoriInEchipaDao.insertScor4JucatoriInEchipa(scor4JucatoriInEchipaEntityBean);

    }

    public void inserisciRigaVuota(InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra){
        db = AppDatabase.getPersistentDatabase(context);
        Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean = new Punti4GiocatoriInSquadraEntityBean();
        punti4GiocatoriInSquadraEntityBean.setIdGioco(infoRigaVuota4GiocatoriInSquadra.getIdGioco());
        punti4GiocatoriInSquadraEntityBean.setIdPartida(infoRigaVuota4GiocatoriInSquadra.getIdPartida());
        punti4GiocatoriInSquadraEntityBean.setFinePartida(infoRigaVuota4GiocatoriInSquadra.getTipoFinePartida());
        if(lastBean!=null){
            punti4GiocatoriInSquadraEntityBean.setTurno(lastBean.getTurno()+1);
        }
        else{
            punti4GiocatoriInSquadraEntityBean.setTurno(0);
        }

        db.tabellaPunti4GiocatoriInSquadraDao().inserisciPunti4GiocatoriInSquadra(punti4GiocatoriInSquadraEntityBean);

        TurnManagement4GiocatoriInSquadra turnBean = new TurnManagement4GiocatoriInSquadra();
        turnBean.setIdGioco(idGioco);
        turnBean.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.toString());
        db.turnManagement4GiocatoriInSquadraDao().insertTurnManagement4JucatoriInEchipa(turnBean);
    }


    private InfoCineACistigat getCineACistigat(Integer puncteCastigatoare, Map<Integer,Integer> mappaIdCampoValore,boolean unSoloVincitorie){
            return new InfoCineACistigat(context,mappaIdCampoValore,puncteCastigatoare,unSoloVincitorie);


    }
    public Integer getPuncteCastigatoare4GiocatoriInSquadra(){
        return db.puncteCastigatoare4JucatoriInEchipaDao().selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndIdPartida(idGioco,idPartida).getPuncteCastigatoare();
    }

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }
    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    private void vaiNellaTabellaPunti(){
        Intent intent = new Intent(context, TabellaPunti.class);
        intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), idGioco);
        intent.putExtra(ConstantiGlobal.ACTION_CODE, ActionCode.GIOCATORI_4_IN_SQUADRA);
        context.startActivity(intent);
    }

    private void updateStatusAndDeltaNrPartideGioco4GiocatoriInSquadra(String status,Integer deltaPartida){
        Joc4JucatoriInEchipaDao joc4JucatoriInEchipaDao = db.joc4JucatoriInEchipaDao();

        Gioco4GiocatoriInSquadra gioco4GiocatoriInSquadra = joc4JucatoriInEchipaDao.selectJocByIdJoc(idGioco);

        gioco4GiocatoriInSquadra.setStatus(status);

        gioco4GiocatoriInSquadra.setNrPartide(gioco4GiocatoriInSquadra.getNrPartide()+deltaPartida);

        joc4JucatoriInEchipaDao.updateGioco4GiuocatoriInSquadra(gioco4GiocatoriInSquadra);
    }
    private void setStatusAndNrPartideGioco4GiocatoriInSquadra(String status,Integer nrPartide){
        Joc4JucatoriInEchipaDao joc4JucatoriInEchipaDao = db.joc4JucatoriInEchipaDao();

        Gioco4GiocatoriInSquadra gioco4GiocatoriInSquadra = joc4JucatoriInEchipaDao.selectJocByIdJoc(idGioco);

        gioco4GiocatoriInSquadra.setStatus(status);

        gioco4GiocatoriInSquadra.setNrPartide(nrPartide);

        joc4JucatoriInEchipaDao.updateGioco4GiuocatoriInSquadra(gioco4GiocatoriInSquadra);
    }

    public void inserisciAdausAllaPartida(InfoWinnerPoints infoWinnerPoints ) {
        idGioco = infoWinnerPoints.getIdGioco();
        inserisciOAggiornaWinnerPoints(infoWinnerPoints);
        StatusGioco4GiocatoriInSquadra statusGioco4GiocatoriInSquadra = new StatusGioco4GiocatoriInSquadra(context);
        updateStatusAndDeltaNrPartideGioco4GiocatoriInSquadra(statusGioco4GiocatoriInSquadra.getPartidaNonFinitaProlungata(),0);
        vaiNellaTabellaPunti();
    }
}
