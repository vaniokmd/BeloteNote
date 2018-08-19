package com.ionvaranita.belotenote.traduttori.impl;

import com.ionvaranita.belotenote.constanti.ConstantiGlobal;
import com.ionvaranita.belotenote.constanti.IdsCampiStampa;
import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.VisualizzazioneBoltEntityBean;
import com.ionvaranita.belotenote.traduttori.EntityBeanToViewFactory;
import com.ionvaranita.belotenote.view.Punti4GiocatoriInSquadraView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Punti4GiocatoriInSquadraEntityBeanImpl implements EntityBeanToViewFactory {

    private List<VisualizzazioneBoltEntityBean> listaBolt;
    private Map<Integer,VisualizzazioneBoltEntityBean> mappaIdRigaBoltBean = new HashMap<>();
    private Set<Integer> insiemeIdRigheBolt;
    public Punti4GiocatoriInSquadraEntityBeanImpl(List<VisualizzazioneBoltEntityBean> listaBolt){
        this.listaBolt=listaBolt;
        for(VisualizzazioneBoltEntityBean bean:this.listaBolt){
            mappaIdRigaBoltBean.put(bean.getIdRiga(),bean);
            insiemeIdRigheBolt.add(bean.getIdRiga());
        }


    }
    @Override
    public Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraEntityBeanToView(Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean) {
        Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraView = new Punti4GiocatoriInSquadraView();

        punti4GiocatoriInSquadraView.setPuntiGioco(fixNullIntegerValue(punti4GiocatoriInSquadraEntityBean.getPuntiGioco()));
        fixNoiVoi(punti4GiocatoriInSquadraView,punti4GiocatoriInSquadraEntityBean);

        return punti4GiocatoriInSquadraView;
    }

    @Override
    public List<Punti4GiocatoriInSquadraView> listPunti4GiocatoriInSquadraEntityBeanToListView(List<Punti4GiocatoriInSquadraEntityBean> punti4GiocatoriInSquadraEntityBeans) {
        List<Punti4GiocatoriInSquadraView> punti4GiocatoriInSquadraViews = new ArrayList<>();

        for (Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean:punti4GiocatoriInSquadraEntityBeans) {
            punti4GiocatoriInSquadraViews.add(punti4GiocatoriInSquadraEntityBeanToView(punti4GiocatoriInSquadraEntityBean));
        }

        return punti4GiocatoriInSquadraViews;
    }
    private void fixNoiVoi(Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraView,Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean){
        Integer idRigaBean = punti4GiocatoriInSquadraEntityBean.getId();
        Integer puntiNoiBean = punti4GiocatoriInSquadraEntityBean.getPuntiNoi();
        Integer puntiVoiBean = punti4GiocatoriInSquadraEntityBean.getPuntiVoi();
        if(puntiNoiBean==null||puntiVoiBean==null){
            punti4GiocatoriInSquadraView.setPuntiNoi(fixNullIntegerValue(puntiNoiBean));
            punti4GiocatoriInSquadraView.setPuntiVoi(fixNullIntegerValue(puntiVoiBean));
        }
        else if(!insiemeIdRigheBolt.isEmpty()&&insiemeIdRigheBolt.remove(idRigaBean)){
            VisualizzazioneBoltEntityBean visualizzazioneBoltEntityBean = mappaIdRigaBoltBean.get(idRigaBean);
            Integer idPersona = visualizzazioneBoltEntityBean.getIdPersona();

            if(idPersona.equals(IdsCampiStampa.ID_PUNTI_NOI)){
                punti4GiocatoriInSquadraView.setPuntiNoi(ConstantiGlobal.STRING_BOLT);
            }
            else if(idPersona.equals(IdsCampiStampa.ID_PUNTI_VOI)){
                punti4GiocatoriInSquadraView.setPuntiVoi(ConstantiGlobal.STRING_BOLT);
            }

        }

    }
    private String fixNullIntegerValue(Integer value){
        if(value==null){
            return "-";
        }
        return value.toString();
    }
}
