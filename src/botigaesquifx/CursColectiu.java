package botigaesquifx;

import java.time.LocalDate;

public class CursColectiu extends Curs {

    private LocalDate data;
    private int preuFinal;

    public CursColectiu(int id, String nom, String dni_monitor, LocalDate data, int preuFinal) {
        super(id, nom, dni_monitor);
        this.data = data;
        this.preuFinal = preuFinal;
    }

    public LocalDate getData() {
        return data;
    }

    public void getData(LocalDate data) {
        this.data = data;
    }

    public int getPreuFinal() {
        return preuFinal;
    }

    public void setPreuFinal(int preuFinal) {
        this.preuFinal = preuFinal;
    }

}