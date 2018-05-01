package com.ionvaranita.belotenote.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ionvaranita.belotenote.entity.PuncteCastigatoareGlobalBean;

import java.util.List;

/**
 * Created by ionvaranita on 11/04/18.
 */
@Dao
public interface PuncteCastigatoareGlobalDao {

    @Query("select * from PuncteCastigatoareGlobalBean order by data desc")
    List<PuncteCastigatoareGlobalBean> selectAllPuncteCastigatoareGlobalOrderByData();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPuncteCastigatoareGlobal(PuncteCastigatoareGlobalBean puncteCastigatoareGlobalBean);

}
