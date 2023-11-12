CREATE TABLE parking (
    id INT NOT NULL,
    nombre VARCHAR(10),
    PRIMARY KEY (id)
);

CREATE TABLE clima (
    temperatura FLOAT,
    humedad FLOAT,
    humo_gas FLOAT,
    fecha TIMESTAMP NOT NULL,
    id_parking INT,
    PRIMARY KEY (fecha),
    FOREIGN KEY (id_parking) REFERENCES parking(id)
);

CREATE TABLE plaza (
    id INT NOT NULL,
    estado VARCHAR(10),
    tipo VARCHAR(10),
    id_parking INT,
    PRIMARY KEY (id),
    FOREIGN KEY (id_parking) REFERENCES parking(id)
);

CREATE TABLE registro (
    matricula VARCHAR(10) NOT NULL,
    id_evento INT,
    fecha TIMESTAMP,
    id_parking INT,
    PRIMARY KEY (matricula),
    FOREIGN KEY (id_parking) REFERENCES parking(id)
);

CREATE TABLE evento_plaza (
    id_plaza INT,
    id_evento INT,
    fecha INT
);

CREATE TABLE evento (
    id INT NOT NULL,
    descripcion VARCHAR(10),
    PRIMARY KEY (id)
);

ALTER TABLE registro ADD FOREIGN KEY (id_evento) REFERENCES evento(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE clima ADD UNIQUE (id_parking);
ALTER TABLE clima ADD FOREIGN KEY (id_parking) REFERENCES parking(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE plaza ADD FOREIGN KEY (id_parking) REFERENCES parking(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE evento_plaza ADD FOREIGN KEY (id_plaza) REFERENCES plaza(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE evento_plaza ADD FOREIGN KEY (id_evento) REFERENCES evento(id)
    ON DELETE SET NULL ON UPDATE CASCADE;

