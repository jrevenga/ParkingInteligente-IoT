#include <WiFi.h>
#include <PubSubClient.h>
#include <LiquidCrystal.h>
#include <DHT.h>
#include <ESP32Servo.h>

#include "config.h" //Configuraton file
#include "MQTT.hpp" //MQTT functions
#include "ESP8266_Utils.hpp" //Wifi connection
#include "ESP8266_Utils_MQTT.hpp" //MQTT Connection

int smokeValue = 0;

void setup(void) {
  Serial.begin(115200);
  ConnectWiFi();
  InitMqtt();
  lcd.begin(16, 2);  // Inicializa la pantalla LCD con 16 columnas y 2 filas
  lcd.clear();      // Limpia la pantalla
  lcd.print("Iniciando");  // Escribe un mensaje inicial en la pantalla
  dht.begin();
  pinMode(trigPin1, OUTPUT);
  pinMode(echoPin1, INPUT);
  pinMode(trigPin2, OUTPUT);
  pinMode(echoPin2, INPUT);
  pinMode(trigPin3, OUTPUT);
  pinMode(echoPin3, INPUT);
  pinMode(redLedPin1, OUTPUT);
  pinMode(greenLedPin1, OUTPUT);
  pinMode(redLedPin2, OUTPUT);
  pinMode(greenLedPin2, OUTPUT);
  pinMode(redLedPin3, OUTPUT);
  pinMode(greenLedPin3, OUTPUT);
  pinMode(pinSensorBarrera, INPUT);
  servo.attach(pinServo);
  servo.write(0);   // Asegura que la barrera comience en posición cerrada
}

void loop() {

  HandleMqtt();
  
  if (Serial.available() > 0) {
    String input = Serial.readStringUntil('\n');  // Lee la entrada completa desde la comunicación serial

    // Divide la entrada en matrícula y dirección
    int spaceIndex = input.indexOf(',');
    if (spaceIndex != -1) {
        String matricula = input.substring(0, spaceIndex);
        String direccion = input.substring(spaceIndex + 1);
        if (matricula.length() == 8) {
            if (direccion == "entrada") {
              if (plazasLibres > 0 && smokeValue < 2800){
                plazasLibres --;
                mqttClient.publish("Ciudad1/Parking1/PlazasLibres", String(plazasLibres).c_str());
                mqttClient.publish("Ciudad1/Parking1/Entrada", matricula.c_str());
              }
            } else if (direccion == "salida") {
              if (plazasLibres < capacidad){
                plazasLibres ++;
                mqttClient.publish("Ciudad1/Parking1/PlazasLibres", String(plazasLibres).c_str());
              }
              mqttClient.publish("Ciudad1/Parking1/Salida", matricula.c_str());
            }
        }
    }
  }

  //Calculo distancia sensor 1
  digitalWrite(trigPin1, HIGH);
  delay(1);
  digitalWrite(trigPin1, LOW);
  int duracion1 = pulseIn(echoPin1, HIGH);  //Recibe el puslo
  int distancia1 = duracion1 / 58.2;    //Calculo para hallar la distancia en cm

  // Verificar si hay un objeto cerca del sensor 1
  if (distancia1 < 10) {
    msg1 = "ocupado";
    digitalWrite(redLedPin1, HIGH);
    digitalWrite(greenLedPin1, LOW);
    if (distancia1 < 5) {
      // Si está demasiado cerca, activa el zumbador
      tone(pinZumbador, 1000, 0);
    } else {
      // Si no está demasiado cerca, apaga el zumbador
      noTone(pinZumbador);
    }
  } else {
    msg1 = "libre";
    digitalWrite(redLedPin1, LOW);
    digitalWrite(greenLedPin1, HIGH);
    noTone(pinZumbador);
  }

  //Calculo distancia sensor 2
  digitalWrite(trigPin2, HIGH);
  delay(1);
  digitalWrite(trigPin2, LOW);
  int duracion2 = pulseIn(echoPin2, HIGH);
  int distancia2 = duracion2 / 58.2;

  // Verificar si hay un objeto cerca del sensor 2
  if (distancia2 < 10) {
    msg2 = "ocupado";
    digitalWrite(redLedPin2, HIGH);
    digitalWrite(greenLedPin2, LOW);
  } else {
    msg2 = "libre";
    digitalWrite(redLedPin2, LOW);
    digitalWrite(greenLedPin2, HIGH);
  }

  //Calculo distancia sensor 3
  digitalWrite(trigPin3, HIGH);
  delay(1);
  digitalWrite(trigPin3, LOW);
  int duracion3 = pulseIn(echoPin3, HIGH);
  int distancia3 = duracion3 / 58.2;

  // Verificar si hay un objeto cerca del sensor 3
  if (distancia3 < 10) {
    msg3 = "ocupado";
    digitalWrite(redLedPin3, HIGH);
    digitalWrite(greenLedPin3, LOW);
  } else {
    msg3 = "libre";
    digitalWrite(redLedPin3, LOW);
    digitalWrite(greenLedPin3, HIGH);
  }

  if (n % 20 == 0){
    //Subir el topic del estado del estacionamiento
    mqttClient.publish("Ciudad1/Parking1/Plaza1", msg1.c_str());
    mqttClient.publish("Ciudad1/Parking1/Plaza2", msg2.c_str());
    mqttClient.publish("Ciudad1/Parking1/Plaza3", msg3.c_str());
  }
  
  if (n % 40 == 0){
    //Medir temperatura, humedad y humo
    float temperatura = dht.readTemperature();
    mqttClient.publish("Ciudad1/Parking1/Sensor1", String(temperatura).c_str());

    int humedad = dht.readHumidity();
    mqttClient.publish("Ciudad1/Parking1/Sensor2", String(humedad).c_str());

    smokeValue = analogRead(smokeSensorPin);
    mqttClient.publish("Ciudad1/Parking1/Sensor3", String(smokeValue).c_str());
  }

  if (n == 0){
    mqttClient.publish("Ciudad1/Parking1/PlazasLibres", String(plazasLibres).c_str());
  }
  
  n++;
  delay(500);
}
