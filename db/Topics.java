package db;

public class Topics 
{
    private String idTopic;
    private String idCity;
    private String idStation;
    private String idSensor;
    private String idAlert;
    private String value;
 
    // constructors
    public Topics() 
    {
    	this.idTopic = null;
    	this.idCity = null;
    	this.idStation = null;
    	this.idSensor = null;
    	this.idAlert = null;
    	this.setValue(null);
    }
    public Topics(String idTopic, String idCity, String idStation, String idSensor, String idAlert, String value) 
    {
    	this.idTopic = idTopic;
    	this.idCity = idCity;
    	this.idStation = idStation;
    	this.idSensor = idSensor;
    	this.idAlert = idAlert;
    	this.setValue(value);
    }

	public String getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(String idTopic) {
		this.idTopic = idTopic;
	}

	public String getIdCity() {
		return idCity;
	}

	public void setIdCity(String idCity) {
		this.idCity = idCity;
	}

	public String getIdStation() {
		return idStation;
	}

	public void setIdStation(String idStation) {
		this.idStation = idStation;
	}

	public String getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(String idSensor) {
		this.idSensor = idSensor;
	}

	public String getIdAlert() {
		return idAlert;
	}

	public void setIdAlert(String idAlert) {
		this.idAlert = idAlert;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
    
 }
