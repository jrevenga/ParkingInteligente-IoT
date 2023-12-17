import random
from datetime import datetime, timedelta

class City:
    def __init__(self, id, name, country):
        self.id = id
        self.name = name
        self.country = country

class Parking:
    def __init__(self, id, name, latitude, longitude):
        self.id = id
        self.name = name
        self.latitude = latitude
        self.longitude = longitude

class Sensor:
    def __init__(self, id, tipo, parking):
        self.id = id
        self.tipo = tipo
        self.parking = parking

class SensorType:
    def __init__(self, id, name, units, minvalue, maxvalue):
        self.id = id
        self.name = name
        self.units = units
        self.minvalue = minvalue
        self.maxvalue = maxvalue

class CarHistory:
    def __init__(self, timestamp, matricula, entrada, parking):
        self.timestamp = timestamp
        self.matricula = matricula
        self.entrada = entrada
        self.parking = parking

class Measurement:
    def __init__(self, sensor, timestamp, value, alerta):
        self.sensor = sensor
        self.timestamp = timestamp
        self.value = value
        self.alerta = alerta

def generate_cities():
    return [City(1, "Madrid", "Spain"), City(2, "Barcelona", "Spain")]

def generate_parkings(cities):
    parkings = []
    parking_id = 1  # Inicializamos el ID del parking
    for city in cities:
        for i in range(1, 6):
            parkings.append(Parking(parking_id, f"Parking {parking_id}", random.uniform(0, 90), random.uniform(0, 180)))
            parking_id += 1  # Incrementamos el ID del parking
    return parkings

def generate_sensors(parkings):
    sensors = []
    for parking in parkings:
        sensors.append(Sensor(parking.id, 1, parking.id))
        sensors.append(Sensor(parking.id + 10, 2, parking.id))
    return sensors

def generate_sensor_types():
    return [SensorType(1, "Temperature", "Celsius", 0, 100), SensorType(2, "Gas", "PPM", 0, 1000)]

def generate_car_histories(parkings):
    car_histories = []
    matriculas = ["ABC000","DEF001","GHI002","JKL003","MNO004","PQR005","STU006","VWX007","YZA008","BCD009"]
    
    for parking in parkings:
        for matricula in matriculas:
            fecha=datetime.now() + timedelta(hours=random.uniform(1,6))
            fecha2=datetime.now() + timedelta(hours=random.uniform(7,12))
            car_histories.append(CarHistory(fecha, matricula, True, parking.id))
            car_histories.append(CarHistory(fecha2, matricula, False, parking.id))
    return car_histories

def generate_measurements(sensors, sensor_types):
    measurements = []
    for sensor in sensors:
        sensor_type = next((st for st in sensor_types if st.id == sensor.tipo), None)
        for day in range(1, 32):
            timestamp = datetime(2023, 1, day)
            value = random.uniform(sensor_type.minvalue, sensor_type.maxvalue)
            alerta = value > 50
            measurements.append(Measurement(sensor.id, timestamp, value, alerta))
    return measurements

def write_to_txt(data, filename):
    with open(filename, 'w') as file:
        for item in data:
            file.write(str(item.__dict__) + '\n')

if __name__ == "__main__":
    cities = generate_cities()
    parkings = generate_parkings(cities)
    sensors = generate_sensors(parkings)
    sensor_types = generate_sensor_types()
    car_histories = generate_car_histories(parkings)
    measurements = generate_measurements(sensors, sensor_types)

    write_to_txt(cities, 'cities.txt')
    write_to_txt(parkings, 'parkings.txt')
    write_to_txt(sensors, 'sensors.txt')
    write_to_txt(sensor_types, 'sensor_types.txt')
    write_to_txt(car_histories, 'car_histories.txt')
    write_to_txt(measurements, 'measurements.txt')
