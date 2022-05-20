package botigaesquifx;

public class CursColectiu extends Curs {

    private int idDia;
    private int preuFinal;

    public CursColectiu(int id, String nom, String dni_monitor, int idDia, int preuFinal) {
        super(id, nom, dni_monitor);
        this.idDia = idDia;
        this.preuFinal = preuFinal;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public int getPreuFinal() {
        return preuFinal;
    }

    public void setPreuFinal(int preuFinal) {
        this.preuFinal = preuFinal;
    }

}