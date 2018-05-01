package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ionvaranita.belotenote.entity.Scor4JucatoriInEchipaEntityBean;

/**
 * Created by ionvaranita on 09/12/2017.
 */
@Dao
public interface Scor4JucatoriInEchipaDao {
    @Query(value = "select * from Scor4JucatoriInEchipaEntityBean where idGioco = :idJoc")
    Scor4JucatoriInEchipaEntityBean selectScorBean4JucatoriInEchipaByIdJoc(Integer idJoc);
    @Insert
    void insertScor4JucatoriInEchipa(Scor4JucatoriInEchipaEntityBean scor4JucatoriInEchipaEntityBean);
    @Update
    void updateScor4JucatoriInEchipa(Scor4JucatoriInEchipaEntityBean scor4JucatoriInEchipaEntityBean);
}
