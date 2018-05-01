package com.ionvaranita.belotenote.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by ionvaranita on 11/04/18.
 */
@Entity
public class PuncteCastigatoareGlobalBean {

    @PrimaryKey
    private Integer puncteCastigatoare;
    @NonNull
    private Long data;

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public PuncteCastigatoareGlobalBean(){
        data = new Date().getTime();

    }

    public Integer getPuncteCastigatoare() {
        return puncteCastigatoare;
    }


    public void setPuncteCastigatoare(Integer puncteCastigatoare) {
        this.puncteCastigatoare = puncteCastigatoare;
    }
}
