-- Borrar procedimientos almacenados
DROP PROCEDURE IF EXISTS GenerateTemperatureData;
DROP PROCEDURE IF EXISTS GenerateHumidityData;
DROP PROCEDURE IF EXISTS GenerateGasData;
DELIMITER //
CREATE PROCEDURE GenerateTemperatureData()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE days_back INT DEFAULT 0;
  
  WHILE i <= 100 DO
    INSERT INTO parking.historico_mediciones (id_sensor, fecha, valor, alerta)
    VALUES (1, FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - days_back * 86400), ROUND(RAND() * 10.0 + 20.0, 2), false);
    
    SET days_back := days_back + 1;
    SET i := i + 1;
  END WHILE;
END //
DELIMITER ;

-- Llamar al procedimiento para generar las consultas
CALL GenerateTemperatureData();

DELIMITER //
CREATE PROCEDURE GenerateHumidityData()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE days_back INT DEFAULT 0;
  
  WHILE i <= 100 DO
    INSERT INTO parking.historico_mediciones (id_sensor, fecha, valor, alerta)
    VALUES (2, FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - days_back * 86400), ROUND(RAND() * 30.0 + 40.0, 2), false);
    
    SET days_back := days_back + 1;
    SET i := i + 1;
  END WHILE;
END //
DELIMITER ;

-- Llamar al procedimiento para generar las consultas
CALL GenerateHumidityData();

DELIMITER //
CREATE PROCEDURE GenerateGasData()
BEGIN
  DECLARE i INT DEFAULT 1;
  DECLARE days_back INT DEFAULT 0;
  
  WHILE i <= 100 DO
    INSERT INTO parking.historico_mediciones (id_sensor, fecha, valor, alerta)
    VALUES (3, FROM_UNIXTIME(UNIX_TIMESTAMP(NOW()) - days_back * 86400), 0.0, false);
    
    SET days_back := days_back + 1;
    SET i := i + 1;
  END WHILE;
END //
DELIMITER ;

-- Llamar al procedimiento para generar las consultas
CALL GenerateGasData();

-- Generar 100 registros de entrada de coches para los últimos 40 días
INSERT INTO parking.historico_coches (fecha, matricula, Entrada, id_parking)
SELECT
    TIMESTAMPADD(SECOND, RAND() * 8 * 3600, TIMESTAMPADD(DAY, -RAND() * 40, NOW())) AS fecha,
    CONCAT(CHAR(FLOOR(65 + RAND() * 26)), CHAR(FLOOR(65 + RAND() * 26)), CHAR(FLOOR(48 + RAND() * 10)),
           CHAR(FLOOR(48 + RAND() * 10)), CHAR(FLOOR(48 + RAND() * 10)), CHAR(FLOOR(48 + RAND() * 10)),
           CHAR(FLOOR(65 + RAND() * 26)), CHAR(FLOOR(65 + RAND() * 26))) AS matricula,
    1 AS Entrada,
    1 AS id_parking
FROM
    information_schema.tables
LIMIT 100;

-- Generar 100 registros de salida de coches para los últimos 40 días
INSERT INTO parking.historico_coches (fecha, matricula, Entrada, id_parking)
SELECT
    TIMESTAMPADD(SECOND, RAND() * 8 * 3600, TIMESTAMPADD(DAY, -RAND() * 40, NOW())) AS fecha,
    matricula,
    0 AS Entrada,
    1 AS id_parking
FROM
    parking.historico_coches
WHERE
    Entrada = 1
LIMIT 100;
