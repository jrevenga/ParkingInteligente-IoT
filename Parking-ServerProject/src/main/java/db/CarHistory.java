package db;

import java.sql.Timestamp;

public class CarHistory 
{
    private Timestamp timestamp;
    private String matricula;
    private boolean entrada;
    private int parking;

    public CarHistory(Timestamp timestamp, String matricula, boolean entrada, int parking) {
        this.timestamp = timestamp;
        this.matricula = matricula;
        this.entrada = entrada;
        this.parking = parking;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }
        
}
