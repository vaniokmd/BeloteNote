package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ionvaranita.belotenote.entity.VisualizzazioneBoltEntityBean;

import java.util.List;
import java.util.Map;

@Dao
public interface VisualizzazioneBoltDao {
    @Insert
    void insertBolt(VisualizzazioneBoltEntityBean visualizzazioneBoltEntityBean);
    @Query("select * from VisualizzazioneBoltEntityBean v where v.idGioco = :idGioco")
    List<VisualizzazioneBoltEntityBean> getListaBoltByIdGioco(Integer idGioco);
}
