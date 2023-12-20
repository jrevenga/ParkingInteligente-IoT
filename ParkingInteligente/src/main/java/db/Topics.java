package db;

public class Topics 
{
    private String idTopic;
    private String idCity;
    private String idParking;
    private String idSensor;
    private String idAlert;
    private String value;
    private String falso = "0";
 
    // constructors
    public Topics() 
    {
    	this.idTopic = null;
    	this.idCity = null;
    	this.idParking = null;
    	this.idSensor = null;
    	this.idAlert = null;
    	this.setValue(null);
    }
    public Topics(String idTopic, String idCity, String idParking, String idSensor, String idAlert, String value) 
    {
    	this.idTopic = idTopic;
    	this.idCity = idCity;
    	this.idParking = idParking;
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

	public String getIdParking() {
		return idParking;
	}

	public void setIdParking(String idParking) {
		this.idParking = idParking;
	}

	public String getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(String idSensor) {
		this.idSensor = idSensor;
	}

	public String getIdAlert() {
		if (idAlert == null) {
			return falso;
		} else {
			return idAlert;
		}
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
