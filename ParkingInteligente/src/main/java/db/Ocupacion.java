package db;

import java.sql.Date;

public class Ocupacion {
	  private Date fecha;
	  private int cantidad;
	 
	    // constructors
	    public Ocupacion() 
	    {
	    	this.fecha = null;
	    	this.cantidad = 0;
	    }

		public Date getFecha() 
		{
			return fecha;
		}

		public void setFecha(Date fecha) 
		{
			this.fecha = fecha;
		}

        public int getCantidad() {
            return cantidad;
        }

        public void setCantidad(int cantidad) {
            this.cantidad = cantidad;
        }
}
