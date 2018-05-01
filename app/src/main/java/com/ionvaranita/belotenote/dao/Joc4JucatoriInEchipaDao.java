package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ionvaranita.belotenote.entity.Gioco4GiocatoriInSquadra;

import java.util.List;

/**
 * Created by ionvaranita on 11/12/2017.
 */
@Dao
public interface Joc4JucatoriInEchipaDao {
    @Query("select * from Gioco4GiocatoriInSquadra order by idGioco desc")
    List<Gioco4GiocatoriInSquadra> selectAllJocuri4JucatoriInEchipa();

    @Insert
    void insertJoc4JucatoriInEchipa(Gioco4GiocatoriInSquadra gioco4GiocatoriInSquadra);

    @Query("select * from Gioco4GiocatoriInSquadra where idGioco=(select max(idGioco) from Gioco4GiocatoriInSquadra)")
    Gioco4GiocatoriInSquadra selectLastJocInserit4JucatoriInEchipa();

    @Query("select * from Gioco4GiocatoriInSquadra where idGioco = :idJoc")
    Gioco4GiocatoriInSquadra selectJocByIdJoc(Integer idJoc);

}
