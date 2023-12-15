package logic;

import java.util.ArrayList;

import db.ChartMeasurements;
import db.City;
import db.ConectionDDBB;
import db.SensorType;
import db.Sensor;
import db.CarHistory;
import db.Parking;
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
				clima.setMeasurement(rs.getDouble("Measurement"));
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
	public static ArrayList<Measurement> getMonthTempFromParking(int idCiudad, int idParking, int idTipo)
	{
		ArrayList<Measurement> temperaturas = new ArrayList<Measurement>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetMonthTempFromParking(con);
			ps.setInt(1, idCiudad);
			ps.setInt(2, idParking);
			ps.setInt(3, idTipo);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Measurement temp = new Measurement();
				temp.setSensor(rs.getInt("ID_SENSOR"))
				temp.setValue(rs.getDouble("VALOR"));
				temp.setTimestamp(rs.getDate("FECHA"));
				temp.setAlerta(rs.getInt("ALERTA"));
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

	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return Temperature, Humidity and Gas concentration of the parking
	 */
	public static ArrayList<Measurement> getMonthGasesFromParking(int idCiudad, int idParking, int idTipo)
	{
		ArrayList<Measurement> gases = new ArrayList<Measurement>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetMonthGasesFromParking(con);
			ps.setInt(1, idCiudad);
			ps.setInt(2, idParking);
			ps.setInt(3, idTipo);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Measurement gas = new Measurement();
				gas.setSensor(rs.getInt("ID_SENSOR"))
				gas.setValue(rs.getDouble("VALOR"));
				gas.setTimestamp(rs.getDate("FECHA"));
				gas.setAlerta(rs.getInt("ALERTA"))
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

	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return Temperature, Humidity and Gas concentration of the parking
	 */
	public static ArrayList<Measurement> getMonthAlarmsFromParking(int idCiudad, int idParking, int idTipo)
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
				alarma.setSensor(rs.getInt("ID_SENSOR"))
				alarma.setValue(rs.getDouble("VALOR"));
				alarma.setTimestamp(rs.getDate("FECHA"));
				alarma.setAlerta(rs.getInt("ALERTA"))
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
				registro.setTimestamp(rs.getDate("FECHA"));
				registro.setEntrada(rs.getInt("ENTRADA"));
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
	public static ArrayList<CarHistory> getParkingTimeDayFromParking(int idCiudad, int idParking)
	{
		ArrayList<CarHistory> registros = new ArrayList<CarHistory>();
		ArrayList<Map<long,CarHistory>> registrosFinales = new ArrayList<Map<long,CarHistory>>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetParkingTimeDayFromParking(con);
			ps.setInt(1, idCiudad);
			ps.setInt(2, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				String matricula = rs.getString("MATRICULA");
				Date fecha = rs.getDate("FECHA");
				int entrada = rs.getInt("ENTRADA");

				// Buscar si ya existe un registro para esta matrícula en la lista
            	CarHistory registroExistente = buscarRegistro(matricula, registros);

				if(registroExistente != null){
					Map<long,CarHistory> registroTiempo = new HashMap<>();
					long difMiliseg = fecha.getTime() - registroExistente.getFecha().getTime();
					if (entrada == 0) {
						registroTiempo.put("tiempo",difMiliseg);
						registroTiempo.put("registro",registroExistente);
						registrosFinales.add(registroTiempo);
					} else {
						registroTiempo.put("tiempo",-difMiliseg);
						registroTiempo.put("registro",registroExistente);
						registrosFinales.add(registroTiempo);
					}
				}

				CarHistory registro = new CarHistory();
				registro.setParking(idParking);
				registro.setMatricula(matricula);
				registro.setTimestamp(fecha);
				registro.setEntrada(entrada);
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
		return registrosFinales;
	}

	private static CarHistory buscarRegistro(String matricula, ArrayList<CarHistory> registros) {
        for (CarHistory registro : registros) {
            if (registro.getMatricula().equals(matricula)) {
                return registro;
            }
        }
        return null;
    }

	/**
	 * 
	 * @return History of the parkings
	
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
	/** */
}

