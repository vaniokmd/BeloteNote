package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.ionvaranita.belotenote.entity.VisualizzazioneBoltEntityBean;

@Dao
public interface VisualizzazioneBoltDao {
    @Insert
    void insertBolt(VisualizzazioneBoltEntityBean visualizzazioneBoltEntityBean);
}
