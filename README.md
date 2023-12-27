# Parking Inteligente con IoT y Java

Este proyecto implementa un sistema de parking inteligente que utiliza programación embebida (IoT) para recopilar información de sensores en un entorno de estacionamiento. La información recopilada se envía a través de MQTT a un servidor Java, que la procesa y almacena en una base de datos MariaDB. La interfaz web proporcionada por Apache Tomcat muestra estadísticas y datos en tiempo real.

## Estructura del Proyecto

- **Arduino**: Contiene el código para los dispositivos Arduino que gestionan los sensores, incluyendo un lector de matrículas con visión artificial. Los datos se envían al servidor a través de MQTT.

- **JavaServer**: Implementa el servidor Java que se suscribe a los temas de MQTT, procesa los datos y los almacena en una base de datos MariaDB.

- **WebApp**: Contiene la aplicación web implementada en Java y desplegada en Apache Tomcat. Permite visualizar estadísticas y datos en tiempo real.

## Requisitos del Sistema

- Arduino IDE para programar los dispositivos Arduino.
- Java SDK para compilar y ejecutar el servidor Java.
- Apache Tomcat para desplegar la aplicación web.

## Configuración

1. Configurar los dispositivos Arduino con el código proporcionado en la carpeta `Arduino`.
2. Configurar el servidor Java con los detalles de conexión a la base de datos y al broker MQTT en la carpeta `JavaServer`.
3. Desplegar la aplicación web en Apache Tomcat utilizando el archivo WAR generado en la carpeta `WebApp`.

## Uso

1. Iniciar los dispositivos Arduino en el entorno de estacionamiento.
2. Iniciar el servidor Java para suscribirse a los temas MQTT y gestionar los datos.
3. Acceder a la interfaz web desplegada en Apache Tomcat para visualizar estadísticas y datos en tiempo real.
