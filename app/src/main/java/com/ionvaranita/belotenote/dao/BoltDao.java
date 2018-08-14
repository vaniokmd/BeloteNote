package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ionvaranita.belotenote.entity.BoltEntityBean;

@Dao
public interface BoltDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateBoltByIdGiocoIdPartidaIdPersona(BoltEntityBean boltEntityBean);
    @Query("select b.nrBolt from BoltEntityBean b where b.idGioco=:idGioco and b.idPartida = :idPartida and b.idPersona = :idPersona")
    Integer getNrBoltByIdGiocoIdPartidaIdPersona(Integer idGioco,Integer idPartida,Integer idPersona);
}
