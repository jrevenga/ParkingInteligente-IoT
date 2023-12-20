package db;

public class Topics 
{
    private String idTopic;
    private String idCity;
    private String idParking;
    private String idSensor;
    private String idPlaza;
    private String plazasLibres;
    private String entrada;
    private String salida;
    private String value;
    private String matricula;
 
    // constructors
    public Topics() 
    {
    	this.idTopic = null;
    	this.idCity = null;
    	this.idParking = null;
    	this.idSensor = null;
    	this.idPlaza = null;
    	this.setPlazasLibres(null);
    	this.setEntrada(null);
    	this.setSalida(null);
    	this.setValue(null);
    	this.setMatricula(null);
    }
    public Topics(String idTopic, String idCity, String idParking, String idSensor, String idPlaza, String plazasLibres, String entrada, String salida, String value, String matricula)
    {
    	this.idTopic = idTopic;
    	this.idCity = idCity;
    	this.idParking = idParking;
    	this.idSensor = idSensor;
    	this.idPlaza = idPlaza;
    	this.setPlazasLibres(plazasLibres);
    	this.setEntrada(entrada);
    	this.setSalida(salida);
    	this.setValue(value);
    	this.setMatricula(matricula);
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
		if (Double.parseDouble(value) > 2600) {
			return "1";
		} else {
			return "0";
		}
	}
	
	public String getIdPlaza() {
		return idPlaza;
	}
	public void setIdPlaza(String idPlaza) {
		this.idPlaza = idPlaza;
	}
	
	public String getPlazasLibres() {
		return plazasLibres;
	}
	public void setPlazasLibres(String PlazasLibres) {
		this.plazasLibres = PlazasLibres;
	}
	
	public String getEntrada() {
		return entrada;
	}
	public void setEntrada(String Entrada) {
		this.entrada = Entrada;
	}
	
	public String getSalida() {
		return salida;
	}
	public void setSalida(String salida) {
		this.salida = salida;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
    
 }
