package com.ionvaranita.belotenote.business;

import android.content.Context;

import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.constanti.Turnul4GiocatoriInSquadraEnum;
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
import com.ionvaranita.belotenote.utils.IntegerUtils;

import java.util.HashMap;
import java.util.Map;

import static com.ionvaranita.belotenote.constanti.ConstantiGlobal.CONTINUA;
import static com.ionvaranita.belotenote.constanti.ConstantiGlobal.CONTINUA_CON_AGGIUNTA_PUNTI;


public class BusinessInserimento4GiocatoriInSquadra {
    private InfoCineACistigat InfoCineACistigat;
    private Integer idGioco;
    private Integer idPartida;
    private Context context;
    private AppDatabase db;
    Punti4GiocatoriInSquadraEntityBean lastBean;

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
        infoWinnerPoints.setIdPartida(idPartida);
        infoWinnerPoints.setIdGioco(idGioco);

        inserisciOAggiornaWinnerPoints(infoWinnerPoints);
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
        infoWinnerPoints.setIdPartida(lastBean.getIdPartida());
        infoWinnerPoints.setWinnerPoints(infoNuovaPartida4GiocatoriInSquadra.getWinnerPoints());

        inserisciOAggiornaWinnerPoints(infoWinnerPoints);
    }

    private void inserisciOAggiornaWinnerPoints(InfoWinnerPoints infoWinnerPoints){
        idGioco = infoWinnerPoints.getIdGioco();
        idPartida = infoWinnerPoints.getIdPartida();

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

        bean.setTurno(lastBean.getTurno() + 1);
        bean.setIdPartida(lastBean.getIdPartida());

        db.tabellaPunti4GiocatoriInSquadraDao().inserisciPunti4GiocatoriInSquadra(bean);

        Map<Integer, Integer> mappaIdCampoValore = new HashMap<>();

        mappaIdCampoValore.put(IdsCampiStampa.ID_PUNTI_NOI_STAMPA, puntiNoiAggiornato);
        mappaIdCampoValore.put(IdsCampiStampa.ID_PUNTI_VOI_STAMPA, puntiVoiAggiornato);

        PuncteCastigatoare4JucatoriInEchipaBean puncteCastigatoare4JucatoriInEchipaBean = db.puncteCastigatoare4JucatoriInEchipaDao().selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndIdPartida(idGioco, lastBean.getIdPartida());

        InfoCineACistigat =getCineACistigat(puncteCastigatoare4JucatoriInEchipaBean.getPuncteCastigatoare(),mappaIdCampoValore);
        if(!InfoCineACistigat.cineACistigat().equals(CONTINUA)&&!InfoCineACistigat.cineACistigat().equals(CONTINUA_CON_AGGIUNTA_PUNTI)){
            InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra = new InfoRigaVuota4GiocatoriInSquadra();
            infoRigaVuota4GiocatoriInSquadra.setIdPartida(idPartida);
            infoRigaVuota4GiocatoriInSquadra.setIdGioco(idGioco);
            infoRigaVuota4GiocatoriInSquadra.setWinnerPoints(puncteCastigatoare4JucatoriInEchipaBean.getPuncteCastigatoare());
            inserisciRigaVuota(infoRigaVuota4GiocatoriInSquadra);
        }
    }

    public void inserisciRigaVuota(InfoRigaVuota4GiocatoriInSquadra infoRigaVuota4GiocatoriInSquadra){
        db = AppDatabase.getPersistentDatabase(context);
        Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean = new Punti4GiocatoriInSquadraEntityBean();
        punti4GiocatoriInSquadraEntityBean.setIdGioco(infoRigaVuota4GiocatoriInSquadra.getIdGioco());
        punti4GiocatoriInSquadraEntityBean.setIdPartida(infoRigaVuota4GiocatoriInSquadra.getIdPartida());
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


    private InfoCineACistigat getCineACistigat(Integer puncteCastigatoare, Map<Integer,Integer> mappaIdCampoValore){
            return new InfoCineACistigat(context,mappaIdCampoValore,puncteCastigatoare);


    }
    public Integer getPuncteCastigatoare4GiocatoriInSquadra(){
        return db.puncteCastigatoare4JucatoriInEchipaDao().selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndIdPartida(idGioco,idPartida).getPuncteCastigatoare();
    }

    public InfoCineACistigat getInfoCineACistigat() {
        return InfoCineACistigat;
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
}
