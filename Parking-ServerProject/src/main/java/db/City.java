package db;

public class City 
{
    private int id;
    private String name;
    private String country;
 
    // constructors
    public City() 
    {
    	this.id = 0;
    	this.name = null;
        this.country = null;
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
        
        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
 }
