package db;

public class Sensor {
    
    private int parking;
    private int tipo;
    private int id;

    // constructors
    public Sensor() 
    {
    	this.id = 0;
    	this.parking = 0;
    	this.tipo = 0;
    }
    public Sensor(int id, int tipo, int parking) 
    {
    	this.id = id;
    	this.parking = parking;
    	this.tipo = tipo;
    }

    
    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
