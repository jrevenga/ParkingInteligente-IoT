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
                Log.log.debug("Database Connected 1");

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


        private static ArrayList<Measurement> calcularMedia(ArrayList<Measurement> valores, int idSensor){
            Map<Timestamp, Double> sumas = new HashMap<>();
            Map<Timestamp, Integer> cuentas = new HashMap<>();

            for (Measurement valor : valores) {
                sumas.put(valor.getTimestamp(), sumas.getOrDefault(valor.getTimestamp(), 0.0) + valor.getValue());
                cuentas.put(valor.getTimestamp(), cuentas.getOrDefault(valor.getTimestamp(), 0) + 1);
            }

            // Paso 3 y 4: Calcular la media y crear nuevos objetos
            ArrayList<Measurement> resultado = new ArrayList<>();
            for (Timestamp fecha : sumas.keySet()) {
                double media = sumas.get(fecha) / cuentas.get(fecha);
                resultado.add(new Measurement(idSensor,fecha,media, false));
            }
            return resultado;
        }
	/**
	 * 
	 * @param idParking ID of the parking to search
	 * @return Temperature, Humidity and Gas concentration of the parking
	 */
	public static ArrayList<Measurement> getMonthGasesFromParking(int idCiudad, int idParking, int idTipo)
	{
		ArrayList<Measurement> gases = new ArrayList<Measurement>();
		ArrayList<Measurement> gasesMedias = new ArrayList<Measurement>();
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetMonthGasesFromParking(con);
			ps.setInt(1, idTipo);
			ps.setInt(2, idCiudad);
			ps.setInt(3, idParking);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
                        int idSensor = rs.getInt("ID_SENSOR");
			while (rs.next())
			{
				Measurement gas = new Measurement();
				gas.setSensor(rs.getInt("ID_SENSOR"));
				gas.setValue(rs.getDouble("VALOR"));
				gas.setTimestamp(rs.getTimestamp("FECHA"));
				gas.setAlerta(rs.getBoolean("ALERTA"));
				gases.add(gas);
			}
                        gasesMedias = calcularMedia(gases,idSensor);
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
		return gasesMedias;
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
        
	public static ArrayList<Map<Long,CarHistory>> getParkingTimeDayFromParking(int idCiudad, int idParking)
	{
		ArrayList<CarHistory> registros = new ArrayList<CarHistory>();
		ArrayList<Map<Long,CarHistory>> registrosFinales = new ArrayList<>();
		
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
				Timestamp fecha = rs.getTimestamp("TIMESTAMP");
				boolean entrada = rs.getBoolean("ENTRADA");

				// Buscar si ya existe un registro para esta matrícula en la lista
                                CarHistory registroExistente = buscarRegistro(matricula, registros);

				if(registroExistente != null){
					Map<Long,CarHistory> registroTiempo = new HashMap<>();
					long difMiliseg = fecha.getTime() - registroExistente.getTimestamp().getTime();
					if (entrada == false) {
						registroTiempo.put(difMiliseg,registroExistente);
						registrosFinales.add(registroTiempo);
					} else {
						registroTiempo.put(-difMiliseg,registroExistente);
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

