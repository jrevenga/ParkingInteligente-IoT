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
				String topicCity = "City" + rsCity.getInt("id_ciudad");
				topics.add("City" + rsCity.getInt("id_ciudad"));
				
				//Get parkings of the city
				PreparedStatement psParkings = ConectionDDBB.GetParkingsFromCity(con);
				psParkings.setInt(1, rsCity.getInt("id_ciudad"));
				Log.logmqtt.debug("Query to search parkings=> {}", psParkings.toString());
				ResultSet rsParkings = psParkings.executeQuery();
				while (rsParkings.next()){
					String topicParking = topicCity + "/Parking" + rsParkings.getInt("id_parking");
					topics.add(topicParking);
					
					//Get sensors form parking
					PreparedStatement psSensors = ConectionDDBB.GetParkingSensors(con);
					psSensors.setInt(1, rsParkings.getInt("id_parking"));
					Log.logmqtt.debug("Query to search sensors=> {}", psSensors.toString());
					ResultSet rsSensors = psSensors.executeQuery();
					while (rsSensors.next()){
						String topicSensor = topicParking + "/Sensor" + rsSensors.getInt("id_sensor");
						topics.add(topicSensor);
					}
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

	public void messageArrived(String topic, MqttMessage message) throws Exception 
	{
       Log.logmqtt.info("{}: {}", topic, message.toString());
       String[] topics = topic.split("/");
       Topics newTopic = new Topics();
       newTopic.setValue(message.toString());
       if(topic.contains("Sensor"))
       {
		   newTopic.setIdCity(topics[0].replace("City", ""));
		   newTopic.setIdParking(topics[1].replace("Parking", ""));
		   newTopic.setIdSensor(topics[2].replace("Sensor", ""));
    	   Log.logmqtt.info("Mensaje from city{}, parking{} sensor{}: {}", 
    			   newTopic.getIdCity(), newTopic.getIdParking(), newTopic.getIdSensor(), message.toString());
    	   
    	   //Store the information of the sensor
    	   Logic.storeNewMedicion(newTopic);
       }else
       {
    	   if(topic.contains("Parking"))
    	   {
    		   newTopic.setIdCity(topics[0].replace("City", ""));
    		   newTopic.setIdParking(topics[1].replace("Parking", ""));
        	   Log.logmqtt.info("Mensaje from city{}, parking{}: {}", 
        			   newTopic.getIdCity(), newTopic.getIdParking(), message.toString());
    	   }else
    	   {
    		   if(topic.contains("City"))
        	   {
    			   newTopic.setIdCity(topics[0].replace("City", ""));
    	    	   Log.logmqtt.info("Mensaje from city{}: {}", 
    	    			   newTopic.getIdCity(), message.toString());
        	   }else
        	   {
        		   
        	   }
    	   }
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