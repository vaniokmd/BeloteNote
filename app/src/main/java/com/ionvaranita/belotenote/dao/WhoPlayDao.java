package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ionvaranita.belotenote.entity.WhoPlayEntityBean;

@Dao
public interface WhoPlayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateWhoPlayed(WhoPlayEntityBean whoPlayEntityBean);
    @Query("select * from WhoPlayEntityBean w where w.idGioco=:idGioco")
    WhoPlayEntityBean getWhoPlayedByIdGioco(Integer idGioco);

}
