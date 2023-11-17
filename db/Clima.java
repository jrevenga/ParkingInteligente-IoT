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
public class Clima {
    private float temperatura;
    private float humedad;
    private float humoGas;
    private Timestamp fecha;
    private int idParking;

    public Clima() {
        // default
    }

    public Clima(float temperatura, float humedad, float humoGas, Timestamp fecha, int idParking) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.humoGas = humoGas;
        this.fecha = fecha;
        this.idParking = idParking;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public float getHumedad() {
        return humedad;
    }

    public void setHumedad(float humedad) {
        this.humedad = humedad;
    }

    public float getHumoGas() {
        return humoGas;
    }

    public void setHumoGas(float humoGas) {
        this.humoGas = humoGas;
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
