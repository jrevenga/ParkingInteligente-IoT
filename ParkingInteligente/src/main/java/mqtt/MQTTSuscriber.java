package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import db.ConectionDDBB;
import db.Topics;
import logic.Log;
import logic.Logic;

public class MQTTSuscriber implements MqttCallback
{
	public void searchTopicsToSuscribe(MQTTBroker broker){
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		ArrayList<String> topics = new ArrayList<>();
		try{
			con = conector.obtainConnection(true);
			Log.logmqtt.debug("Database Connected");
			
			//Get Cities to search the main topic
			PreparedStatement psCity = ConectionDDBB.GetCities(con);
			Log.logmqtt.debug("Query to search cities=> {}", psCity.toString());
			ResultSet rsCity = psCity.executeQuery();
			while (rsCity.next()){
				String topicCity = "Ciudad" + rsCity.getInt("id_ciudad");
				topics.add("Ciudad" + rsCity.getInt("id_ciudad"));
				
				//Get parkings of the city
				PreparedStatement psParkings = ConectionDDBB.GetParkingsFromCity(con);
				psParkings.setInt(1, rsCity.getInt("id_ciudad"));
				Log.logmqtt.debug("Query to search parkings=> {}", psParkings.toString());
				ResultSet rsParkings = psParkings.executeQuery();
				while (rsParkings.next()){
					String topicParking = topicCity + "/Parking" + rsParkings.getInt("id_parking");
					topics.add(topicParking);
					
					//Get sensors from parking
					PreparedStatement psSensors = ConectionDDBB.GetParkingSensors(con);
					psSensors.setInt(1, rsParkings.getInt("id_parking"));
					Log.logmqtt.debug("Query to search sensors=> {}", psSensors.toString());
					ResultSet rsSensors = psSensors.executeQuery();
					while (rsSensors.next()){
						String topicSensor = topicParking + "/Sensor" + rsSensors.getInt("id_sensor");
						topics.add(topicSensor);
					}
					
					//Get plazas from parking
					PreparedStatement psPlazas = ConectionDDBB.GetParkingPlazas(con);
					psPlazas.setInt(1, rsParkings.getInt("id_parking"));
					Log.logmqtt.debug("Query to search plazas=> {}", psPlazas.toString());
					ResultSet rsPlazas = psPlazas.executeQuery();
					while (rsPlazas.next()){
					    int nPlaza = rsPlazas.getInt("plazas_totales");
					    for (int n = 1; n <= nPlaza; n++) {
						    String topicPlaza = topicParking + "/Plaza" + n;
						    topics.add(topicPlaza);
						}
					}
					
					//Get plazasLibres from parking
					PreparedStatement psPlazasLibres = ConectionDDBB.GetParkingPlazasLibres(con);
					psPlazasLibres.setInt(1, rsParkings.getInt("id_parking"));
					Log.logmqtt.debug("Query to search plazasLibres=> {}", psPlazasLibres.toString());
					String topic = topicParking + "/PlazasLibres";
					topics.add(topic);
					String topic2 = topicParking + "/Entrada";
					topics.add(topic2);
					String topic3 = topicParking + "/Salida";
					topics.add(topic3);
					
				}
			}	
			suscribeTopic(broker, topics);			
		} catch (SQLException e){Log.logmqtt.error("Error: {}", e);} 
		catch (NullPointerException e){Log.logmqtt.error("Error: {}", e);} 
		catch (Exception e){Log.logmqtt.error("Error:{}", e);} 
		finally{conector.closeConnection(con);}
	}
	
	public void suscribeTopic(MQTTBroker broker, ArrayList<String> topics)
	{
		Log.logmqtt.debug("Suscribe to topics");
        MemoryPersistence persistence = new MemoryPersistence();
        try
        {
            MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            //usuario y contraseña
            connOpts.setUserName(MQTTBroker.getUsername());
            connOpts.setPassword(MQTTBroker.getPassword().toCharArray());
            
            connOpts.setCleanSession(true);
            Log.logmqtt.debug("Mqtt Connecting to broker: " + MQTTBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.debug("Mqtt Connected");
            sampleClient.setCallback(this);
            for (int i = 0; i <topics.size(); i++) 
            {
                sampleClient.subscribe(topics.get(i));
                Log.logmqtt.info("Subscribed to {}", topics.get(i));
			}
            
        } catch (MqttException me) {
            Log.logmqtt.error("Error suscribing topic: {}", me);
        } catch (Exception e) {
            Log.logmqtt.error("Error suscribing topic: {}", e);
        }
	}
	
	public void connectionLost(Throwable cause) {
        Log.logmqtt.error("Connection lost: {}", cause.getMessage());
    }
	
	public void messageArrived(String topic, MqttMessage message) throws Exception {
	    Log.logmqtt.info("{}: {}", topic, message.toString());
	    String[] topics = topic.split("/");
	    Topics newTopic = new Topics();
	    
	    if (topic.contains("Sensor")) {
	    	newTopic.setValue(message.toString());
	        newTopic.setIdCity(topics[0].replace("Ciudad", ""));
	        newTopic.setIdParking(topics[1].replace("Parking", ""));
	        newTopic.setIdSensor(topics[2].replace("Sensor", ""));
	        Log.logmqtt.info("Mensaje from ciudad{}, parking{} sensor{}: {}",
	                newTopic.getIdCity(), newTopic.getIdParking(), newTopic.getIdSensor(), message.toString());
	        // Store the information of the sensor
	        Logic.storeNewMedicion(newTopic);
	        
	    } else if (topic.contains("PlazaLibres")) {
	    	newTopic.setPlazasLibres(message.toString());
	    	newTopic.setIdCity(topics[0].replace("Ciudad", ""));
	        newTopic.setIdParking(topics[1].replace("Parking", ""));
	        newTopic.setIdSensor(topics[2].replace("PlazasLibres", ""));
	        Log.logmqtt.info("Mensaje from ciudad{}, parking{}, PlazasLibres: {}",
	                newTopic.getIdCity(), newTopic.getIdParking(), newTopic.getPlazasLibres(), message.toString());
	        // Store the information of plazas libres
	        Logic.storeNewPlazasLibres(newTopic);
	        
	    } else if (topic.contains("Entrada")) {
	    	newTopic.setMatricula(message.toString());
	    	newTopic.setEntrada("1");
	    	newTopic.setIdCity(topics[0].replace("Ciudad", ""));
	        newTopic.setIdParking(topics[1].replace("Parking", ""));
	        newTopic.setIdSensor(topics[2].replace("Entrada", ""));
	        Log.logmqtt.info("Mensaje from ciudad{}, parking{} Entrada{}: {}",
	                newTopic.getIdCity(), newTopic.getIdParking(), newTopic.getEntrada(), message.toString());
	        // Store the information of historico_coches
	        Logic.storeNewHistoricoCoches(newTopic);
	        
	    } else if (topic.contains("Salida")) {
	    	newTopic.setMatricula(message.toString());
	    	newTopic.setEntrada("0");
	    	newTopic.setIdCity(topics[0].replace("Ciudad", ""));
	        newTopic.setIdParking(topics[1].replace("Parking", ""));
	        newTopic.setIdSensor(topics[2].replace("Salida", ""));
	        Log.logmqtt.info("Mensaje from ciudad{}, parking{} Salida{}: {}",
	                newTopic.getIdCity(), newTopic.getIdParking(), newTopic.getSalida(), message.toString());
	        // Store the information of historico_coches
	        Logic.storeNewHistoricoCoches(newTopic);
	        
	    } else if (topic.contains("Parking")) {
	        newTopic.setIdCity(topics[0].replace("Ciudad", ""));
	        newTopic.setIdParking(topics[1].replace("Parking", ""));
	        Log.logmqtt.info("Mensaje from ciudad{}, parking{}: {}",
	                newTopic.getIdCity(), newTopic.getIdParking(), message.toString());
	        
	    } else if (topic.contains("Ciudad")) {
	        newTopic.setIdCity(topics[0].replace("Ciudad", ""));
	        Log.logmqtt.info("Mensaje from city{}: {}",
	                newTopic.getIdCity(), message.toString());
	    } else {
	    }
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	    try {
	        Log.logmqtt.debug("Delivery complete. Message ID: {}", token.getMessageId());
	    } catch (Exception e) {
	        Log.logmqtt.error("Error in deliveryComplete: {}", e.getMessage());
	    }
	}

}