-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 02, 2023 at 11:55 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12
USE parking;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parking`
--

-- --------------------------------------------------------

--
-- Table structure for table `ciudad`
--

CREATE TABLE `ciudad` (
  `id_ciudad` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `pais` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `historico_coches`
--

CREATE TABLE `historico_coches` (
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `matricula` varchar(15) NOT NULL,
  `Entrada` tinyint(1) NOT NULL,
  `id_parking` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `historico_mediciones`
--

CREATE TABLE `historico_mediciones` (
  `id_sensor` int(11) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `valor` double NOT NULL,
  `alerta` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `parking`
--

CREATE TABLE `parking` (
  `id_parking` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `latitud` double NOT NULL,
  `longitud` double NOT NULL,
  `id_ciudad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sensor`
--

CREATE TABLE `sensor` (
  `id_parking` int(11) DEFAULT NULL,
  `id_tipo` int(11) DEFAULT NULL,
  `id_sensor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tipo_sensor`
--

CREATE TABLE `tipo_sensor` (
  `id_tipo` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `unidades` varchar(15) NOT NULL,
  `minvalor` double NOT NULL,
  `maxvalor` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`id_ciudad`);

--
-- Indexes for table `historico_coches`
--
ALTER TABLE `historico_coches`
  ADD PRIMARY KEY (`fecha`,`matricula`,`Entrada`),
  ADD KEY `fk_historico_parking` (`id_parking`);

--
-- Indexes for table `historico_mediciones`
--
ALTER TABLE `historico_mediciones`
  ADD PRIMARY KEY (`fecha`),
  ADD KEY `fk_mediciones_sensor` (`id_sensor`);

--
-- Indexes for table `parking`
--
ALTER TABLE `parking`
  ADD PRIMARY KEY (`id_parking`),
  ADD KEY `fk_parking_ciudad` (`id_ciudad`);

--
-- Indexes for table `sensor`
--
ALTER TABLE `sensor`
  ADD PRIMARY KEY (`id_sensor`),
  ADD KEY `fk_sensor_tipo` (`id_tipo`),
  ADD KEY `fk_sensor_parking` (`id_parking`);

--
-- Indexes for table `tipo_sensor`
--
ALTER TABLE `tipo_sensor`
  ADD PRIMARY KEY (`id_tipo`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `historico_coches`
--
ALTER TABLE `historico_coches`
  ADD CONSTRAINT `fk_historico_parking` FOREIGN KEY (`id_parking`) REFERENCES `parking` (`id_parking`);

--
-- Constraints for table `historico_mediciones`
--
ALTER TABLE `historico_mediciones`
  ADD CONSTRAINT `fk_mediciones_sensor` FOREIGN KEY (`id_sensor`) REFERENCES `sensor` (`id_sensor`);

--
-- Constraints for table `parking`
--
ALTER TABLE `parking`
  ADD CONSTRAINT `fk_parking_ciudad` FOREIGN KEY (`id_ciudad`) REFERENCES `ciudad` (`id_ciudad`);

--
-- Constraints for table `sensor`
--
ALTER TABLE `sensor`
  ADD CONSTRAINT `fk_sensor_parking` FOREIGN KEY (`id_parking`) REFERENCES `parking` (`id_parking`),
  ADD CONSTRAINT `fk_sensor_tipo` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_sensor` (`id_tipo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
