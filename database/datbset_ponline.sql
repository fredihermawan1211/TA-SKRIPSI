-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 22, 2023 at 07:46 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.0.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `datbes_ponline`
--
CREATE DATABASE IF NOT EXISTS `datbes_ponline` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `datbes_ponline`;

-- --------------------------------------------------------

--
-- Table structure for table `tab_anggota`
--

CREATE TABLE `tab_anggota` (
  `qriijjxteb` bigint(20) NOT NULL,
  `qswtearttuysuainogpgota` varchar(255) DEFAULT NULL,
  `komunitas_zygmqsabko` bigint(20) DEFAULT NULL,
  `user_cfvdcbqnjl` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tab_jadwal`
--

CREATE TABLE `tab_jadwal` (
  `khelyqlath` bigint(20) NOT NULL,
  `jyiycynlnd` datetime DEFAULT NULL,
  `anggota_qriijjxteb` bigint(20) DEFAULT NULL,
  `kolam_bwvecxxrfj` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tab_kolam`
--

CREATE TABLE `tab_kolam` (
  `bwvecxxrfj` bigint(20) NOT NULL,
  `jvvagcwjau` varchar(50) DEFAULT NULL,
  `komunitas_zygmqsabko` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tab_komunitas`
--

CREATE TABLE `tab_komunitas` (
  `zygmqsabko` bigint(20) NOT NULL,
  `wapkapoyju` varchar(255) DEFAULT NULL,
  `dborbhqzvn` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tab_user`
--

CREATE TABLE `tab_user` (
  `cfvdcbqnjl` bigint(20) NOT NULL,
  `tbfewjerbs` varchar(255) NOT NULL,
  `lfcmfluvud` bit(1) NOT NULL,
  `kwbrftpgtm` varchar(255) DEFAULT NULL,
  `ggxafwykfd` varchar(255) NOT NULL,
  `jtixhfcdmv` varchar(255) DEFAULT NULL,
  `ccuombbxii` varchar(255) NOT NULL,
  `oebwazzvdi` varchar(255) DEFAULT NULL,
  `uwegexwxuf` varchar(255) NOT NULL,
  `xvfeeumirr` varchar(64) DEFAULT NULL,
  `typyhbwqgm` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tab_user`
--

INSERT INTO `tab_user` (`cfvdcbqnjl`, `tbfewjerbs`, `lfcmfluvud`, `kwbrftpgtm`, `ggxafwykfd`, `jtixhfcdmv`, `ccuombbxii`, `oebwazzvdi`, `uwegexwxuf`, `xvfeeumirr`, `typyhbwqgm`) VALUES
(1, 'pegawai1_ponline@kinda.pw', b'1', NULL, 'Pegawai 1', '$2a$10$2UE/DnalIdyhXooM6i6NKe5tN1oN1zJV6paBqQOEX5TDS9.ijBMEm', 'local', NULL, 'ROLE_USER', NULL, NULL),
(2, 'apaajalah@kinda.pw', b'0', NULL, 'apa aja', '$2a$10$/0TtEe25EboZ8ouVQe8MneIhOu0.zaswqx9Y/3NWDb8FzyLfYLwyS', 'local', NULL, 'ROLE_USER', 'WjYCgGKGGaCs4iMTWbduEPJfTussEqIvv45okEHMthm5b81EiijFPdVjY8dThCcD', NULL),
(3, 'fredy_hermawan@teknokrat.ac.id', b'1', 'https://lh3.googleusercontent.com/a/AEdFTp5Ehfdid3dWtZucPcWNbWHc0UsYtTwJ-3yPuLAkzg=s96-c', 'Fredy Hermawan Kuliah', NULL, 'google', '117878212321696496446', 'ROLE_USER', NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tab_anggota`
--
ALTER TABLE `tab_anggota`
  ADD PRIMARY KEY (`qriijjxteb`),
  ADD KEY `FKn7i5q6llxu1ltnod3xmmrlepd` (`komunitas_zygmqsabko`),
  ADD KEY `FKfs3g1p15xgtmwmhjadjvvhpmt` (`user_cfvdcbqnjl`);

--
-- Indexes for table `tab_jadwal`
--
ALTER TABLE `tab_jadwal`
  ADD PRIMARY KEY (`khelyqlath`),
  ADD KEY `FK955bwp8h445ccafxcjd7yhv4b` (`anggota_qriijjxteb`),
  ADD KEY `FKq4fpvu4hhnqy5n0noa87d1xbe` (`kolam_bwvecxxrfj`);

--
-- Indexes for table `tab_kolam`
--
ALTER TABLE `tab_kolam`
  ADD PRIMARY KEY (`bwvecxxrfj`),
  ADD KEY `FK725ftq07lkfkoikngmatmp631` (`komunitas_zygmqsabko`);

--
-- Indexes for table `tab_komunitas`
--
ALTER TABLE `tab_komunitas`
  ADD PRIMARY KEY (`zygmqsabko`);

--
-- Indexes for table `tab_user`
--
ALTER TABLE `tab_user`
  ADD PRIMARY KEY (`cfvdcbqnjl`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tab_anggota`
--
ALTER TABLE `tab_anggota`
  MODIFY `qriijjxteb` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tab_jadwal`
--
ALTER TABLE `tab_jadwal`
  MODIFY `khelyqlath` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tab_kolam`
--
ALTER TABLE `tab_kolam`
  MODIFY `bwvecxxrfj` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tab_komunitas`
--
ALTER TABLE `tab_komunitas`
  MODIFY `zygmqsabko` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tab_user`
--
ALTER TABLE `tab_user`
  MODIFY `cfvdcbqnjl` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tab_anggota`
--
ALTER TABLE `tab_anggota`
  ADD CONSTRAINT `FKfs3g1p15xgtmwmhjadjvvhpmt` FOREIGN KEY (`user_cfvdcbqnjl`) REFERENCES `tab_user` (`cfvdcbqnjl`),
  ADD CONSTRAINT `FKn7i5q6llxu1ltnod3xmmrlepd` FOREIGN KEY (`komunitas_zygmqsabko`) REFERENCES `tab_komunitas` (`zygmqsabko`);

--
-- Constraints for table `tab_jadwal`
--
ALTER TABLE `tab_jadwal`
  ADD CONSTRAINT `FK955bwp8h445ccafxcjd7yhv4b` FOREIGN KEY (`anggota_qriijjxteb`) REFERENCES `tab_anggota` (`qriijjxteb`),
  ADD CONSTRAINT `FKq4fpvu4hhnqy5n0noa87d1xbe` FOREIGN KEY (`kolam_bwvecxxrfj`) REFERENCES `tab_kolam` (`bwvecxxrfj`);

--
-- Constraints for table `tab_kolam`
--
ALTER TABLE `tab_kolam`
  ADD CONSTRAINT `FK725ftq07lkfkoikngmatmp631` FOREIGN KEY (`komunitas_zygmqsabko`) REFERENCES `tab_komunitas` (`zygmqsabko`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
