#include <WiFi.h>
#include <PubSubClient.h>
#include <LiquidCrystal.h>
#include <DHT.h>
#include <ESP32Servo.h>

#include "config.h " // Configuraton file
#include "MQTT.hpp" //MQTT functions
#include "ESP8266_Utils.hpp" //Wifi connection
#include "ESP8266_Utils_MQTT.hpp" //MQTT Connection

#include "Alarma.hpp"

const int pinServo = 2; // Pin del servomotor
Servo servo;

const int echoPin1 = 39; // Pin de echo del sensor de distancia 1
const int trigPin1 = 12; // Pin de trig del sensor de distancia 1
const int echoPin2 = 35; // Pin de echo del sensor de distancia 2
const int trigPin2 = 25; // Pin de trig del sensor de distancia 2
const int echoPin3 = 34; // Pin de echo del sensor de distancia 3
const int trigPin3 = 23; // Pin de trig del sensor de distancia 3

const int redLedPin1 = 14; // Pin del LED rojo para el sensor 1
const int greenLedPin1 = 16; // Pin del LED verde para el sensor 1
const int redLedPin2 = 27; // Pin del LED rojo para el sensor 2
const int greenLedPin2 = 26; // Pin del LED verde para el sensor 2
const int redLedPin3 = 21; // Pin del LED rojo para el sensor 3
const int greenLedPin3 = 17; // Pin del LED verde para el sensor 3

const int dhtPin = 22; // Pin del sensor de temperatura
DHT dht(dhtPin, DHT11);

const int smokeSensorPin = 36; // Pin del sensor de humo MQ-2
LiquidCrystal lcd(13,32,4,5,18,19);

String msg;
bool parkingOcupado = false; // Variable para rastrear el estado del estacionamiento

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
  servo.attach(pinServo);
  servo.write(0);   // Asegura que la barrera comience en posición cerrada
}

void loop() {
  HandleMqtt();

  if (Serial.available() > 0) {
    String matricula = Serial.readStringUntil('\n');  // Lee la matrícula desde la comunicación serial
    if (matricula.length() == 8 && !parkingOcupado) {
      mqttClient.publish("esp32/matricula", (matricula).c_str());
      // Abre la barrera
      lcd.clear();
      lcd.print("Bienvenido");
      servo.write(90);
      delay(2000);
      servo.write(0);
      delay(1000);
    }
  }

  int plazasLibres = 3;

  //Calculo distancia sensor 1
  digitalWrite(trigPin1, HIGH);
  delay(1);
  digitalWrite(trigPin1, LOW);
  int duracion1 = pulseIn(echoPin1, HIGH);  //Recibe el puslo
  int distancia1 = duracion1 / 58.2;    //Calculo para hallar la distancia en cm

  // Verificar si hay un objeto cerca del sensor 1
  if (distancia1 < 10) {
    msg = "ocupado";
    mqttClient.publish("esp32/plaza1", msg.c_str());
    digitalWrite(redLedPin1, HIGH);
    digitalWrite(greenLedPin1, LOW);
    plazasLibres--;
    if (distancia1 < 5) {
      // Si está demasiado cerca, activa el zumbador
      tone(BUZZZER_PIN, 1000, 0);
    } else {
      // Si no está demasiado cerca, apaga el zumbador
      noTone(BUZZZER_PIN);
    }
  } else {
    msg = "libre";
    mqttClient.publish("esp32/plaza1", msg.c_str());
    digitalWrite(redLedPin1, LOW);
    digitalWrite(greenLedPin1, HIGH);
    noTone(BUZZZER_PIN);
  }

  //Calculo distancia sensor 2
  digitalWrite(trigPin2, HIGH);
  delay(1);
  digitalWrite(trigPin2, LOW);
  int duracion2 = pulseIn(echoPin2, HIGH);
  int distancia2 = duracion2 / 58.2;

  // Verificar si hay un objeto cerca del sensor 2
  if (distancia2 < 10) {
    msg = "ocupado";
    mqttClient.publish("esp32/plaza2", msg.c_str());
    digitalWrite(redLedPin2, HIGH);
    digitalWrite(greenLedPin2, LOW);
    plazasLibres--;
  } else {
    msg = "libre";
    mqttClient.publish("esp32/plaza2", msg.c_str());
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
    msg = "ocupado";
    mqttClient.publish("esp32/plaza3", msg.c_str());
    digitalWrite(redLedPin3, HIGH);
    digitalWrite(greenLedPin3, LOW);
    plazasLibres--;
  } else {
    msg = "libre";
    mqttClient.publish("esp32/plaza3", msg.c_str());
    digitalWrite(redLedPin3, LOW);
    digitalWrite(greenLedPin3, HIGH);
  }

  //Medir temperatura, humedad y humo
  float temperatura = dht.readTemperature();
  mqttClient.publish("esp32/temperatura", (String(temperatura)+ "°C").c_str());

  float humedad = dht.readHumidity();
  msg = String(humedad) + " %";
  mqttClient.publish("esp32/humedad", (String(humedad)+ "%").c_str());

  int smokeValue = analogRead(smokeSensorPin);
  msg = String(smokeValue);
  mqttClient.publish("esp32/humo", String(smokeValue).c_str());

  // Mostrar el estado del estacionamiento en la pantalla LCD
  lcd.clear();
  lcd.setCursor(0, 0);
  if (parkingOcupado) {
    lcd.print("Ocupado");
  } else {
    lcd.print("Libre  Plazas:" + String(plazasLibres));
  }
  lcd.setCursor(0, 1);
  lcd.print(String(temperatura) + "C  Hum:" + String(humedad) + "%");

  // Comprueba el sensor de humo
  if (smokeValue > 1000) {
    // Si se detecta humo, activa la alarma y muestra un mensaje en la pantalla
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("ALARMA");
    lcd.setCursor(0, 1);
    lcd.print("Evacuar edificio");
    alarma();
  }

  // Verificar si el estacionamiento está ocupado o libre
  if ((distancia1 < 10 && distancia2 < 10 && distancia3 < 10) || smokeValue > 1000) {
    parkingOcupado = true;
  } else {
    parkingOcupado = false;
  }


  delay(500);
}
