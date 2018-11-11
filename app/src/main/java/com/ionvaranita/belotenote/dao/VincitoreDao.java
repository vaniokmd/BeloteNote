package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ionvaranita.belotenote.entity.VincitoreBean;

import java.util.List;

@Dao
public interface VincitoreDao {
    @Insert
    void inserisciVincitore(VincitoreBean vincitoreBean);

    @Query("select * from VincitoreBean as c where c.idGioco = :idGioco")
    List<VincitoreBean> getListVincitoriByIdGioco(Integer idGioco);
}
