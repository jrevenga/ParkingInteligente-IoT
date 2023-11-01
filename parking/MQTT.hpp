#include "variables.h"

WiFiClient espClient;
PubSubClient mqttClient(espClient);

void SuscribeMqtt() {
  //mqttClient.subscribe("parking/#");
  mqttClient.subscribe("parking/plazas/plazasLibres");
  mqttClient.subscribe("parking/temperatura");
  mqttClient.subscribe("parking/humedad");
  mqttClient.subscribe("parking/humo");
}

String content = "";
String nPlazas, temp, hum;
void OnMqttReceived(char* topic, byte* payload, unsigned int length) {
  /*
  Serial.print("Received on ");
  Serial.print(topic);
  Serial.print(": ");
  content = "";
  for (size_t i = 0; i < length; i++) {
    content.concat((char)payload[i]);
  }
  Serial.print(content);
  Serial.println(); 
  */

  // Verifica si el mensaje se recibe en el tópico "parking/humo" 
  if (strcmp(topic, "parking/humo") == 0) {
    String message = "";
    for (int i = 0; i < length; i++) {
      message += (char)payload[i];
    }
    // Se verifica si el mensaje es "alarma"
    if (message == "alarma") {
      // Ejecuta la secuencia de tonos y pausas
      tone(pinZumbador, 800, 0);
      delay(600);
      noTone(pinZumbador);
      delay(500);
      tone(pinZumbador, 800, 0);
      delay(600);
      noTone(pinZumbador);
      delay(500);
      tone(pinZumbador, 800, 0);
      delay(1200);
      noTone(pinZumbador);
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("ALARMA");
      lcd.setCursor(0, 1);
      lcd.print("Evacuar edificio");
    }
  }

  // Mostrar el estado del estacionamiento en la pantalla LCD
  if (strcmp(topic, "parking/plazas/plazasLibres") == 0) {
    nPlazas = "";
    for (size_t i = 0; i < length; i++) {
      nPlazas += (char)payload[i];
    }
    lcd.clear();
    lcd.setCursor(0, 0);
    if (nPlazas.toInt() == 0) {
      lcd.print("Ocupado");
    } else {
      lcd.print("Libre  Plazas:" + nPlazas);
    }
  }

  if (strcmp(topic, "parking/temperatura") == 0) {
    temp = "";
    for (size_t i = 0; i < length; i++) {
      temp += (char)payload[i];
    }
    lcd.setCursor(0, 1);
    lcd.print(temp + "C");
  }

  if (strcmp(topic, "parking/humedad") == 0) {
    hum = "";
    for (size_t i = 0; i < length; i++) {
      hum += (char)payload[i];
    }
    lcd.setCursor(6, 1);
    lcd.print("Hum:" + hum + "%");
  }


}