// Wifi Settings
const char* ssid = "POCOX5Pro";
const char* password = "06072001?";
const char* hostname = "ESP32_1";

//MQTT Settings
const char* MQTT_BROKER_ADRESS = "192.168.112.15";
//172.17.0.1
//192.168.64.245
const uint16_t MQTT_PORT = 1883;
const char* MQTT_CLIENT_NAME = "ESP32Client_1";

//Server Settings
IPAddress ip(192, 168, 1, 137);
IPAddress gateway(192, 168, 1, 1);
IPAddress subnet(255, 255, 255, 0);