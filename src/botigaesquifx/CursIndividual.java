package botigaesquifx;

public class CursIndividual extends Curs{

    private int preuHora;


    

    public CursIndividual(int id, String nom, String dni_monitor, int preuHora) {
        super(id, nom, dni_monitor);
        this.preuHora = preuHora;
    }




    public int getPreuHora() {
        return preuHora;
    }




    public void setPreuHora(int preuHora) {
        this.preuHora = preuHora;
    }

    



    
    
}
