INSERT INTO ciudad (id_ciudad, nombre, pais)
VALUES (1, 'madrid', 'España');

INSERT INTO parking (id_parking, nombre, latitud, longitud, id_ciudad, plazas_totales, plazas_disponibles)
VALUES (1, 'ParkRetiro', 2.5, 3.5, 1, 3, 3);

INSERT INTO parking.tipo_sensor
(id_tipo,
nombre,
unidades,
minvalor,
maxvalor)
VALUES
(1, 'temperatura', 'C', -20.0, 55.0);

INSERT INTO parking.tipo_sensor
(id_tipo,
nombre,
unidades,
minvalor,
maxvalor)
VALUES
(2, 'humedad', '%', 0.0, 100.0);

INSERT INTO parking.tipo_sensor
(id_tipo,
nombre,
unidades,
minvalor,
maxvalor)
VALUES
(3, 'gas', 'ppm', -1.0, 10000.0);

INSERT INTO parking.sensor (id_parking, id_tipo, id_sensor)
VALUES (1, 1, 1);

INSERT INTO parking.sensor (id_parking, id_tipo, id_sensor)
VALUES (1, 2, 2);

INSERT INTO parking.sensor (id_parking, id_tipo, id_sensor)
VALUES (1, 3, 3);


