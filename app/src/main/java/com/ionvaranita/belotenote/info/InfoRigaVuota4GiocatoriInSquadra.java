package com.ionvaranita.belotenote.info;

public class InfoRigaVuota4GiocatoriInSquadra {
    private Integer idGioco;
    private Integer idPartida;
    private Integer winnerPoints;
    private String tipoFinePartida;

    public String getTipoFinePartida() {
        return tipoFinePartida;
    }

    public void setTipoFinePartida(String tipoFinePartida) {
        this.tipoFinePartida = tipoFinePartida;
    }

    public Integer getIdGioco() {
        return idGioco;
    }

    public void setIdGioco(Integer idGioco) {
        this.idGioco = idGioco;
    }

    public Integer getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public Integer getWinnerPoints() {
        return winnerPoints;
    }

    public void setWinnerPoints(Integer winnerPoints) {
        this.winnerPoints = winnerPoints;
    }
}
