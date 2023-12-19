package db;

public class Parking 
{
    private int id;
    private int ciudad;
    private String name;
    private Double latitude;
    private Double longitude;
 
    // constructors
    public Parking() 
    {
    	this.id = 0;
    	this.name = null;
    	this.latitude = 0.0;
    	this.longitude = 0.0;
    }

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

        public int getCiudad() {
            return ciudad;
        }

        public void setCiudad(int ciudad) {
            this.ciudad = ciudad;
        }
        
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public Double getLatitude() 
	{
		return latitude;
	}

	public void setLatitude(Double latitude) 
	{
		this.latitude = latitude;
	}

	public Double getLongitude() 
	{
		return longitude;
	}

	public void setLongitude(Double longitude) 
	{
		this.longitude = longitude;
	}	
 }
