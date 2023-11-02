#include <WiFi.h>
#include <PubSubClient.h>
#include <LiquidCrystal.h>
#include <DHT.h>
#include <ESP32Servo.h>

#include "config.h" //Configuraton file
#include "MQTT.hpp" //MQTT functions
#include "ESP8266_Utils.hpp" //Wifi connection
#include "ESP8266_Utils_MQTT.hpp" //MQTT Connection

Servo servo;
DHT dht(dhtPin, DHT11);
String msg1, msg2, msg3;
int n = 0;
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
      mqttClient.publish("parking/matricula", (matricula).c_str());
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
    msg1 = "ocupado";
    digitalWrite(redLedPin1, HIGH);
    digitalWrite(greenLedPin1, LOW);
    plazasLibres--;
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
  //Subir el topic del estado del estacionamiento
  //mqttClient.publish("parking/plazas/1", msg1.c_str());

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
    plazasLibres--;
  } else {
    msg2 = "libre";
    digitalWrite(redLedPin2, LOW);
    digitalWrite(greenLedPin2, HIGH);
  }
  //Subir el topic del estado del estacionamiento
  //mqttClient.publish("parking/plazas/2", msg2.c_str());

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
    plazasLibres--;
  } else {
    msg3 = "libre";
    digitalWrite(redLedPin3, LOW);
    digitalWrite(greenLedPin3, HIGH);
  }
  //Subir el topic del estado del estacionamiento
  //mqttClient.publish("parking/plazas/3", msg3.c_str());

  if (n % 3 == 0){
    //Subir topic de plazas libres
    mqttClient.publish("parking/plazasLibres", String(plazasLibres).c_str());
  }
  
  if (n % 20 == 0){
    //Medir temperatura, humedad y humo
    float temperatura = dht.readTemperature();
    mqttClient.publish("parking/temperatura", String(temperatura).c_str());

    int humedad = dht.readHumidity();
    mqttClient.publish("parking/humedad", String(humedad).c_str());
  }

  int smokeValue = analogRead(smokeSensorPin);
  if (smokeValue > 3000) {
    // Si se detecta humo, manda mensaje para activar la alarma
    mqttClient.publish("parking/humo", String("on").c_str());
  }

  // Verificar si el estacionamiento está ocupado o libre
  if ((distancia1 < 10 && distancia2 < 10 && distancia3 < 10) || smokeValue > 3000) {
    parkingOcupado = true;
  } else {
    parkingOcupado = false;
  }
  
  n++;
  delay(500);
}
