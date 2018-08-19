package com.ionvaranita.belotenote.traduttori;

import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.view.Punti4GiocatoriInSquadraView;

import java.util.List;

public interface EntityBeanToViewFactory {
     Punti4GiocatoriInSquadraView punti4GiocatoriInSquadraEntityBeanToView(Punti4GiocatoriInSquadraEntityBean punti4GiocatoriInSquadraEntityBean);
     List<Punti4GiocatoriInSquadraView> listPunti4GiocatoriInSquadraEntityBeanToListView(List<Punti4GiocatoriInSquadraEntityBean> punti4GiocatoriInSquadraEntityBeans);
}
