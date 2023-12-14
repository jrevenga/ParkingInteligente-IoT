package db;

public class SensorType 
{
    private int id;
    private String name;
    private String units;
    private double minvalue;
    private double maxvalue;
    
    
    // constructors
    public SensorType() 
    {
    	this.id = 0;
    	this.name = null;
    	this.units = null;
        this.minvalue = 0;
        this.maxvalue = 999;
    }
    public SensorType(int id, String name, String units, int value, double minvalue, double maxvalue) 
    {
    	this.id = id;
    	this.name = name;
    	this.units = units;
        this.minvalue = minvalue;
        this.maxvalue = maxvalue;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

        public double getMinvalue() {
            return minvalue;
        }

        public void setMinvalue(double minvalue) {
            this.minvalue = minvalue;
        }

        public double getMaxvalue() {
            return maxvalue;
        }

        public void setMaxvalue(double maxvalue) {
            this.maxvalue = maxvalue;
        }
        
        

 }
