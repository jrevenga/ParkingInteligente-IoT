/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author Esteban G
 */
public class EventoPlaza {
    private int idPlaza;
    private int idEvento;
    private int fecha;

    public EventoPlaza() {
        // default
    }

    public EventoPlaza(int idPlaza, int idEvento, int fecha) {
        this.idPlaza = idPlaza;
        this.idEvento = idEvento;
        this.fecha = fecha;
    }

    public int getIdPlaza() {
        return idPlaza;
    }

    public void setIdPlaza(int idPlaza) {
        this.idPlaza = idPlaza;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }
}

