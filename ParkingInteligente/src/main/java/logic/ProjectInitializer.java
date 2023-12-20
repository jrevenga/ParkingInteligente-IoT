package logic;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import mqtt.MQTTBroker;
import mqtt.MQTTSuscriber;

/**
 *	ES: Clase encargada de inicializar el sistema
 */
@WebListener
public class ProjectInitializer implements ServletContextListener
{
	
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
	
	@Override
	/**
	 *	ES: Metodo empleado para detectar la inicializacion del servidor	<br>
	 * 	@param sce <br>
	 * 	ES: Evento de contexto creado durante el arranque del servidor	<br>
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		Log.log.info("-->Suscribe Topics<--");
		MQTTBroker broker = new MQTTBroker();
		MQTTSuscriber suscriber = new MQTTSuscriber();
		suscriber.searchTopicsToSuscribe(broker);
		
	}	
}