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
import com.ionvaranita.belotenote.infogn.InfoGiocoNuovo4GiocatoriInSquadra;
import com.ionvaranita.belotenote.utils.CineACastigatUtils;
import com.ionvaranita.belotenote.utils.InfoCineACistigat;
import com.ionvaranita.belotenote.utils.IntegerUtils;

import java.util.HashMap;
import java.util.Map;


public class BusinessInserimento4GiocatoriInSquadra {
    private String cineACistigat;
    private Integer idGioco;
    private Context context;
    private AppDatabase db;

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
        Punti4GiocatoriInSquadraEntityBean puntiBean = new Punti4GiocatoriInSquadraEntityBean();

        puntiBean.setIdPartida(idPartida);
        puntiBean.setTurno(0);
        puntiBean.setIdGioco(idGioco);

        db.tabellaPunti4GiocatoriInSquadraDao().inserisciPunti4GiocatoriInSquadra(puntiBean);

        PuncteCastigatoare4JucatoriInEchipaBean puncteCastigatoare = new PuncteCastigatoare4JucatoriInEchipaBean();
        puncteCastigatoare.setIdGioco(idGioco);
        puncteCastigatoare.setIdPartida(idPartida);
        puncteCastigatoare.setPuncteCastigatoare(infoGiocoNuovo4GiocatoriInSquadra.getPuncteCastigatoare());

        db.puncteCastigatoare4JucatoriInEchipaDao().insertPuncteCastigatoare4JucatoriInEchipa(puncteCastigatoare);

        TurnManagement4GiocatoriInSquadra turnBean = new TurnManagement4GiocatoriInSquadra();
        turnBean.setIdGioco(idGioco);
        turnBean.setTurnoPresente(Turnul4GiocatoriInSquadraEnum.TURNUL_NOI.toString());

        db.turnManagement4GiocatoriInSquadraDao().insertTurnManagement4JucatoriInEchipa(turnBean);

        Scor4JucatoriInEchipaEntityBean scorBean = new Scor4JucatoriInEchipaEntityBean();
        scorBean.setIdGioco(idGioco);
        scorBean.setScorNoi(0);
        scorBean.setScorVoi(0);

        db.scor4JucatoriInEchipaDao().insertScor4JucatoriInEchipa(scorBean);

        PuncteCastigatoareGlobalBean pncteCastigatoareGlobal = new PuncteCastigatoareGlobalBean();
        pncteCastigatoareGlobal.setPuncteCastigatoare(infoGiocoNuovo4GiocatoriInSquadra.getPuncteCastigatoare());

        db.puncteCastigatoareGlobalDao().insertPuncteCastigatoareGlobal(pncteCastigatoareGlobal);
    }
    public void inserisciBeanNelDatabase(Punti4GiocatoriInSquadraEntityBean bean) {
        Punti4GiocatoriInSquadraEntityBean lastBean = db.tabellaPunti4GiocatoriInSquadraDao().getLastRecordPunti4GiocatoriInSquadraByIdGioco(idGioco);

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

        cineACistigat=cineACistigat(puncteCastigatoare4JucatoriInEchipaBean.getPuncteCastigatoare(),mappaIdCampoValore);
    }


    private String cineACistigat(Integer puncteCastigatoare, Map<Integer,Integer> mappaIdCampoValore){
            InfoCineACistigat infoCineACistigat =  new InfoCineACistigat(mappaIdCampoValore);
            CineACastigatUtils cineACastigatUtils = new CineACastigatUtils(context,puncteCastigatoare);
            return cineACastigatUtils.cineACistigat(infoCineACistigat);

    }

    public String getCineACistigat() {
        return cineACistigat;
    }

    public void setCineACistigat(String cineACistigat) {
        this.cineACistigat = cineACistigat;
    }

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

}
