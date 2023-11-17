/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Timestamp;

/**
 *
 * @author Esteban G
 */
public class Registro {
    private String matricula;
    private int idEvento;
    private Timestamp fecha;
    private int idParking;

    public Registro() {
        //default
    }

    public Registro(String matricula, int idEvento, Timestamp fecha, int idParking) {
        this.matricula = matricula;
        this.idEvento = idEvento;
        this.fecha = fecha;
        this.idParking = idParking;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getIdParking() {
        return idParking;
    }

    public void setIdParking(int idParking) {
        this.idParking = idParking;
    }
}