package logic;

import java.util.ArrayList;

import db.ChartMeasurements;
import db.City;
import db.ConectionDDBB;
import db.SensorType;
import db.Sensor;
import db.CarHistory;
import db.Measurement;
import db.Parking;
import db.Topics;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Logic 
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return Temperature, Humidity and Gas concentration of the parking
	 */
        public static ArrayList<Measurement> getMonthTempFromParking(int idCiudad, int idParking, int idTipo) {
            ArrayList<Measurement> temperaturas = new ArrayList<Measurement>();
            ConectionDDBB conector = new ConectionDDBB();
            Connection con = null;

            try {
                con = conector.obtainConnection(true);
                Log.log.debug("Database Connected");

                PreparedStatement ps = ConectionDDBB.GetMonthTempFromParking(con);
                Log.log.info("ps= ", ps.toString());
                ps.setInt(1, idTipo);
    			ps.setInt(2, idCiudad);
    			ps.setInt(3, idParking);
                Log.log.info("Query=> {}", ps.toString());
                ResultSet rs = ps.executeQuery();
                

                int idSensor = idTipo;

                if (rs.next()) {
                    do {
                        Measurement temp = new Measurement();
                        temp.setSensor(idSensor);
                        temp.setValue(rs.getDouble("MEDIA_VALOR"));
                        temp.setTimestamp(rs.getTimestamp("FECHA"));
                        temperaturas.add(temp);
                    } while (rs.next());

                    // Calcular la media solo si hay al menos una medición
                } else {
                    Log.log.warn("El conjunto de resultados está vacío.");
                }

            } catch (SQLException e) {
                Log.log.error("Error: {}", e);
            } catch (NullPointerException e) {
                Log.log.error("Error: {}", e);
            } catch (Exception e) {
                Log.log.error("Error: {}", e);
            } finally {
                conector.closeConnection(con);
            }

            return temperaturas;
        }
        
        public static ArrayList<Measurement> getActualHumidityFromParking(int idCiudad, int idParking, int idTipo)
    	{
    		ArrayList<Measurement> humedades = new ArrayList<Measurement>();

    		ConectionDDBB conector = new ConectionDDBB();
    		Connection con = null;
    		try
    		{
    			con = conector.obtainConnection(true);
    			Log.log.debug("Database Connected");
    			
    			PreparedStatement ps = ConectionDDBB.GetActualGasesFromParking(con);
    			ps.setInt(1, idCiudad);
    			ps.setInt(2, idParking);
    			ps.setInt(3, idTipo);
    			Log.log.info("Query=> {}", ps.toString());
    			ResultSet rs = ps.executeQuery();
    			while (rs.next())
    			{
    				Measurement humedad = new Measurement();
    				humedad.setSensor(rs.getInt("ID_SENSOR"));
    				humedad.setValue(rs.getDouble("VALOR"));
    				humedad.setTimestamp(rs.getTimestamp("FECHA"));
    				humedad.setAlerta(rs.getBoolean("ALERTA"));
    				humedades.add(humedad);
    			}
    		} catch (SQLException e)
    		{
    			Log.log.error("Error: {}", e);
    			humedades = new ArrayList<Measurement>();
    		} catch (NullPointerException e)
    		{
    			Log.log.error("Error: {}", e);
    			humedades = new ArrayList<Measurement>();
    		} catch (Exception e)
    		{
    			Log.log.error("Error:{}", e);
    			humedades = new ArrayList<Measurement>();
    		} finally
    		{
    			conector.closeConnection(con);
    		}
    		return humedades;
    	}

	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return Temperature, Humidity and Gas concentration of the parking
	 */
	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return Temperature, Humidity and Gas concentration of the parking
	 */
        public static ArrayList<Measurement> getMonthGasesFromParking(int idCiudad, int idParking, int idTipo) {
            ArrayList<Measurement> gases = new ArrayList<Measurement>();
            ConectionDDBB conector = new ConectionDDBB();
            Connection con = null;

            try {
                con = conector.obtainConnection(true);
                Log.log.debug("Database Connected 1");

                PreparedStatement ps = ConectionDDBB.GetMonthGasesFromParking(con);
                Log.log.info("ps= ", ps.toString());
                ps.setInt(1, idTipo);
    			ps.setInt(2, idCiudad);
    			ps.setInt(3, idParking);
                Log.log.info("Query=> {}", ps.toString());
                ResultSet rs = ps.executeQuery();
                

                int idSensor = idTipo;

                if (rs.next()) {
                    do {
                        Measurement gas = new Measurement();
                        gas.setSensor(idSensor);
                        gas.setValue(rs.getDouble("MEDIA_VALOR"));
                        gas.setTimestamp(rs.getTimestamp("FECHA"));
                        gases.add(gas);
                    } while (rs.next());

                    // Calcular la media solo si hay al menos una medición
                } else {
                    Log.log.warn("El conjunto de resultados está vacío.");
                }

            } catch (SQLException e) {
                Log.log.error("Error: {}", e);
            } catch (NullPointerException e) {
                Log.log.error("Error: {}", e);
            } catch (Exception e) {
                Log.log.error("Error: {}", e);
            } finally {
                conector.closeConnection(con);
            }

            return gases;
        }
        
        public static ArrayList<Measurement> getMonthHumidityFromParking(int idCiudad, int idParking, int idTipo) {
            ArrayList<Measurement> humedades = new ArrayList<Measurement>();
            ConectionDDBB conector = new ConectionDDBB();
            Connection con = null;

            try {
                con = conector.obtainConnection(true);
                Log.log.debug("Database Connected 1");

                PreparedStatement ps = ConectionDDBB.GetMonthHumidityFromParking(con);
                Log.log.info("ps= ", ps.toString());
                ps.setInt(1, idTipo);
    			ps.setInt(2, idCiudad);
    			ps.setInt(3, idParking);
                Log.log.info("Query=> {}", ps.toString());
                ResultSet rs = ps.executeQuery();
                

                int idSensor = idTipo;

                if (rs.next()) {
                    do {
                        Measurement humedad = new Measurement();
                        humedad.setSensor(idSensor);
                        humedad.setValue(rs.getDouble("MEDIA_VALOR"));
                        humedad.setTimestamp(rs.getTimestamp("FECHA"));
                        humedades.add(humedad);
                    } while (rs.next());

                    // Calcular la media solo si hay al menos una medición
                } else {
                    Log.log.warn("El conjunto de resultados está vacío.");
                }

            } catch (SQLException e) {
                Log.log.error("Error: {}", e);
            } catch (NullPointerException e) {
                Log.log.error("Error: {}", e);
            } catch (Exception e) {
                Log.log.error("Error: {}", e);
            } finally {
                conector.closeConnection(con);
            }

            return humedades;
        }

	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return Temperature, Humidity and Gas concentration of the parking
	 */
	public static ArrayList<Measurement> getMonthAlarmsFromParking(int idCiudad, int idParking)
	{
		ArrayList<Measurement> alarmas = new ArrayList<Measurement>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetMonthAlarmsFromParking(con);
			ps.setInt(1, idCiudad);
			ps.setInt(2, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Measurement alarma = new Measurement();
				alarma.setSensor(rs.getInt("ID_SENSOR"));
				alarma.setValue(rs.getDouble("VALOR"));
				alarma.setTimestamp(rs.getTimestamp("FECHA"));
				alarma.setAlerta(rs.getBoolean("ALERTA"));
				alarmas.add(alarma);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			alarmas = new ArrayList<Measurement>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			alarmas = new ArrayList<Measurement>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			alarmas = new ArrayList<Measurement>();
		} finally
		{
			conector.closeConnection(con);
		}
		return alarmas;
	}

	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return History of the parking
	 */
	public static ArrayList<CarHistory> getMonthCarHistoryFromParking(int idCiudad, int idParking)
	{
		ArrayList<CarHistory> registros = new ArrayList<CarHistory>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetMonthCarHistoryFromParking(con);
			ps.setInt(1, idCiudad);
			ps.setInt(2, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				CarHistory registro = new CarHistory();
				registro.setParking(idParking);
				registro.setMatricula(rs.getString("MATRICULA"));
				registro.setTimestamp(rs.getTimestamp("FECHA"));
				registro.setEntrada(rs.getBoolean("ENTRADA"));
				registros.add(registro);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<CarHistory>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<CarHistory>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			registros = new ArrayList<CarHistory>();
		} finally
		{
			conector.closeConnection(con);
		}
		return registros;
	}

	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return History of the parking
	 */
        
	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return History of the parking
	 */
        
	public static int getEmptyPlacesFromParking(int idCiudad, int idParking)
	{	
                int plazasLibres = 0;
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetEmptyPlacesFromParking(con);
			ps.setInt(1, idCiudad);
			ps.setInt(2, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			plazasLibres = rs.getInt("PLAZAS_DISPONIBLES");	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
		} finally
		{
			conector.closeConnection(con);
		}
		return plazasLibres;
	}
        
    public static ArrayList<Measurement> getActualTempFromParking(int idCiudad, int idParking, int idTipo)
	{
		ArrayList<Measurement> temperaturas = new ArrayList<Measurement>();

		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetActualTempFromParking(con);
			ps.setInt(1, idTipo);
			ps.setInt(2, idCiudad);
			ps.setInt(3, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Measurement temp = new Measurement();
				temp.setSensor(rs.getInt("ID_SENSOR"));
				temp.setValue(rs.getDouble("VALOR"));
				temp.setTimestamp(rs.getTimestamp("FECHA"));
				temp.setAlerta(rs.getBoolean("ALERTA"));
				temperaturas.add(temp);
			}
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			temperaturas = new ArrayList<Measurement>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			temperaturas = new ArrayList<Measurement>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			temperaturas = new ArrayList<Measurement>();
		} finally
		{
			conector.closeConnection(con);
		}
		return temperaturas;
	}
        
    public static ArrayList<Measurement> getActualGasesFromParking(int idCiudad, int idParking, int idTipo)
	{
		ArrayList<Measurement> gases = new ArrayList<Measurement>();

		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetActualGasesFromParking(con);
			ps.setInt(1, idTipo);
			ps.setInt(2, idCiudad);
			ps.setInt(3, idParking);
			
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Measurement gas = new Measurement();
				gas.setSensor(rs.getInt("ID_SENSOR"));
				gas.setValue(rs.getDouble("VALOR"));
				gas.setTimestamp(rs.getTimestamp("FECHA"));
				gas.setAlerta(rs.getBoolean("ALERTA"));
				gases.add(gas);
			}
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			gases = new ArrayList<Measurement>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			gases = new ArrayList<Measurement>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			gases = new ArrayList<Measurement>();
		} finally
		{
			conector.closeConnection(con);
		}
		return gases;
	}
        
        public static ArrayList<CarHistory> getActualCarHistoryFromParking(int idCiudad, int idParking)
	{
		ArrayList<CarHistory> registros = new ArrayList<CarHistory>();

		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetActualCarHistoryFromParking(con);
			ps.setInt(1, idCiudad);
			ps.setInt(2, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				CarHistory registro = new CarHistory();
				registro.setParking(idParking);
				registro.setMatricula(rs.getString("MATRICULA"));
				registro.setTimestamp(rs.getTimestamp("FECHA"));
				registro.setEntrada(rs.getBoolean("ENTRADA"));
				registros.add(registro);
			}
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<CarHistory>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<CarHistory>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			registros = new ArrayList<CarHistory>();
		} finally
		{
			conector.closeConnection(con);
		}
		return registros;
	}

        public static void storeNewMedicion(Topics newTopic)
    	{
    		ConectionDDBB conector = new ConectionDDBB();
    		Connection con = null;
    		try
    		{
    			con = conector.obtainConnection(true);
    			Log.log.debug("Database Connected");
    			
    			PreparedStatement ps = ConectionDDBB.InsertMedicion(con);
    			ps.setString(1, newTopic.getIdSensor());
    	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    			ps.setString(2, sdf.format(timestamp));
    			ps.setString(3, newTopic.getValue());
    			ps.setString(4, newTopic.getIdAlert());
    			ps.setString(5, newTopic.getIdSensor());
    			ps.setString(6, sdf.format(timestamp));
    			ps.setString(7, newTopic.getValue());
    			ps.setString(8, newTopic.getIdAlert());
    			Log.log.info("Query to store Measurement=> {}", ps.toString());
    			ps.executeUpdate();
    		} catch (SQLException e)
    		{
    			Log.log.error("Error: {}", e);
    		} catch (NullPointerException e)
    		{
    			Log.log.error("Error: {}", e);
    		} catch (Exception e)
    		{
    			Log.log.error("Error:{}", e);
    		} finally
    		{
    			conector.closeConnection(con);
    		}
    	}
}

