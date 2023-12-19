package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import logic.Log;


public class ConectionDDBB
{
	public Connection obtainConnection(boolean autoCommit) throws NullPointerException
    {
        Connection con=null;
        int intentos = 5;
        for (int i = 0; i < intentos; i++) 
        {
        	Log.logdb.info("Attempt {} to connect to the database", i);
        	try
	          {
	            Context ctx = new InitialContext();
	            // Get the connection factory configured in Tomcat
	            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ubicomp");

	            // Obtiene una conexion
	            con = ds.getConnection();
				Calendar calendar = Calendar.getInstance();
				java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
	            Log.logdb.debug("Connection creation. Bd connection identifier: {} obtained in {}", con.toString(), date.toString());
	            con.setAutoCommit(autoCommit);
	        	Log.logdb.info("Conection obtained in the attempt: " + i);
	            i = intentos;
	          } catch (NamingException ex)
	          {
	            Log.logdb.error("Error getting connection while trying: {} = {}", i, ex); 
	          } catch (SQLException ex)
	          {
	            Log.logdb.error("ERROR sql getting connection while trying:{ }= {}", i, ex);
	            throw (new NullPointerException("SQL connection is null"));
	          }
		}        
        return con;
    }
    
    public void closeTransaction(Connection con)
    {
        try
          {
            con.commit();
            Log.logdb.debug("Transaction closed");
          } catch (SQLException ex)
          {
            Log.logdb.error("Error closing the transaction: {}", ex);
          }
    }
    
    public void cancelTransaction(Connection con)
    {
        try
          {
            con.rollback();
            Log.logdb.debug("Transaction canceled");
          } catch (SQLException ex)
          {
            Log.logdb.error("ERROR sql when canceling the transation: {}", ex);
          }
    }

    public void closeConnection(Connection con)
    {
        try
          {
        	Log.logdb.info("Closing the connection");
            if (null != con)
              {
				Calendar calendar = Calendar.getInstance();
				java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
	            Log.logdb.debug("Connection closed. Bd connection identifier: {} obtained in {}", con.toString(), date.toString());
                con.close();
              }

        	Log.logdb.info("The connection has been closed");
          } catch (SQLException e)
          {
        	  Log.logdb.error("ERROR sql closing the connection: {}", e);
        	  e.printStackTrace();
          }
    }
    
    public static PreparedStatement getStatement(Connection con,String sql)
    {
        PreparedStatement ps = null;
        try
          {
            if (con != null)
              {
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

              }
          } catch (SQLException ex)
          {
    	        Log.logdb.warn("ERROR sql creating PreparedStatement:{} ", ex);
          }

        return ps;
    }   
    
    //************** CALLS TO THE DATABASE ***************************//
    public static PreparedStatement GetMonthTempFromParking(Connection con)
    {
    	return getStatement(con,"SELECT * FROM historico_mediciones JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor JOIN parking ON sensor.id_parking = parking.id_parking JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad WHERE sensor.id_tipo = 1 AND ciudad.id_ciudad = ? AND parking.id_parking = ? AND historico_mediciones.fecha >= NOW() - INTERVAL 30 DAY;");  	
    }  
    public static PreparedStatement GetMonthGasesFromParking(Connection con)
    {
    	return getStatement(con,"SELECT * FROM historico_mediciones JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor JOIN parking ON sensor.id_parking = parking.id_parking JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad WHERE sensor.id_tipo = 2 AND ciudad.id_ciudad = ? AND parking.id_parking = ? AND historico_mediciones.fecha >= NOW() - INTERVAL 30 DAY;");  		
    }  
    public static PreparedStatement GetMonthAlarmsFromParking(Connection con)
    {
    	return getStatement(con,"SELECT * FROM historico_mediciones JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor JOIN parking ON sensor.id_parking = parking.id_parking JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad WHERE historico_mediciones.alerta = false AND ciudad.id_ciudad = ? AND parking.id_parking = ? AND historico_mediciones.fecha >= NOW() - INTERVAL 30 DAY;");  	
    }
    public static PreparedStatement GetMonthCarHistoryFromParking(Connection con)
    {
    	return getStatement(con,"SELECT * FROM historico_coches JOIN parking ON historico_coches.id_parking = parking.id_parking JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad WHERE ciudad.id_ciudad = ? AND parking.id_parking = ? AND historico_coches.fecha >= NOW() - INTERVAL 30 DAY;");  	
    }  
    public static PreparedStatement GetParkingTimeDayFromParking(Connection con)
    {
    	return getStatement(con,"#SELECT * FROM historico_coches JOIN parking ON historico_coches.id_parking = parking.id_parking JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad WHERE ciudad.id_ciudad = ? AND parking.id_parking = ? AND date(historico_coches.fecha) = current_date();");  	
    }  
/*
    public static PreparedStatement GetParkings(Connection con)
    {
    	return getStatement(con,"SELECT * FROM PARKING.PARKING");  	
    }

    public static PreparedStatement GetParkingsFromCity(Connection con)
    {
    	return getStatement(con,"SELECT * FROM PARKING.STATION WHERE id_ciudad=?");  	
    } 
    
    public static PreparedStatement GetParkingSensors(Connection con)
    {
    	return getStatement(con,"SELECT * FROM PARKING.SENSOR WHERE id_parking=?;");  	
    }
    
    public static PreparedStatement GetCities(Connection con)
    {
    	return getStatement(con,"SELECT * FROM ciudad;");  	
    }
    
    public static PreparedStatement InsertMeasurement(Connection con) {
    return getStatement(con, 
        "INSERT INTO historico_mediciones (id_sensor, fecha, valor, alerta) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE id_sensor=?, fecha=?, valor=?, alerta=?;");
    }

    public static PreparedStatement GetLastValueStationSensor(Connection con)
    {
    	return getStatement(con,"select * from MEASUREMENT where id_parking=? AND id_sensor= ? ORDER BY fecha LIMIT 1;");  	
    }
    
    public static PreparedStatement GetInfoFromStation(Connection con)
    {
    	return getStatement(con,"SELECT * FROM PARKING.SENSOR WHERE id_parking=?;");  	
    }
    
    public static PreparedStatement GetDataBD(Connection con)
    {
    	return getStatement(con,"SELECT * FROM PARKING.historico_mediciones");  	
    }
    
    public static PreparedStatement SetDataBD(Connection con)
    {
    	return getStatement(con,"INSERT INTO PARKING.HISTORICO_MEDICIONES VALUES (?,?)");  	
    }
*/
    public static PreparedStatement GetActualCarHistoryFromParking(Connection con)
    {
    	return getStatement(con,"#SELECT * FROM historico_coches JOIN parking ON historico_coches.id_parking = parking.id_parking JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad WHERE ciudad.id_ciudad = ? AND parking.id_parking = ? AND date(historico_coches.fecha) = current_date();");  	
    }
    
    public static PreparedStatement GetActualGasesFromParking(Connection con)
    {
    	return getStatement(con,"SELECT * FROM historico_mediciones JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor JOIN parking ON sensor.id_parking = parking.id_parking JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad WHERE sensor.id_tipo = 2 AND ciudad.id_ciudad = ? AND parking.id_parking = ? AND date(historico_mediciones.fecha) = current_date();");  	
    }
    
    public static PreparedStatement GetActuaTempFromParking(Connection con)
    {
    	return getStatement(con,"SELECT * FROM historico_mediciones JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor JOIN parking ON sensor.id_parking = parking.id_parking JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad WHERE sensor.id_tipo = 1 AND ciudad.id_ciudad = ? AND parking.id_parking = ? AND date(historico_mediciones.fecha) = current_date();");  	
    }
    
    public static PreparedStatement InsertnewMeasurement(Connection con)
    {
        corregir
    	return getStatement(con,"INSERT INTO Measurement (STATION_ID, SENSORTYPE_ID, DATE, VALUE) VALUES (?,?,?,?) ON duplicate key update STATION_ID=?, SENSORTYPE_ID=?, DATE=?, VALUE=?;"); 
    }
}
