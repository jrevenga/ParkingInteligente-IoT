#include "variables.h"

WiFiClient espClient;
PubSubClient mqttClient(espClient);

void SuscribeMqtt() {
  mqttClient.subscribe("Ciudad1/Parking1/#");
}

String content = "";
String message, alarma, nPlazas, temp, hum;
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
  if (strcmp(topic, "parking/matricula/entrada") == 0) {
    message = "";
    for (int i = 0; i < length; i++) {
      message += (char)payload[i];
    }
    // Abre la barrera
      lcd.clear();
      lcd.print("Bienvenido");
      servo.write(90);
      delay(4000);
      while(digitalRead(pinSensorBarrera) == LOW){
        delay(1000);
      }
      servo.write(0);
      delay(1000);
  }

  if (strcmp(topic, "parking/matricula/salida") == 0) {
    message = "";
    for (int i = 0; i < length; i++) {
      message += (char)payload[i];
    }
    // Abre la barrera
      lcd.clear();
      lcd.print("Hasta pronto");
      servo.write(90);
      delay(4000);
      while(digitalRead(pinSensorBarrera) == LOW){
        delay(1000);
      }
      servo.write(0);
      delay(1000);
  }

  if (strcmp(topic, "parking/humo") == 0) {
    alarma = "";
    for (int i = 0; i < length; i++) {
      alarma += (char)payload[i];
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
  if (alarma == "on") {
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
    alarma = "off";
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