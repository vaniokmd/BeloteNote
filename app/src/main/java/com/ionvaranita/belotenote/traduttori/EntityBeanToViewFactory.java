package com.ionvaranita.belotenote.traduttori;

import com.ionvaranita.belotenote.entity.Punti4GiocatoriInSquadraEntityBean;
import com.ionvaranita.belotenote.entity.VisualizzazioneBoltEntityBean;
import com.ionvaranita.belotenote.view.Punti4GiocatoriInSquadraView;

import java.util.List;
import java.util.Map;

public interface EntityBeanToViewFactory {
     Map<Integer,List<Punti4GiocatoriInSquadraView>> mappa4GiocatoriInSquadra(List<Punti4GiocatoriInSquadraEntityBean> punti4GiocatoriInSquadraEntityBeans,List<VisualizzazioneBoltEntityBean> listaBolt);
     List<Punti4GiocatoriInSquadraView> getAllPunti4GiocatoriInSquadraView(List<Punti4GiocatoriInSquadraEntityBean> punti4GiocatoriInSquadraEntityBeans,List<VisualizzazioneBoltEntityBean> listaBolt);
}
