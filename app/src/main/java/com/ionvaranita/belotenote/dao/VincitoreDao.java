package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.ionvaranita.belotenote.entity.VincitoreBean;

@Dao
public interface VincitoreDao {
    @Insert
    void inserisciVincitore(VincitoreBean vincitoreBean);
}
