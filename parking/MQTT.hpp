#include "variables.h"

WiFiClient espClient;
PubSubClient mqttClient(espClient);

void SuscribeMqtt() {
  //mqttClient.subscribe("parking/#");
  mqttClient.subscribe("parking/matricula");
  mqttClient.subscribe("parking/plazasLibres");
  mqttClient.subscribe("parking/temperatura");
  mqttClient.subscribe("parking/humedad");
  mqttClient.subscribe("parking/humo");
}

String content = "";
String message, nPlazas, temp, hum;
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
  if (strcmp(topic, "parking/humo") == 0) {
    message = "";
    for (int i = 0; i < length; i++) {
      message += (char)payload[i];
    }
  }

  if (strcmp(topic, "parking/plazasLibres") == 0) {
    nPlazas = "";
    for (size_t i = 0; i < length; i++) {
      nPlazas += (char)payload[i];
    }
  }

  if (strcmp(topic, "parking/temperatura") == 0) {
    temp = "";
    for (size_t i = 0; i < length; i++) {
      temp += (char)payload[i];
    }
  }

  if (strcmp(topic, "parking/humedad") == 0) {
    hum = "";
    for (size_t i = 0; i < length; i++) {
      hum += (char)payload[i];
    }
  }

  // Mostrar datos en la pantalla LCD
  if (message == "on") {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("ALARMA");
    lcd.setCursor(0, 1);
    lcd.print("Evacuar edificio");
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
    message = "off";
  } else {
    lcd.clear();
    lcd.setCursor(0, 0);
    if (nPlazas.toInt() == 0) {
      lcd.print("Ocupado");
    } else {
      lcd.print("Libre  Plazas:" + nPlazas);
    }
    lcd.setCursor(0, 1);
    lcd.print(temp + "C" + "  Hum:" + hum + "%");
  }

}