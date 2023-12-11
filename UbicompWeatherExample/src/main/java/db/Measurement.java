package db;

import java.util.Date;

public class Measurement 
{
	private int sensorType;
	private Date dateMeasurement;
	private double value;

	// constructors
	public Measurement() {
		this.setSensorType(0);
		this.dateMeasurement = null;
		this.value = 0.0;
	}
	public Measurement(int sensorType, Date dateMeasurement, double value) {
		this.sensorType = sensorType;
		this.dateMeasurement = dateMeasurement;
		this.value = value;
	}

	public int getSensorType() {
		return sensorType;
	}

	public void setSensorType(int sensorType) {
		this.sensorType = sensorType;
	}

	public Date getDateMeasurement() {
		return dateMeasurement;
	}

	public void setDateMeasurement(Date dateMeasurement) {
		this.dateMeasurement = dateMeasurement;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
}
