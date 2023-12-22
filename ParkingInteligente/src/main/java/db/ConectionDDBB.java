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
	            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/parking");

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
    public static PreparedStatement GetCities(Connection con)
    {
    	return getStatement(con,"SELECT * FROM ciudad;");
    }
    
    public static PreparedStatement GetParkings(Connection con)
    {
    	return getStatement(con,"SELECT * FROM parking");
    }
    
    public static PreparedStatement GetParkingsFromCity(Connection con)
    {
    	return getStatement(con,"SELECT * FROM parking WHERE id_ciudad=?");
    }
    
    public static PreparedStatement GetParkingSensors(Connection con)
    {
    	return getStatement(con,"SELECT * FROM sensor WHERE id_parking=?;");
    }
    
    public static PreparedStatement GetParkingPlazasLibres(Connection con)
    {
    	return getStatement(con,"SELECT plazas_disponibles FROM parking WHERE id_parking=?;");
    }
    
    public static PreparedStatement GetParkingPlazas(Connection con)
    {
    	return getStatement(con,"SELECT plazas_totales FROM parking WHERE id_parking=?;");
    }
    
    public static PreparedStatement GetUlimasMediciones(Connection con)
    {
    	return getStatement(con,"select * from historico_mediciones where id_sensor=? ORDER BY fecha LIMIT 1;");
    }
    
    public static PreparedStatement GetUltimosCoches(Connection con)
    {
    	return getStatement(con,"select * from historico_coches where id_parking=? ORDER BY fecha LIMIT 1;");
    }
    
    public static PreparedStatement GetInfoFromParking(Connection con)
    {
    	return getStatement(con,"SELECT * FROM parking WHERE id_parking=?;");
    }
    
    public static PreparedStatement InsertMedicion(Connection con)
    {
    	return getStatement(con,"INSERT INTO historico_mediciones (id_sensor, fecha, valor, alerta) VALUES (?,?,?,?) ON duplicate key update id_sensor=?, fecha=?, valor=?, alerta=?;");  	
    }
    
    public static PreparedStatement UpdatePlazasLibres(Connection con)
    {
    	return getStatement(con,"INSERT INTO parking (id_parking, nombre, latitud, longitud, id_ciudad, plazas_totales, plazas_disponibles) VALUES (?, \"ParkRetiro\", 2.5, 3.5, 1, 3, ?) ON DUPLICATE KEY UPDATE plazas_disponibles = ?;");
    }
    
    public static PreparedStatement InsertMatriculas(Connection con)
    {
    	return getStatement(con,"INSERT INTO historico_coches (fecha, matricula, entrada, id_parking) VALUES (?,?,?,?) ON duplicate key update fecha=?, matricula=?, entrada=?, id_parking=?;");  	
    }
    

    public static PreparedStatement GetDataBD(Connection con)
    {
    	return getStatement(con,"SELECT * FROM parkig.historico_mediciones");
    }
    
    public static PreparedStatement SetDataBD(Connection con)
    {
    	return getStatement(con,"INSERT INTO parking.historico_mediciones VALUES (?,?)");
    }
    
    //************** CALLS TO THE DATABASE ***************************//
    public static PreparedStatement GetMonthTempFromParking(Connection con)
    {
    	PreparedStatement ps = getStatement(con,"SELECT DATE_FORMAT(historico_mediciones.fecha, '%Y-%m-%d') as fecha, AVG(historico_mediciones.valor) as media_valor\n"
    			+ "FROM historico_mediciones\n"
    			+ "JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor\n"
    			+ "JOIN parking ON sensor.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE sensor.id_tipo = ?\n"
    			+ "AND ciudad.id_ciudad = ?\n"
    			+ "AND parking.id_parking = ?\n"
    			+ "AND historico_mediciones.fecha >= NOW() - INTERVAL 30 DAY\n"
    			+ "GROUP BY DATE_FORMAT(historico_mediciones.fecha, '%Y-%m-%d');");
    	Log.log.debug(ps);
    return ps;
    }  
    public static PreparedStatement GetMonthGasesFromParking(Connection con)
    {
    	return getStatement(con,"SELECT DATE_FORMAT(historico_mediciones.fecha, '%Y-%m-%d') as fecha, AVG(historico_mediciones.valor) as media_valor\n"
    			+ "FROM historico_mediciones\n"
    			+ "JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor\n"
    			+ "JOIN parking ON sensor.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE sensor.id_tipo = ?\n"
    			+ "AND ciudad.id_ciudad = ?\n"
    			+ "AND parking.id_parking = ?\n"
    			+ "AND historico_mediciones.fecha >= NOW() - INTERVAL 30 DAY\n"
    			+ "GROUP BY DATE_FORMAT(historico_mediciones.fecha, '%Y-%m-%d');");
    }  
    public static PreparedStatement GetMonthHumidityFromParking(Connection con)
    {
    	return getStatement(con,"SELECT DATE_FORMAT(historico_mediciones.fecha, '%Y-%m-%d') as fecha, AVG(historico_mediciones.valor) as media_valor\n"
    			+ "FROM historico_mediciones\n"
    			+ "JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor\n"
    			+ "JOIN parking ON sensor.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE sensor.id_tipo = ?\n"
    			+ "AND ciudad.id_ciudad = ?\n"
    			+ "AND parking.id_parking = ?\n"
    			+ "AND historico_mediciones.fecha >= NOW() - INTERVAL 30 DAY\n"
    			+ "GROUP BY DATE_FORMAT(historico_mediciones.fecha, '%Y-%m-%d');");
    }
    public static PreparedStatement GetMonthAlarmsFromParking(Connection con)
    {
    	return getStatement(con,"SELECT historico_mediciones.id_sensor,historico_mediciones.fecha,historico_mediciones.valor,historico_mediciones.alerta\n"
    			+ "FROM historico_mediciones\n"
    			+ "JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor\n"
    			+ "JOIN parking ON sensor.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE historico_mediciones.alerta = true\n"
    			+ "AND ciudad.id_ciudad = ?\n"
    			+ "AND parking.id_parking = ?\n"
    			+ "AND historico_mediciones.fecha >= NOW() - INTERVAL 30 DAY;");  	
    } 
    public static PreparedStatement GetMonthCarHistoryFromParking(Connection con)
    {
    	return getStatement(con,"SELECT DATE(fecha) AS fecha_entrada, COUNT(*) AS cantidad_coches\n"
    			+ "FROM historico_coches\n"
    			+ "JOIN parking ON historico_coches.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE ciudad.id_ciudad = ?\n"
    			+ "AND parking.id_parking = ?\n"
    			+ "AND entrada = 1 AND fecha >= CURDATE() - INTERVAL 30 DAY GROUP BY fecha_entrada ORDER BY fecha_entrada DESC;");
    }   
    public static PreparedStatement GetEmptyPlacesFromParking(Connection con)
    {
    	return getStatement(con,"SELECT parking.plazas_disponibles\n" 
                        + "FROM parking\n" 
                        + "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
                        + "WHERE ciudad.id_ciudad = ?\n"
                        + "AND parking.id_parking = ?");
    }
    
    public static PreparedStatement InsertMeasurement(Connection con) {
    return getStatement(con, 
        "INSERT INTO historico_mediciones (id_sensor, fecha, valor, alerta) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE id_sensor=?, fecha=?, valor=?, alerta=?;");
    }    
	
    public static PreparedStatement GetActualCarHistoryFromParking(Connection con)
    {
    	return getStatement(con,"SELECT * FROM historico_coches\n"
    			+ "JOIN parking ON historico_coches.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE ciudad.id_ciudad = ?\n"
    			+ "AND parking.id_parking = ?\n"
    			+ "AND date(historico_coches.fecha) = current_date();");  	
    }
    
    public static PreparedStatement GetActualGasesFromParking(Connection con)
    {
    	return getStatement(con,"SELECT historico_mediciones.id_sensor,historico_mediciones.fecha,historico_mediciones.valor,historico_mediciones.alerta\n"
    			+ "FROM historico_mediciones\n"
    			+ "JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor\n"
    			+ "JOIN parking ON sensor.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE sensor.id_tipo = ?\n"
    			+ "  AND ciudad.id_ciudad = ?\n"
    			+ "  AND parking.id_parking = ?\n"
    			+ "ORDER BY historico_mediciones.fecha DESC\n"
    			+ "LIMIT 1;");  	
    }
    
    public static PreparedStatement GetActualHumidityFromParking(Connection con)
    {
    	return getStatement(con,"SELECT historico_mediciones.id_sensor,historico_mediciones.fecha,historico_mediciones.valor,historico_mediciones.alerta\n"
    			+ "FROM historico_mediciones\n"
    			+ "JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor\n"
    			+ "JOIN parking ON sensor.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE sensor.id_tipo = ?\n"
    			+ "  AND ciudad.id_ciudad = ?\n"
    			+ "  AND parking.id_parking = ?\n"
    			+ "ORDER BY historico_mediciones.fecha DESC\n"
    			+ "LIMIT 1;");  	
    }
    
    public static PreparedStatement GetActualTempFromParking(Connection con)
    {
    	return getStatement(con,"SELECT historico_mediciones.id_sensor,historico_mediciones.fecha,historico_mediciones.valor,historico_mediciones.alerta\n"
    			+ "FROM historico_mediciones\n"
    			+ "JOIN sensor ON historico_mediciones.id_sensor = sensor.id_sensor\n"
    			+ "JOIN parking ON sensor.id_parking = parking.id_parking\n"
    			+ "JOIN ciudad ON parking.id_ciudad = ciudad.id_ciudad\n"
    			+ "WHERE sensor.id_tipo = ?\n"
    			+ "  AND ciudad.id_ciudad = ?\n"
    			+ "  AND parking.id_parking = ?\n"
    			+ "ORDER BY historico_mediciones.fecha DESC\n"
    			+ "LIMIT 1;");  	
    }
    
}
