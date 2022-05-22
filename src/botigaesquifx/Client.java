package botigaesquifx;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class Client {
   private String dni;
   private String nom;
   private String cognom;
   private int telefon;
   private String email;

   private String numFamilia;
   private String numFederat;
   private int nivell;
   private LocalDate dataFederat;
   private LocalDate dataFamiliar;





    public Client(String dni, String nom, String cognom, int telefon, String email, String numFamilia, String numFederat, int nivell, LocalDate dataFederat, LocalDate dataFamiliar) {
        this.dni = dni;
        this.nom = nom;
        this.cognom = cognom;
        this.telefon = telefon;
        this.email = email;
        this.numFamilia = numFamilia;
        this.numFederat = numFederat;
        this.nivell = nivell;
        this.dataFederat = dataFederat;
        this.dataFamiliar = dataFamiliar;
    }
    public Client(){};

    // Getters i setters

    public int getNivell() {
        return nivell;
    }

    public void setNivell(int nivell) {
        this.nivell = nivell;
    }

    public LocalDate getDataFederat() {
        return dataFederat;
    }

    public void setDataFederat(LocalDate dataFederat) {
        this.dataFederat = dataFederat;
    }

    public LocalDate getDataFamiliar() {
        return dataFamiliar;
    }

    public void setDataFamiliar(LocalDate dataFamiliar) {
        this.dataFamiliar = dataFamiliar;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getnumFamilia() {
        return numFamilia;
    }

    public void setnumFamilia(String numFamilia) {
        this.numFamilia = numFamilia;
    }

    public String getNumFederat() {
        return numFederat;
    }

    public void setNumFederat(String numFederat) {
        this.numFederat = numFederat;
    }

    @Override
    public String toString() {
        return "Client [cognom=" + cognom + ", dni=" + dni + ", email=" + email + ", familiaNombrosa=" + numFamilia + ", nom=" + nom + ", numFederat=" + numFederat + ", telefon=" + telefon + "]";
    }


}