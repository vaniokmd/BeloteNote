package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ionvaranita.belotenote.entity.TurnManagement4GiocatoriInSquadra;

/**
 * Created by ionvaranita on 22/12/2017.
 */
@Dao
public interface TurnManagement4GiocatoriInSquadraDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTurnManagement4JucatoriInEchipa(TurnManagement4GiocatoriInSquadra turnManagement4GiocatoriInSquadra);

    @Query("select * from TurnManagement4GiocatoriInSquadra where  idGioco=:idGioco")
    TurnManagement4GiocatoriInSquadra selectTurn4JucatoriInEchipaByIdJoc(Integer idGioco);

}
