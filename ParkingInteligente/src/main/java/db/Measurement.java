package db;

import java.sql.Timestamp;

public class Measurement 
{
	private int sensor;
	private Timestamp timestamp;
	private double value;
        private boolean alerta;

	// constructors
	public Measurement() {
		this.setSensor(0);
		this.timestamp = null;
		this.value = 0.0;
                this.alerta = false;
	}
	public Measurement(int sensor, Timestamp timestamp, double value, boolean alerta) {
		this.sensor = sensor;
		this.timestamp = timestamp;
		this.value = value;
                this.alerta = alerta;
	}

	public int getSensor() {
		return sensor;
	}

	public void setSensor(int sensor) {
		this.sensor = sensor;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isAlerta() {
            return alerta;
        }

        public void setAlerta(boolean alerta) {
            this.alerta = alerta;
        }
        
}
