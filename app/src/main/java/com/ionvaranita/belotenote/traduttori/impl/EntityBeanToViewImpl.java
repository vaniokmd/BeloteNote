package com.ionvaranita.belotenote.traduttori.impl;

import android.os.Build;

import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.VisualizzazioneBoltEntityBean;
import com.ionvaranita.belotenote.traduttori.EntityBeanToViewFactory;
import com.ionvaranita.belotenote.view.Punti4GiocatoriInSquadraView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityBeanToViewImpl implements EntityBeanToViewFactory {


    private Map<Integer,VisualizzazioneBoltEntityBean> mappaIdRigaBoltBean = new HashMap<>();
    private Set<Integer> insiemeIdRigheBolt = new HashSet<>();

    private Integer nrPartide;

    public Integer getNrPartide() {
        return nrPartide;
    }

    public void setNrPartide(Integer nrPartide) {
        this.nrPartide = nrPartide;
    }

    private Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraEntityBeanToView(Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean) {
        Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraView = new Punti4GiocatoriInSquadraView();

        punti4GiocatoriInSquadraView.setPuntiGioco(fixNullIntegerValue(punti4GiocatoriInSquadraEntityBean.getPuntiGioco()));
        punti4GiocatoriInSquadraView.setIdPartida(punti4GiocatoriInSquadraEntityBean.getIdPartida());
        fixNoiVoi(punti4GiocatoriInSquadraView,punti4GiocatoriInSquadraEntityBean);

        return punti4GiocatoriInSquadraView;
    }

    @Override
    public Map<Integer,List<Punti4GiocatoriInSquadraView>> mappa4GiocatoriInSquadra(List<Punti4GiocatoriInSquadraEntityBean> punti4GiocatoriInSquadraEntityBeans,List<VisualizzazioneBoltEntityBean> listaBolt) {
        inizializzaListaBolt(listaBolt);
        Map<Integer,List<Punti4GiocatoriInSquadraView>> risultato = null;
        risultato = ragruppaPunti4GiocatoriByIdPartida(punti4GiocatoriInSquadraEntityBeans);
        if(Build.VERSION.SDK_INT>=24){
            //FIGO JAVA8
           // risultato=punti4GiocatoriInSquadraEntityBeans.stream().map(entita->punti4GiocatoriInSquadraEntityBeanToView(entita)).collect(Collectors.toMap(Punti4GiocatoriInSquadraView::getIdPartida));
        }
        else{

        }
        return risultato;
    }

    private void inizializzaListaBolt(List<VisualizzazioneBoltEntityBean> listaBolt){
        mappaIdRigaBoltBean.clear();
        insiemeIdRigheBolt.clear();
        for(VisualizzazioneBoltEntityBean bean:listaBolt){
            mappaIdRigaBoltBean.put(bean.getIdRiga(),bean);
            insiemeIdRigheBolt.add(bean.getIdRiga());
        }
    }

    @Override
    public List<Punti4GiocatoriInSquadraView> getAllPunti4GiocatoriInSquadraView(List<Punti4GiocatoriInSquadraEntityBean> punti4GiocatoriInSquadraEntityBeans, List<VisualizzazioneBoltEntityBean> listaBolt) {
        inizializzaListaBolt(listaBolt);
        List<Punti4GiocatoriInSquadraView> risultato = new ArrayList<>();
        Integer nrPartide = 0;
        Integer idPartida = null;
        for (Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean:
                punti4GiocatoriInSquadraEntityBeans) {
            if(idPartida!=punti4GiocatoriInSquadraEntityBean.getIdPartida()){
                idPartida = punti4GiocatoriInSquadraEntityBean.getIdPartida();
                nrPartide++;
            }
            risultato.add(punti4GiocatoriInSquadraEntityBeanToView(punti4GiocatoriInSquadraEntityBean));
        }
        this.nrPartide=nrPartide;
        return risultato;
     }

    private Map<Integer,List<Punti4GiocatoriInSquadraView>> ragruppaPunti4GiocatoriByIdPartida(List<Punti4GiocatoriInSquadraEntityBean> listaTabella4JucatoriInEchipa){
        Map<Integer,List<Punti4GiocatoriInSquadraView>> result = new HashMap<>();
        Integer idPartida = null;
        List<Punti4GiocatoriInSquadraView> listaPuntiByIdPartida= null;
        for (Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean:
                listaTabella4JucatoriInEchipa) {
            if(punti4GiocatoriInSquadraEntityBean.getIdPartida()!=idPartida){
                idPartida = punti4GiocatoriInSquadraEntityBean.getIdPartida();
                listaPuntiByIdPartida = new ArrayList<>();
                result.put(idPartida,listaPuntiByIdPartida);
            }
            listaPuntiByIdPartida.add(punti4GiocatoriInSquadraEntityBeanToView(punti4GiocatoriInSquadraEntityBean));


        }
        return result;
    }

    private void fixNoiVoi(Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraView,Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean){
        Integer idRigaBean = punti4GiocatoriInSquadraEntityBean.getId();
        Integer puntiNoiBean = punti4GiocatoriInSquadraEntityBean.getPuntiNoi();
        Integer puntiVoiBean = punti4GiocatoriInSquadraEntityBean.getPuntiVoi();
        if(!insiemeIdRigheBolt.isEmpty()&&insiemeIdRigheBolt.remove(idRigaBean)){
            VisualizzazioneBoltEntityBean visualizzazioneBoltEntityBean = mappaIdRigaBoltBean.get(idRigaBean);
            Integer idPersona = visualizzazioneBoltEntityBean.getIdPersona();

            if(idPersona.equals(IdsCampiStampa.ID_NOI)){
                punti4GiocatoriInSquadraView.setPuntiNoi(ConstantiGlobal.STRING_BOLT);
            }
            else{
                punti4GiocatoriInSquadraView.setPuntiNoi(fixNullIntegerValue(puntiNoiBean));

            }
            if(idPersona.equals(IdsCampiStampa.ID_VOI)){
                punti4GiocatoriInSquadraView.setPuntiVoi(ConstantiGlobal.STRING_BOLT);
            }
            else{
                punti4GiocatoriInSquadraView.setPuntiVoi(fixNullIntegerValue(puntiVoiBean));
            }

        }
        else{
            punti4GiocatoriInSquadraView.setPuntiNoi(fixNullIntegerValue(puntiNoiBean));
            punti4GiocatoriInSquadraView.setPuntiVoi(fixNullIntegerValue(puntiVoiBean));
        }

    }
    private String fixNullIntegerValue(Integer value){
        if(value==null){
            return "-";
        }
        return value.toString();
    }
}
