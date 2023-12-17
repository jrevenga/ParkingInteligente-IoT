package db;

public class SensorType 
{
    private int id;
    private String name;
    private String units;
    private double valuemin;
    private double valuemax;
    
    
    // constructors
    public SensorType() 
    {
    	this.id = 0;
    	this.name = null;
    	this.units = null;
        this.valuemin = 0;
        this.valuemax = 999;
    }
    public SensorType(int id, String name, String units, int value, double minvalue, double maxvalue) 
    {
    	this.id = id;
    	this.name = name;
    	this.units = units;
        this.valuemin = minvalue;
        this.valuemax = maxvalue;
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
            return valuemin;
        }

        public void setMinvalue(double minvalue) {
            this.valuemin = minvalue;
        }

        public double getMaxvalue() {
            return valuemax;
        }

        public void setMaxvalue(double maxvalue) {
            this.valuemax = maxvalue;
        }
        
        

 }
