package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;

import java.util.List;

/**
 * Created by ionvaranita on 20/11/17.
 */
@Dao
public interface TabellaPunti4GiocatoriInSquadraDao {
    @Query("select * from Punti4GiocatoriInSquadraEntityBean")
    List<Punti4GiocatoriInSquadraEntityBean> selectAllPuntiTabella4GiocatoriInSquadra();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void inserisciPunti4GiocatoriInSquadra(Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean);

    @Query("select * from Punti4GiocatoriInSquadraEntityBean where Punti4GiocatoriInSquadraEntityBean.idGioco = :idGioco and " +
            "idPartida=(select max(idPartida) from  Punti4GiocatoriInSquadraEntityBean where idGioco = :idGioco) and " +
            "turno = (select max(turno) from Punti4GiocatoriInSquadraEntityBean where" +
            " idPartida = (select max(idPartida) from Punti4GiocatoriInSquadraEntityBean where idGioco=:idGioco))")
    Punti4GiocatoriInSquadraEntityBean getLastRecordPunti4GiocatoriInSquadraByIdGioco(Integer idGioco);

    @Query("select * from Punti4GiocatoriInSquadraEntityBean where turno =(select max(turno) from Punti4GiocatoriInSquadraEntityBean where idGioco = :idGioco and idPartida = :idPartida)")
    Punti4GiocatoriInSquadraEntityBean getLastRecord4GiocatoriInSquadraByIdGiocoIdPartida(Integer idGioco, Integer idPartida);

    @Query("select  * from Punti4GiocatoriInSquadraEntityBean where idGioco=:idGioco")
    List<Punti4GiocatoriInSquadraEntityBean> selectAllPunti4GiocatoriInSquadraByIdGioco(Integer idGioco);

}
