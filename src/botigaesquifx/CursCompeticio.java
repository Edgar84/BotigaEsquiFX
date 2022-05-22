package botigaesquifx;

import java.time.LocalDate;

public class CursCompeticio extends Curs {

    private int nivell;
    private LocalDate dataInici;
    private LocalDate dataFi;
    private int preu;

    public CursCompeticio(int id, String nom, String dni_monitor, int nivell, LocalDate dataInici, LocalDate dataFi, int preu) {
        super(id, nom, dni_monitor);
        this.nivell = nivell;
        this.dataInici = dataInici;
        this.dataFi = dataFi;
        this.preu = preu;
    }

    // Getters i setters

    public int getNivell() {
        return nivell;
    }

    public void setNivell(int nivell) {
        this.nivell = nivell;
    }

    public LocalDate getDataInici() {
        return dataInici;
    }

    public void setDataInici(LocalDate dataInici) {
        this.dataInici = dataInici;
    }

    public LocalDate getDataFi() {
        return dataFi;
    }

    public void setDataFi(LocalDate dataFi) {
        this.dataFi = dataFi;
    }

    public int getPreu() {
        return preu;
    }

    public void setPreu(int preu) {
        this.preu = preu;
    }

    @Override
    public String toString() {
        return "CursCompeticio [dataFi=" + dataFi + ", dataInici=" + dataInici + ", nivell=" + nivell + ", preu=" + preu + "]";
    }

}