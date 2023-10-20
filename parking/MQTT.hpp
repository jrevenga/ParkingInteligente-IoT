WiFiClient espClient;
PubSubClient mqttClient(espClient);

void SuscribeMqtt() {
  mqttClient.subscribe("esp32/plaza1");
  mqttClient.subscribe("esp32/plaza2");
  mqttClient.subscribe("esp32/plaza3");
  mqttClient.subscribe("esp32/temperatura");
  mqttClient.subscribe("esp32/humedad");
  mqttClient.subscribe("esp32/humo");
  mqttClient.subscribe("esp32/matricula");
}

String content = "";
void OnMqttReceived(char* topic, byte* payload, unsigned int length) {
  Serial.print("Received on ");
  Serial.print(topic);
  Serial.print(": ");
  content = "";
  for (size_t i = 0; i < length; i++) {
    content.concat((char)payload[i]);
  }
  Serial.print(content);
  Serial.println(); 
}