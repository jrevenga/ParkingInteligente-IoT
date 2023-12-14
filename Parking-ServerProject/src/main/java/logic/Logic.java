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
	public static ArrayList<Plaza> getPlazasFromParking(int idParking)
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
	public static ArrayList<Evento> getEventosFromPlaza(int idPlaza)
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
}
