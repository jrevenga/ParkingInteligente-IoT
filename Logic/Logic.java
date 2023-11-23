package logic;

import java.util.ArrayList;

import db.ChartMeasurements;
import db.Ciudad;
import db.ConectionDDBB;
import db.SensorType;
import db.Station;
import db.Topics;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logic 
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * @return The list of all the parkings stored in the db
	 */
	public static ArrayList<Parking> getParkingsFromDB()
	{
		ArrayList<Parking> parkings = new ArrayList<Parking>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetParkings(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Parking parking = new Parking();
				parking.setId(rs.getInt("ID"));
				parking.setNombre(rs.getString("NOMBRE"));
				parking.setCiudad(rs.getString("CIUDAD"));
				parkings.add(parking);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			parkings = new ArrayList<Parking>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			parkings = new ArrayList<Parking>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			parkings = new ArrayList<Parking>();
		} finally
		{
			conector.closeConnection(con);
		}
		return parkings;
	}
	
	/**
	 * 
	 * @return The list of all the parkings stored in the db of a Ciudad
	 */
	public static ArrayList<Parking> getParkingsFromCiudad(int CiudadId)
	{
		ArrayList<Parking> parkings = new ArrayList<Parking>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetParkingsFromCiudad(con);
			ps.setInt(1, CiudadId);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Parking parking = new Parking();
				parking.setId(rs.getInt("ID"));
				parking.setNombre(rs.getString("NOMBRE"));
				parking.setCiudad(rs.getDouble("CIUDAD"));
				parkings.add(parking);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			parkings = new ArrayList<Parking>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			parkings = new ArrayList<Parking>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			parkings = new ArrayList<Parking>();
		} finally
		{
			conector.closeConnection(con);
		}
		return parkings;
	}
	
	/**
	 * 
	 * @return The list of all the ciudades stored in the db
	 */
	public static ArrayList<Ciudad> getCiudadesFromDB()
	{
		ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetCiudades(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Ciudad ciudad = new Ciudad();
				ciudad.setId(rs.getInt("ID"));
				ciudad.setNombre(rs.getString("NOMBRE"));
				ciudades.add(ciudad);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			ciudades = new ArrayList<Ciudad>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			ciudades = new ArrayList<Ciudad>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			ciudades = new ArrayList<Ciudad>();
		} finally
		{
			conector.closeConnection(con);
		}
		return ciudades;
	}
	
	/**
	 * 
	 * @return Temperature, Humidity and Gas concentration of the parkings
	 */
	public static ArrayList<Clima> getClimasFromDB()
	{
		ArrayList<Clima> climas = new ArrayList<Clima>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetClimaFromParking(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Clima clima = new Clima();
				clima.setIdParking(rs.getInt("IDPARKING"));
				clima.setTemperatura(rs.getDouble("TEMPERATURA"));
				clima.setHumedad(rs.getDouble("HUMEDAD"));
				clima.setHumoGas(rs.getDouble("HUMOGAS"));
				clima.setFecha(rs.getDate("FECHA"));
				climas.add(clima);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			parkings = new ArrayList<Parking>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			parkings = new ArrayList<Parking>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			parkings = new ArrayList<Parking>();
		} finally
		{
			conector.closeConnection(con);
		}
		return climas;
	}

	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return Temperature, Humidity and Gas concentration of the parking
	 */
	public static ArrayList<Clima> getClimasFromParking(int idParking)
	{
		ArrayList<Clima> climas = new ArrayList<Clima>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetClimaFromParking(con);
			ps.setInt(1, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Clima clima = new Clima();
				clima.setIdParking(idParking);
				clima.setTemperatura(rs.getDouble("TEMPERATURA"));
				clima.setHumedad(rs.getDouble("HUMEDAD"));
				clima.setHumoGas(rs.getDouble("HUMOGAS"));
				clima.setFecha(rs.getDate("FECHA"));
				climas.add(clima);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<Clima>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<Clima>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			climas = new ArrayList<Clima>();
		} finally
		{
			conector.closeConnection(con);
		}
		return climas;
	}

	/**
	 * 
	 * @return History of the parkings
	 */
	public static ArrayList<Registro> getRegistrosFromDB()
	{
		ArrayList<Registro> registros = new ArrayList<Registro>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetRegistrosFromDB(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Registro registro = new Registro();
				registro.setIdParking(rs.getInt("IDPARKING"));
				registro.setMatricula(rs.getString("MATRICULA"));
				registro.setFecha(rs.getDate("FECHA"));
				registro.setIdEvento(rs.getInt("IDEVENTO"));
				registros.add(registro);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<Registro>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<Registro>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			registros = new ArrayList<Registros>();
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
	public static ArrayList<Registro> getRegistrosFromParking(int idParking)
	{
		ArrayList<Registro> registros = new ArrayList<Registro>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetRegistrosFromDB(con);
			ps.setInt(1, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Registro registro = new Registro();
				registro.setIdParking(idParking);
				registro.setMatricula(rs.getString("MATRICULA"));
				registro.setFecha(rs.getDate("FECHA"));
				registro.setIdEvento(rs.getInt("IDEVENTO"));
				registros.add(registro);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<Registro>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			registros = new ArrayList<Registro>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			registros = new ArrayList<Registros>();
		} finally
		{
			conector.closeConnection(con);
		}
		return registros;
	}

	/**
	 * 
	 * @return History of the parkings
	 */
	public static ArrayList<Plaza> getPlazasFromDB()
	{
		ArrayList<Plaza> plazas = new ArrayList<Plaza>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetPlazasFromDB(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Plaza plaza = new Plaza();
				plaza.setIdParking(rs.getInt("IDPARKING"));
				plaza.setId(rs.getInt("ID"));
				plaza.setEstado(rs.getString("ESTADO"));
				plazas.add(plaza);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			plazas = new ArrayList<Plaza>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			plazas = new ArrayList<Plaza>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			plazas = new ArrayList<Plaza>();
		} finally
		{
			conector.closeConnection(con);
		}
		return plazas;
	}

	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return History of the parking
	 */
	public static double getPlazasFromParking(int idParking)
	{
		ArrayList<Plaza> plazas = new ArrayList<Plaza>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetPlazasFromParking(con);
			ps.setInt(1, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Plaza plaza = new Plaza();
				plaza.setIdParking(idParking);
				plaza.setId(rs.getInt("ID"));
				plaza.setEstado(rs.getString("ESTADO"));
				plazas.add(plaza);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			plazas = new ArrayList<Plaza>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			plazas = new ArrayList<Plaza>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			plazas = new ArrayList<Plaza>();
		} finally
		{
			conector.closeConnection(con);
		}
		return plazas;
	}

	/**
	 * 
	 * @return History of the parkings
	 */
	public static ArrayList<Evento> getEventosFromDB()
	{
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetEventosFromDB(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Evento evento = new Evento();
				evento.setId(rs.getInt("ID"));
				evento.setEstado(rs.getString("DESCRIPCION"));
				eventos.add(evento);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			eventos = new ArrayList<Evento>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			eventos = new ArrayList<Evento>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			eventos = new ArrayList<Evento>();
		} finally
		{
			conector.closeConnection(con);
		}
		return eventos;
	}

	/**
	 * 
	 * @param idPlaza ID of the place to search
	 * @return History of the parking
	 */
	public static double getEventosFromPlaza(int idPlaza)
	{
		ArrayList<Evento> eventos = new ArrayList<Evento>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetEventosFromPlaza(con);
			ps.setInt(1, idPlaza);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Evento evento = new Evento();
				evento.setId(rs.getInt("ID"));
				evento.setEstado(rs.getString("DESCRIPCION"));
				eventos.add(evento);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			eventos = new ArrayList<Evento>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			eventos = new ArrayList<Evento>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			eventos = new ArrayList<Evento>();
		} finally
		{
			conector.closeConnection(con);
		}
		return eventos;
	}

	/**
	 * 
	 * @param idStation ID of the station to search
	 * @return The list of sensors of a Installation
	 */
	public static ArrayList<SensorType> getStationSensorsFromDB(int idStation)
	{
		ArrayList<SensorType> sensors = new ArrayList<SensorType>();	
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensors(con);
			ps.setInt(1, idStation);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				SensorType sensor = new SensorType();
				sensor.setId(rs.getInt("ID"));
				sensor.setName(rs.getString("NAME"));
				sensor.setUnits(rs.getString("UNITS"));
				if(rs.getInt("STATION_ID")>0)
				{
					sensor.setAvailable(1);
					//EN:Search the last value of the station
					PreparedStatement ps_value = ConectionDDBB.GetLastValueStationSensor(con);
					ps_value.setInt(1, idStation);
					ps_value.setInt(2, rs.getInt("ID"));
					Log.log.info("Query=> {}", ps_value.toString());
					ResultSet rs_value = ps_value.executeQuery();
					if (rs_value.next())
					{
						sensor.setValue(rs_value.getInt("VALUE"));
						if(sensor.getValue()<rs.getInt("MINVALUE"))
						{
							sensor.setLabel(0); //Low value
						}else
						{
							if(sensor.getValue()>rs.getInt("MAXIVALUE"))
							{
								sensor.setLabel(2); //High value
							}else
							{
								sensor.setLabel(1);	 //Medium value
							}
						}
					}
				}else
				{
					sensor.setAvailable(0);
					//TODO buscar la media de la ciudad para dar un valor aproximado
				}
				sensors.add(sensor);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			sensors = new ArrayList<SensorType>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			sensors = new ArrayList<SensorType>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			sensors = new ArrayList<SensorType>();
		} finally
		{
			conector.closeConnection(con);
		}
		return sensors;
	}
	
	/**
	 * 
	 * @return The last week measurements
	 */
	public static ChartMeasurements getLastWeekStationSensorFromDB(int idStation, int sensorId)
	{
		ChartMeasurements measurement = new ChartMeasurements();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensorMeasurementLastDays(con);
			ps.setInt(1, idStation);
			ps.setInt(2, sensorId);
			ps.setInt(3, 7); //ES:Number of Days to search //ES:N�mero de d�as a buscar
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				measurement.getMinValues().add(rs.getInt("min"));
				measurement.getMaxValues().add(rs.getInt("max"));
				measurement.getAvgValues().add(rs.getInt("avg"));
				switch (rs.getInt("dayofweek")) {
					case 1:
						measurement.getLabels().add("Sunday " + rs.getString("date"));
					break;
					case 2:
						measurement.getLabels().add("Monday " + rs.getString("date"));
					break;
					case 3:
						measurement.getLabels().add("Tuesday " + rs.getString("date"));
					break;
					case 4:
						measurement.getLabels().add("Wednesday " + rs.getString("date"));
					break;
					case 5:
						measurement.getLabels().add("Thursday " + rs.getString("date"));
					break;
					case 6:
						measurement.getLabels().add("Friday " + rs.getString("date"));;
					break;
					case 7:
						measurement.getLabels().add("Saturday " + rs.getString("date"));
					break;
				}
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			measurement = new ChartMeasurements();	
		} finally
		{
			conector.closeConnection(con);
		}
		return measurement;
	}
	
	/**
	 * 
	 * @return The last week measurements
	 */
	public static ChartMeasurements getMonthStationSensorFromDB(int idStation, int sensorId)
	{
		ChartMeasurements measurement = new ChartMeasurements();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensorMeasurementMonth(con);
			ps.setInt(1, idStation);
			ps.setInt(2, sensorId);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				measurement.getMinValues().add(rs.getInt("min"));
				measurement.getMaxValues().add(rs.getInt("max"));
				measurement.getAvgValues().add(rs.getInt("avg"));
				switch (rs.getInt("date")) {
					case 1:
						measurement.getLabels().add("Jan");
					break;
					case 2:
						measurement.getLabels().add("Feb");
					break;
					case 3:
						measurement.getLabels().add("Mar");
					break;
					case 4:
						measurement.getLabels().add("Apr");
					break;
					case 5:
						measurement.getLabels().add("May");
					break;
					case 6:
						measurement.getLabels().add("Jun");
					break;
					case 7:
						measurement.getLabels().add("Jul");
					break;
					case 8:
						measurement.getLabels().add("Aug");
					break;
					case 9:
						measurement.getLabels().add("Sep");
					break;
					case 10:
						measurement.getLabels().add("Oct");
					break;
					case 11:
						measurement.getLabels().add("Nov");
					break;
					case 12:
						measurement.getLabels().add("Dec");
					break;
				}
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			measurement = new ChartMeasurements();	
		} finally
		{
			conector.closeConnection(con);
		}
		return measurement;
	}
	
	/**
	 * 	
	 * @param idStation
	 * @return Arraylist with the measurements
	 */
	public static String getWeatherForecast(int idStation)
	{
		String forecast  = "";
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetInfoFromStation(con);
			ps.setInt(1, idStation);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				String lat=rs.getString("LATITUDE");
				String lon=rs.getString("LONGITUDE");
				
				forecast = ThreadWeatherForecast.obtainWeatherString(lat, lon); 
				
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			forecast  = "";
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			forecast  = "";
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			forecast  = "";
		} finally
		{
			conector.closeConnection(con);
		}
		return forecast;
	}
	
	
	public static void storeNewMeasurement(Topics newTopic)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.InsertnewMeasurement(con);
			ps.setString(1, newTopic.getIdStation());
			ps.setString(2, newTopic.getIdSensor());
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			ps.setString(3, sdf.format(timestamp));
			ps.setString(4, newTopic.getValue());
			ps.setString(5, newTopic.getIdStation());
			ps.setString(6, newTopic.getIdSensor());
			ps.setString(7, sdf.format(timestamp));
			ps.setString(8, newTopic.getValue());
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
