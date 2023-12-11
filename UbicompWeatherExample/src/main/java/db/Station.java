package db;

public class Station 
{
    private int id;
    private String name;
    private Double latitude;
    private Double longitude;
 
    // constructors
    public Station() 
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
