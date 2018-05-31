package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ionvaranita.belotenote.entity.PuncteCastigatoare4JucatoriInEchipaBean;

import java.util.List;

/**
 * Created by ionvaranita on 02/04/18.
 */
@Dao
public interface PuncteCastigatoare4JucatoriInEchipaDao {
    @Query("select * from PuncteCastigatoare4JucatoriInEchipaBean")
    List<PuncteCastigatoare4JucatoriInEchipaBean> selectAllPuncteCastigatoare4JucatoriInEchipa();
    @Query("select * from PuncteCastigatoare4JucatoriInEchipaBean where idGioco = :idGioco and idPartida =( select max(idPartida) from PuncteCastigatoare4JucatoriInEchipaBean where idGioco = :idGioco)")
    PuncteCastigatoare4JucatoriInEchipaBean selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndMaxIdPartida(Integer idGioco);
    @Query("select * from PuncteCastigatoare4JucatoriInEchipaBean where idGioco = :idGioco and idPartida =:idPartida ")
    PuncteCastigatoare4JucatoriInEchipaBean selectPuncteCastigatoare4JucatoriInEchipaByIdJocAndIdPartida(Integer idGioco,Integer idPartida);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPuncteCastigatoare4JucatoriInEchipa(PuncteCastigatoare4JucatoriInEchipaBean puncteCastigatoare4JucatoriInEchipaBean);
}
