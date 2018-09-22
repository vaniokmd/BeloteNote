package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ionvaranita.belotenote.entity.GiochiUltimaPartidaBean;

@Dao
public interface GiochiUltimaPartidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void inserisciOrUpdateIdPartidaByIdGioco(GiochiUltimaPartidaBean giochiUltimaPartidaBean);
    @Query("select * from GiochiUltimaPartidaBean where idGioco = :idGioco")
    GiochiUltimaPartidaBean getUltimaPartidaByIdGioco(Integer idGioco);
}
