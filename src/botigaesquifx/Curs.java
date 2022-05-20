package botigaesquifx;

public abstract class Curs{


    private static ConnexioBD conn = new ConnexioBD();

    private int id;
    private String nom;
    private String dni_monitor;


    public Curs(int id, String nom, String dni_monitor) {
        this.id = id;
        this.nom = nom;
        this.dni_monitor = dni_monitor;
    }


    @Override
    public String toString() {
        return "Curs [dni_monitor=" + dni_monitor + ", id=" + id + ", nom=" + nom + "]";
    }


    public static ConnexioBD getConn() {
        return conn;
    }


    public static void setConn(ConnexioBD conn) {
        Curs.conn = conn;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }


    public String getDni_monitor() {
        return dni_monitor;
    }


    public void setDni_monitor(String dni_monitor) {
        this.dni_monitor = dni_monitor;
    }


}
