const int pinZumbador = 33;
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
const int pinServo = 15; // Pin del servomotor
const int dhtPin = 22; // Pin del sensor de temperatura
const int smokeSensorPin = 36; // Pin del sensor de humo MQ-2
const int pinSensorBarrera = 2; // Pin del sensor de infrarojos

LiquidCrystal lcd(13,32,4,5,18,19);
Servo servo;
DHT dht(dhtPin, DHT11);
String msg1, msg2, msg3;

int n = 0;
const int capacidad = 3;
int plazasLibres = capacidad;