package com.ionvaranita.belotenote.business;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;

import com.ionvaranita.belotenote.TabellaPunti;
import com.ionvaranita.belotenote.constanti.ActionCode;
import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.constanti.StatusGioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
import com.ionvaranita.belotenote.custom.integers.IntegerBolt;
import com.ionvaranita.belotenote.dao.BoltDao;
import com.ionvaranita.belotenote.dao.Joc4JucatoriInEchipaDao;
import com.ionvaranita.belotenote.dao.Scor4JucatoriInEchipaDao;
import com.ionvaranita.belotenote.dao.TurnManagement4GiocatoriInSquadraDao;
import com.ionvaranita.belotenote.dao.VisualizzazioneBoltDao;
import com.ionvaranita.belotenote.database.AppDatabase;
import com.ionvaranita.belotenote.entity.BoltEntityBean;
import com.ionvaranita.belotenote.entity.GiochiUltimaPartidaBean;
import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;
import com.ionvaranita.belotenote.entity.PuncteCastigatoare4JucatoriInEchipaBean;
import com.ionvaranita.belotenote.entity.PuncteCastigatoareGlobalBean;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.Scor4JucatoriInEchipaEntityBean;
import com.ionvaranita.belotenote.entity.TurnManagement4GiocatoriInSquadra;
import com.ionvaranita.belotenote.entity.VincitoreBean;
import com.ionvaranita.belotenote.entity.VisualizzazioneBoltEntityBean;
import com.ionvaranita.belotenote.entity.WhoPlayEntityBean;
import com.ionvaranita.belotenote.info.InfoBolt;
import com.ionvaranita.belotenote.info.InfoCineACistigat;
import com.ionvaranita.belotenote.info.InfoGiocoNuovo4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoNuovaPartida4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoRigaVuota4GiocatoriInSquadra;
import com.ionvaranita.belotenote.info.InfoWinnerPoints;
import com.ionvaranita.belotenote.popup.ParametersPuncteCastigatoarePopup;
import com.ionvaranita.belotenote.popup.PopupPuncteCastigatoare;
import com.ionvaranita.belotenote.popup.PopupCineACistigat;
import com.ionvaranita.belotenote.utils.IntegerUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class BusinessInserimento4GiocatoriInSquadra {
    private InfoCineACistigat infoCineACistigat;
    private Integer idGioco;
    private Integer idPartida;
    private Context context;
    private AppDatabase db;
    private Punti4GiocatoriInSquadraEntityBean lastBean;
    private Integer statusGioco;

    private BusinessInserimento4GiocatoriInSquadra(Context context) {
        this.context = context;
        db = AppDatabase.getPersistentDatabase(context);


    }

    public BusinessInserimento4GiocatoriInSquadra(Context context,Integer idGioco){
        this(context);
        if(idGioco!=null){
            this.idGioco = idGioco;

            GiochiUltimaPartidaBean giochiUltimaPartidaBean = db.giochiUltimaPartidDao().getUltimaPartidaByIdGioco(idGioco);
            this.idPartida = giochiUltimaPartidaBean.getIdPartida();

            Gioco4GiocatoriInSquadra gioco4GiocatoriInSquadra = db.joc4JucatoriInEchipaDao().selectJocByIdJoc(idGioco);
            this.statusGioco = gioco4GiocatoriInSquadra.getStatus();

            lastBean = db.tabellaPunti4GiocatoriInSquadraDao().getLastRecord4GiocatoriInSquadraByIdGiocoIdPartida(idGioco,idPartida);

            if(lastBean==null){
                lastBean=new Punti4GiocatoriInSquadraEntityBean();
            }
        }



    }

    public void inserisciOAggiornaWhoPlayed(WhoPlayEntityBean whoPlayEntityBean) {
        db.whoPlayedDao().insertOrUpdateWhoPlayed(whoPlayEntityBean);
    }

    public void inserisciBoltEdAggiornaNrBolt(InfoBolt infoBolt) {
        BoltDao boltDao = db.boltDao();
        VisualizzazioneBoltDao visualizzazioneBoltDao = db.visualizzazioneBoltDao();

        VisualizzazioneBoltEntityBean visualizzazioneBoltEntityBean = new VisualizzazioneBoltEntityBean();
        visualizzazioneBoltEntityBean.setIdGioco(infoBolt.getIdGioco());
        visualizzazioneBoltEntityBean.setIdPersona(infoBolt.getIdPersona());
        visualizzazioneBoltEntityBean.setIdRiga(infoBolt.getIdRiga());

        visualizzazioneBoltDao.insertBolt(visualizzazioneBoltEntityBean);

        BoltEntityBean boltEntityBean = new BoltEntityBean();
        boltEntityBean.setIdGioco(infoBolt.getIdGioco());
        boltEntityBean.setIdPartida(infoBolt.getIdPartida());
        boltEntityBean.setIdPersona(infoBolt.getIdPersona());
        IntegerBolt nrBoltAggiornato = new IntegerBolt(boltDao.getNrBoltByIdGiocoIdPartidaIdPersona(idGioco,idPartida,infoBolt.getIdPersona()));
        if(infoBolt.getNrBolt()!=null){
            nrBoltAggiornato.setValore(nrBoltAggiornato.getValore()+infoBolt.getNrBolt());

        }
        boltEntityBean.setNrBolt(nrBoltAggiornato.getValore());

        boltDao.insertOrUpdateBoltByIdGiocoIdPartidaIdPersona(boltEntityBean);
    }

    private void inserisciOggiornaGiocoPartida(Boolean nuovoGioco){
        if(nuovoGioco){
            Gioco4GiocatoriInSquadra giocoInseritoBean = db.joc4JucatoriInEchipaDao().selectLastJocInserit4JucatoriInEchipa();
            idGioco = giocoInseritoBean.getIdGioco();
            idPartida = idGioco + ConstantiGlobal.INTERVALLO_GIOCO_PARTIDA;
        }
        else if(!nuovoGioco){
            idPartida = idPartida + ConstantiGlobal.INTERVALLO_GIOCO_PARTIDA;
        }

        GiochiUltimaPartidaBean giochiUltimaPartidaBean = new GiochiUltimaPartidaBean();
        giochiUltimaPartidaBean.setIdGioco(idGioco);
        giochiUltimaPartidaBean.setIdPartida(idPartida);
        db.giochiUltimaPartidDao().inserisciOrUpdateIdPartidaByIdGioco(giochiUltimaPartidaBean);

    }

    public void inserisciPrimaVoltaNelDatabase(InfoGiocoNuovo4GiocatoriInSquadra infoGiocoNuovo4GiocatoriInSquadra) {
        Gioco4GiocatoriInSquadra giocoBean = new Gioco4GiocatoriInSquadra();

        giocoBean.setNumeGioco(infoGiocoNuovo4GiocatoriInSquadra.getNomeGioco());
        db.joc4JucatoriInEchipaDao().insertJoc4JucatoriInEchipa(giocoBean);

        inserisciOggiornaGiocoPartida(true);



        Scor4JucatoriInEchipaEntityBean scorBean = new Scor4JucatoriInEchipaEntityBean();
        scorBean.setIdGioco(idGioco);
        scorBean.setScorNoi(0);
        scorBean.setScorVoi(0);
        db.scor4JucatoriInEchipaDao().insertScor4JucatoriInEchipa(scorBean);

        InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra = new InfoRigaVuota4GiocatoriInSquadra();
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

        setStatusAndNrPartideGioco4GiocatoriInSquadra(statusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA, 0);
    }

    public void inserisciNuovaPartidaNeDatabase(InfoNuovaPartida4GiocatoriInSquadra infoNuovaPartida4GiocatoriInSquadra) {
        idGioco = infoNuovaPartida4GiocatoriInSquadra.getIdGioco();

        inserisciOggiornaGiocoPartida(false);



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
        infoWinnerPoints.setIdGioco(idGioco);
        infoWinnerPoints.setWinnerPoints(infoNuovaPartida4GiocatoriInSquadra.getWinnerPoints());

        inserisciOAggiornaWinnerPoints(infoWinnerPoints);

        updateStatusAndDeltaNrPartideGioco4GiocatoriInSquadra(StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA, 1);
    }

    private void inserisciOAggiornaWinnerPoints(InfoWinnerPoints infoWinnerPoints) {
        PuncteCastigatoareGlobalBean pncteCastigatoareGlobal = new PuncteCastigatoareGlobalBean();
        pncteCastigatoareGlobal.setPuncteCastigatoare(infoWinnerPoints.getWinnerPoints());
        db.puncteCastigatoareGlobalDao().insertPuncteCastigatoareGlobal(pncteCastigatoareGlobal);

        PuncteCastigatoare4JucatoriInEchipaBean puncteCastigatoare = new PuncteCastigatoare4JucatoriInEchipaBean();
        puncteCastigatoare.setIdGioco(idGioco);
        puncteCastigatoare.setIdPartida(idPartida);
        puncteCastigatoare.setPuncteCastigatoare(infoWinnerPoints.getWinnerPoints());
        db.puncteCastigatoare4JucatoriInEchipaDao().insertPuncteCastigatoare4JucatoriInEchipa(puncteCastigatoare);


    }

    public void inserisciBeanNelDatabase(Punti4GiocatoriInSquadraEntityBean bean, Integer idPersona) {
        Integer lastPuntiNoi = IntegerUtils.integerFixAndBoltFix(lastBean.getPuntiNoi());

        Integer lastPuntiVoi = IntegerUtils.integerFixAndBoltFix(lastBean.getPuntiVoi());

        bean.setIdGioco(idGioco);

        int puntiNoiAggiornato = lastPuntiNoi + bean.getPuntiNoi();
        int puntiVoiAggiornato = lastPuntiVoi + bean.getPuntiVoi();

        bean.setPuntiNoi(puntiNoiAggiornato);
        bean.setPuntiVoi(puntiVoiAggiornato);

        inserisciNuovoTurno();


        Integer turnoAggiornato = IntegerUtils.integerFixAndBoltFix(lastBean.getTurno()) + 1;


        bean.setTurno(turnoAggiornato);
        bean.setIdPartida(idPartida);
        db.tabellaPunti4GiocatoriInSquadraDao().inserisciPunti4GiocatoriInSquadra(bean);

        if (idPersona != null&&lastBean!=null) {
            Integer idRiga = lastBean.getId();
            InfoBolt infoBolt = new InfoBolt();
            infoBolt.setIdGioco(idGioco);
            infoBolt.setIdPartida(idPartida);
            infoBolt.setIdPersona(idPersona);
            infoBolt.setIdRiga(idRiga);
            infoBolt.setNrBolt(1);
            this.inserisciBoltEdAggiornaNrBolt(infoBolt);


        }

        Map<Integer, Integer> mappaIdCampoValore = new HashMap<>();

        mappaIdCampoValore.put(IdsCampiStampa.ID_NOI, puntiNoiAggiornato);
        mappaIdCampoValore.put(IdsCampiStampa.ID_VOI, puntiVoiAggiornato);

        PuncteCastigatoare4JucatoriInEchipaBean puncteCastigatoare4JucatoriInEchipaBean = db.puncteCastigatoare4JucatoriInEchipaDao().selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndIdPartida(idGioco, idPartida);

        infoCineACistigat = getCineACistigat(puncteCastigatoare4JucatoriInEchipaBean.getPuncteCastigatoare(), mappaIdCampoValore, false);

        final Integer cineACistigat = infoCineACistigat.aflaCineACistigat();

        boolean qualcunoHaVinto = !cineACistigat.equals(StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA_PROLUNGATA) && (!cineACistigat.equals(StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA)) && !cineACistigat.equals(StatusGioco4GiocatoriInSquadra.CODICE_OBBLIGATO_CONTINUA_CON_AGGIUNTA_PUNTI);

        boolean isContinuaConAggiuntaPunti = cineACistigat.equals(StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA_PROLUNGATA);


        final ParametersPuncteCastigatoarePopup parametersPuncteCastigatoarePopupLocal = new ParametersPuncteCastigatoarePopup();
        parametersPuncteCastigatoarePopupLocal.setActioCode(ActionCode.GIOCATORI_4_IN_SQUADRA);
        parametersPuncteCastigatoarePopupLocal.setContext(context);
        parametersPuncteCastigatoarePopupLocal.setIdGioco(idGioco);
        parametersPuncteCastigatoarePopupLocal.setIdPartida(idPartida);
        parametersPuncteCastigatoarePopupLocal.setInfoCineACistigat(infoCineACistigat);

        InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra = new InfoRigaVuota4GiocatoriInSquadra();
        infoRigaVuota4GiocatoriInSquadra.setIdPartida(idPartida);
        infoRigaVuota4GiocatoriInSquadra.setIdGioco(idGioco);
        infoRigaVuota4GiocatoriInSquadra.setWinnerPoints(puncteCastigatoare4JucatoriInEchipaBean.getPuncteCastigatoare());
        infoRigaVuota4GiocatoriInSquadra.setInfoCineACistigat(infoCineACistigat);
        if (qualcunoHaVinto) {
            parametersPuncteCastigatoarePopupLocal.setNuovaPartida(true);
            finisciPartida(infoRigaVuota4GiocatoriInSquadra);
            PopupCineACistigat popupCineACistigat = new PopupCineACistigat(parametersPuncteCastigatoarePopupLocal, bean);
            popupCineACistigat.showPopup();


        } else if (isContinuaConAggiuntaPunti) {

            parametersPuncteCastigatoarePopupLocal.setPartidaProlungata(true);
            PopupPuncteCastigatoare popupPuncteCastigatoare = new PopupPuncteCastigatoare(parametersPuncteCastigatoarePopupLocal);
            infoCineACistigat = getCineACistigat(puncteCastigatoare4JucatoriInEchipaBean.getPuncteCastigatoare(), mappaIdCampoValore, true);

            boolean seiObbligatoDiProlungareLaPartida = cineACistigat.equals(StatusGioco4GiocatoriInSquadra.CODICE_OBBLIGATO_CONTINUA_CON_AGGIUNTA_PUNTI);
            if (seiObbligatoDiProlungareLaPartida) {
                parametersPuncteCastigatoarePopupLocal.setObligatoProlungare(true);
                parametersPuncteCastigatoarePopupLocal.setInfoCineACistigat(infoCineACistigat);
                PopupPuncteCastigatoare popupPuncteCastigatoare1 = new PopupPuncteCastigatoare(parametersPuncteCastigatoarePopupLocal);
                popupPuncteCastigatoare1.getCancelButtonPopup().setVisibility(View.INVISIBLE);
                popupPuncteCastigatoare1.showPopup();

            } else {
                Gioco4GiocatoriInSquadra gioco4GiocatoriInSquadra = db.joc4JucatoriInEchipaDao().selectJocByIdJoc(idGioco);

                gioco4GiocatoriInSquadra.setDataGioco(new Date().getTime());
                gioco4GiocatoriInSquadra.setStatus(StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA_PROLUNGATA);
                db.joc4JucatoriInEchipaDao().updateGioco4GiuocatoriInSquadra(gioco4GiocatoriInSquadra);

                popupPuncteCastigatoare.getCancelButtonPopup().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parametersPuncteCastigatoarePopupLocal.setNuovaPartida(true);
                        parametersPuncteCastigatoarePopupLocal.setInfoCineACistigat(infoCineACistigat);
                        PopupCineACistigat popupCineACistigat = new PopupCineACistigat(parametersPuncteCastigatoarePopupLocal, bean);
                        finisciPartida(infoRigaVuota4GiocatoriInSquadra);
                        popupCineACistigat.showPopup();

                    }
                });
                popupPuncteCastigatoare.showPopup();
            }

        } else {
            vaiNellaTabellaPunti();
        }

    }

    public void finisciPartida(InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra) {
        idGioco = infoRigaVuota4GiocatoriInSquadra.getIdGioco();
        if (infoCineACistigat == null) {
            infoCineACistigat = infoRigaVuota4GiocatoriInSquadra.getInfoCineACistigat();
        }
        //inserisciRigaVuota(infoRigaVuota4GiocatoriInSquadra);
        updateStatusAndDeltaNrPartideGioco4GiocatoriInSquadra(StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_FINITA, 1);
        aggiornaScor4GiocatoriInSquadra(infoCineACistigat.aflaCineACistigat());


    }

    private void inserisciNuovoTurno() {
        TurnManagement4GiocatoriInSquadraDao turnManagement4GiocatoriInSquadraDao = db.turnManagement4GiocatoriInSquadraDao();
        TurnManagement4GiocatoriInSquadra turnManagement4GiocatoriInSquadra = turnManagement4GiocatoriInSquadraDao.selectTurn4JucatoriInEchipaByIdJoc(idGioco);
        String turnoDelDatabase = turnManagement4GiocatoriInSquadra.getTurnoPresente();
        if (turnoDelDatabase.equalsIgnoreCase(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.getDescrizione())) {
            turnManagement4GiocatoriInSquadra.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_VOI.getDescrizione());
        } else if (turnoDelDatabase.equalsIgnoreCase(Turnul4GiocatoriInSquadraEnum.TURNUL_VOI.getDescrizione())) {
            turnManagement4GiocatoriInSquadra.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.getDescrizione());

        }
        turnManagement4GiocatoriInSquadraDao.insertTurnManagement4JucatoriInEchipa(turnManagement4GiocatoriInSquadra);
    }


    private void aggiornaScor4GiocatoriInSquadra(Integer vincitore) {
        Scor4JucatoriInEchipaDao scor4JucatoriInEchipaDao = db.scor4JucatoriInEchipaDao();
        Scor4JucatoriInEchipaEntityBean scor4JucatoriInEchipaEntityBean = scor4JucatoriInEchipaDao.selectScorBean4JucatoriInEchipaByIdJoc(idGioco);

        VincitoreBean vincitoreBean = new VincitoreBean();

        vincitoreBean.setIdGioco(idGioco);
        vincitoreBean.setIdPartida(idPartida);
        vincitoreBean.setVincitore(vincitore);

        db.vincitoreDao().inserisciVincitore(vincitoreBean);

        if (vincitore.equals(IdsCampiStampa.ID_NOI)) {
            scor4JucatoriInEchipaEntityBean.setScorNoi(scor4JucatoriInEchipaEntityBean.getScorNoi() + 1);
        } else if (vincitore.equals(IdsCampiStampa.ID_VOI)) {
            scor4JucatoriInEchipaEntityBean.setScorVoi(scor4JucatoriInEchipaEntityBean.getScorVoi() + 1);
        }
        scor4JucatoriInEchipaDao.insertScor4JucatoriInEchipa(scor4JucatoriInEchipaEntityBean);

    }

    public void inserisciRigaVuota(InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra) {
        db = AppDatabase.getPersistentDatabase(context);
        Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean = new Punti4GiocatoriInSquadraEntityBean();
        punti4GiocatoriInSquadraEntityBean.setIdGioco(infoRigaVuota4GiocatoriInSquadra.getIdGioco());
        punti4GiocatoriInSquadraEntityBean.setIdPartida(infoRigaVuota4GiocatoriInSquadra.getIdPartida());

        if (lastBean != null) {
            punti4GiocatoriInSquadraEntityBean.setTurno(lastBean.getTurno() + 1);
        } else {
            punti4GiocatoriInSquadraEntityBean.setTurno(0);
        }

        db.tabellaPunti4GiocatoriInSquadraDao().inserisciPunti4GiocatoriInSquadra(punti4GiocatoriInSquadraEntityBean);

        TurnManagement4GiocatoriInSquadra turnBean = new TurnManagement4GiocatoriInSquadra();
        turnBean.setIdGioco(idGioco);
        turnBean.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.toString());
        db.turnManagement4GiocatoriInSquadraDao().insertTurnManagement4JucatoriInEchipa(turnBean);
    }


    private InfoCineACistigat getCineACistigat(Integer puncteCastigatoare, Map<Integer, Integer> mappaIdCampoValore, boolean unSoloVincitorie) {
        return new InfoCineACistigat(context, mappaIdCampoValore, puncteCastigatoare, unSoloVincitorie);


    }

    public Integer getPuncteCastigatoare4GiocatoriInSquadra() {
        return db.puncteCastigatoare4JucatoriInEchipaDao().selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndIdPartida(idGioco, idPartida).getPuncteCastigatoare();
    }

    public Integer getIdGioco() {
        return idGioco;
    }


    public Integer getIdPartida() {
        return idPartida;
    }


    private void vaiNellaTabellaPunti() {
        Intent intent = new Intent(context, TabellaPunti.class);
        intent.putExtra(Turnul4GiocatoriInSquadraEnum.ID_JOC.getDescrizione(), idGioco);
        intent.putExtra(ConstantiGlobal.ACTION_CODE, ActionCode.GIOCATORI_4_IN_SQUADRA);
        context.startActivity(intent);
    }

    private void updateStatusAndDeltaNrPartideGioco4GiocatoriInSquadra(Integer status, Integer deltaPartida) {
        Joc4JucatoriInEchipaDao joc4JucatoriInEchipaDao = db.joc4JucatoriInEchipaDao();

        Gioco4GiocatoriInSquadra gioco4GiocatoriInSquadra = joc4JucatoriInEchipaDao.selectJocByIdJoc(idGioco);

        gioco4GiocatoriInSquadra.setStatus(status);

        gioco4GiocatoriInSquadra.setNrPartide(gioco4GiocatoriInSquadra.getNrPartide() + deltaPartida);

        joc4JucatoriInEchipaDao.updateGioco4GiuocatoriInSquadra(gioco4GiocatoriInSquadra);
    }

    private void setStatusAndNrPartideGioco4GiocatoriInSquadra(Integer status, Integer nrPartide) {
        Joc4JucatoriInEchipaDao joc4JucatoriInEchipaDao = db.joc4JucatoriInEchipaDao();

        Gioco4GiocatoriInSquadra gioco4GiocatoriInSquadra = joc4JucatoriInEchipaDao.selectJocByIdJoc(idGioco);

        gioco4GiocatoriInSquadra.setStatus(status);

        gioco4GiocatoriInSquadra.setNrPartide(nrPartide);

        joc4JucatoriInEchipaDao.updateGioco4GiuocatoriInSquadra(gioco4GiocatoriInSquadra);
    }


    public void inserisciAdausAllaPartida(InfoWinnerPoints infoWinnerPoints) {
        idGioco = infoWinnerPoints.getIdGioco();
        inserisciOAggiornaWinnerPoints(infoWinnerPoints);
        updateStatusAndDeltaNrPartideGioco4GiocatoriInSquadra(StatusGioco4GiocatoriInSquadra.CODICE_PARTIDA_NON_FINITA_PROLUNGATA, 0);
        vaiNellaTabellaPunti();
    }
}
