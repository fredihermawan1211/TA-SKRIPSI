-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 04, 2023 at 01:59 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

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

--
-- Dumping data for table `tab_anggota`
--

INSERT INTO `tab_anggota` (`qriijjxteb`, `qswtearttuysuainogpgota`, `komunitas_zygmqsabko`, `user_cfvdcbqnjl`) VALUES
(1, 'RESMI', 1, 2),
(2, 'RESMI', 1, 3),
(3, 'RESMI', 1, 4),
(4, 'RESMI', 1, 5),
(5, 'RESMI', 1, 6),
(6, 'RESMI', 1, 7),
(7, 'RESMI', 1, 8),
(8, 'RESMI', 1, 9),
(9, 'RESMI', 1, 10),
(10, 'RESMI', 1, 11),
(11, 'RESMI', 1, 12),
(12, 'RESMI', 1, 13),
(13, 'RESMI', 1, 14),
(14, 'RESMI', 1, 15),
(15, 'RESMI', 1, 16),
(16, 'RESMI', 1, 17),
(17, 'RESMI', 1, 18),
(18, 'RESMI', 1, 19);

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

--
-- Dumping data for table `tab_jadwal`
--

INSERT INTO `tab_jadwal` (`khelyqlath`, `jyiycynlnd`, `anggota_qriijjxteb`, `kolam_bwvecxxrfj`) VALUES
(1, '2023-06-05 00:00:00', 3, 1),
(2, '2023-06-05 00:00:00', 3, 2),
(3, '2023-06-05 00:00:00', 3, 3),
(4, '2023-06-05 00:00:00', 3, 4),
(5, '2023-06-05 00:00:00', 3, 5),
(6, '2023-06-05 00:00:00', 3, 6),
(7, '2023-06-05 00:00:00', 3, 7),
(8, '2023-06-05 00:00:00', 3, 8),
(9, '2023-06-06 00:00:00', 5, 1),
(10, '2023-06-06 00:00:00', 5, 2),
(11, '2023-06-06 00:00:00', 5, 3),
(12, '2023-06-06 00:00:00', 5, 4),
(13, '2023-06-06 00:00:00', 5, 5),
(14, '2023-06-06 00:00:00', 5, 6),
(15, '2023-06-06 00:00:00', 5, 7),
(16, '2023-06-06 00:00:00', 5, 8),
(17, '2023-06-07 00:00:00', 11, 1),
(18, '2023-06-07 00:00:00', 11, 2),
(19, '2023-06-07 00:00:00', 11, 3),
(20, '2023-06-07 00:00:00', 11, 4),
(21, '2023-06-07 00:00:00', 11, 5),
(22, '2023-06-07 00:00:00', 11, 6),
(23, '2023-06-07 00:00:00', 11, 7),
(24, '2023-06-07 00:00:00', 11, 8),
(25, '2023-06-08 00:00:00', 11, 1),
(26, '2023-06-08 00:00:00', 11, 2),
(27, '2023-06-08 00:00:00', 11, 3),
(28, '2023-06-08 00:00:00', 11, 4),
(29, '2023-06-08 00:00:00', 11, 5),
(30, '2023-06-08 00:00:00', 11, 6),
(31, '2023-06-08 00:00:00', 11, 7),
(32, '2023-06-08 00:00:00', 11, 8),
(33, '2023-06-09 00:00:00', 7, 1),
(34, '2023-06-09 00:00:00', 7, 2),
(35, '2023-06-09 00:00:00', 7, 3),
(36, '2023-06-09 00:00:00', 7, 4),
(37, '2023-06-09 00:00:00', 7, 5),
(38, '2023-06-09 00:00:00', 7, 6),
(39, '2023-06-09 00:00:00', 7, 7),
(40, '2023-06-09 00:00:00', 7, 8),
(41, '2023-06-10 00:00:00', 13, 1),
(42, '2023-06-10 00:00:00', 13, 2),
(43, '2023-06-10 00:00:00', 13, 3),
(44, '2023-06-10 00:00:00', 13, 4),
(45, '2023-06-10 00:00:00', 13, 5),
(46, '2023-06-10 00:00:00', 13, 6),
(47, '2023-06-10 00:00:00', 13, 7),
(48, '2023-06-10 00:00:00', 13, 8),
(49, '2023-06-11 00:00:00', 2, 1),
(50, '2023-06-11 00:00:00', 2, 2),
(51, '2023-06-11 00:00:00', 2, 3),
(52, '2023-06-11 00:00:00', 2, 4),
(53, '2023-06-11 00:00:00', 2, 5),
(54, '2023-06-11 00:00:00', 2, 6),
(55, '2023-06-11 00:00:00', 2, 7),
(56, '2023-06-11 00:00:00', 2, 8),
(57, '2023-06-12 00:00:00', 6, 1),
(58, '2023-06-12 00:00:00', 6, 2),
(59, '2023-06-12 00:00:00', 6, 3),
(60, '2023-06-12 00:00:00', 6, 4),
(61, '2023-06-12 00:00:00', 6, 5),
(62, '2023-06-12 00:00:00', 6, 6),
(63, '2023-06-12 00:00:00', 6, 7),
(64, '2023-06-12 00:00:00', 6, 8),
(65, '2023-06-13 00:00:00', 16, 1),
(66, '2023-06-13 00:00:00', 16, 2),
(67, '2023-06-13 00:00:00', 16, 3),
(68, '2023-06-13 00:00:00', 16, 4),
(69, '2023-06-13 00:00:00', 16, 5),
(70, '2023-06-13 00:00:00', 16, 6),
(71, '2023-06-13 00:00:00', 16, 7),
(72, '2023-06-13 00:00:00', 16, 8),
(73, '2023-06-14 00:00:00', 9, 1),
(74, '2023-06-14 00:00:00', 9, 2),
(75, '2023-06-14 00:00:00', 9, 3),
(76, '2023-06-14 00:00:00', 9, 4),
(77, '2023-06-14 00:00:00', 9, 5),
(78, '2023-06-14 00:00:00', 9, 6),
(79, '2023-06-14 00:00:00', 9, 7),
(80, '2023-06-14 00:00:00', 9, 8),
(81, '2023-06-15 00:00:00', 4, 1),
(82, '2023-06-15 00:00:00', 4, 2),
(83, '2023-06-15 00:00:00', 4, 3),
(84, '2023-06-15 00:00:00', 4, 4),
(85, '2023-06-15 00:00:00', 4, 5),
(86, '2023-06-15 00:00:00', 4, 6),
(87, '2023-06-15 00:00:00', 4, 7),
(88, '2023-06-15 00:00:00', 4, 8),
(89, '2023-06-16 00:00:00', 8, 1),
(90, '2023-06-16 00:00:00', 8, 2),
(91, '2023-06-16 00:00:00', 8, 3),
(92, '2023-06-16 00:00:00', 8, 4),
(93, '2023-06-16 00:00:00', 8, 5),
(94, '2023-06-16 00:00:00', 8, 6),
(95, '2023-06-16 00:00:00', 8, 7),
(96, '2023-06-16 00:00:00', 8, 8),
(97, '2023-06-17 00:00:00', 12, 1),
(98, '2023-06-17 00:00:00', 12, 2),
(99, '2023-06-17 00:00:00', 12, 3),
(100, '2023-06-17 00:00:00', 12, 4),
(101, '2023-06-17 00:00:00', 12, 5),
(102, '2023-06-17 00:00:00', 12, 6),
(103, '2023-06-17 00:00:00', 12, 7),
(104, '2023-06-17 00:00:00', 12, 8);

-- --------------------------------------------------------

--
-- Table structure for table `tab_kolam`
--

CREATE TABLE `tab_kolam` (
  `bwvecxxrfj` bigint(20) NOT NULL,
  `jvvagcwjau` varchar(50) DEFAULT NULL,
  `komunitas_zygmqsabko` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tab_kolam`
--

INSERT INTO `tab_kolam` (`bwvecxxrfj`, `jvvagcwjau`, `komunitas_zygmqsabko`) VALUES
(1, 'Kolam 1', 1),
(2, 'Kolam 2', 1),
(3, 'Kolam 3', 1),
(4, 'Kolam 4', 1),
(5, 'Kolam 5', 1),
(6, 'Kolam 6', 1),
(7, 'Kolam 7', 1),
(8, 'Kolam 8', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tab_komunitas`
--

CREATE TABLE `tab_komunitas` (
  `zygmqsabko` bigint(20) NOT NULL,
  `wapkapoyju` varchar(255) DEFAULT NULL,
  `dborbhqzvn` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tab_komunitas`
--

INSERT INTO `tab_komunitas` (`zygmqsabko`, `wapkapoyju`, `dborbhqzvn`) VALUES
(1, 'Jl.Gajah mada, Desa Jati Mulyo, Kec. Jati Agung, Kab. Lampung Selatan', 'Pokdakan Karya Bersama');

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
(2, 'setiawan_adi_saputra@ponline.tech', b'1', NULL, 'Setiawan Adi Saputra', '$2a$10$m9UXARymy0lP70x/VleKounNRnPi.sew2pMapjosQiAm/AQufd.72', 'local', NULL, 'ROLE_OWNER', NULL, NULL),
(3, 'andika@ponline.tech', b'1', NULL, 'Andika', '$2a$10$NAMNT2blN4LYNOifKapEvuo3IUatyCS4x4pwjyn5LMR1esBWq8wti', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(4, 'andi_prasetyo@ponline.tech', b'1', NULL, 'Andi Prasetyo', '$2a$10$n2hwnCSp9dVdjUiXns6CyOdUBE8TUiEp3af1rolUuU1ULhgZ8ycOW', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(5, 'joni_saputra@ponline.tech', b'1', NULL, 'Joni Saputra', '$2a$10$yB0CkFAwA85n9eyXpPgZM.9RD9XTYpR8ZLg.L3U09Jei8H2wGnqf2', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(6, 'mujiono@ponline.tech', b'1', NULL, 'Mujiono', '$2a$10$BCbSp9ksbttkTfAQCZWHneQHOPDsTH0ncYLdhdUJM0FkLf.jqN3Ta', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(7, 'hendrik@ponline.tech', b'1', NULL, 'Hendrik', '$2a$10$xpn1EAwZMR5VXs8SykytaudIJU5lISG4e4C3OeFC.m.8aa327ZI2C', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(8, 'romadon@ponline.tech', b'1', NULL, 'Romadon', '$2a$10$qmm3nuMvINzPxCfy/4krdOT/mRvHDtIb0vS5DUOzfptztc1xF4D1y', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(9, 'maryanto@ponline.tech', b'1', NULL, 'Maryanto', '$2a$10$b2Q2Dg5bHfI5OpuzNG9ev.b4WJlQGiGM2ITSJUL7umLaH5L/K4hIu', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(10, 'agus@ponline.tech', b'1', NULL, 'Agus', '$2a$10$YypEUj5XioxE5ETzW/WQ9ulxhp3bSe4SgZfMdIgGFzgkmv91WBpzi', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(11, 'marjono@ponline.tech', b'1', NULL, 'Marjono', '$2a$10$B94ERJkQAkGrFhDmsrzEr.Uu23C9gNklkY.5g5HEa5gEQ13PtfaNy', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(12, 'suratin@ponline.tech', b'1', NULL, 'Suratin', '$2a$10$JC4wb6DO1kJgbmRmEDxDhOX9s2ZsnmxTGcBdTFNtazkbBDynFJM1W', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(13, 'slamet@ponline.tech', b'1', NULL, 'Slamet', '$2a$10$T7PZv5S4hhfeFxT7l61LoeTP58KRqn0EXro/AZGl2.W/usxCO.LxG', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(14, 'robi@ponline.tech', b'1', NULL, 'Robi', '$2a$10$nR1hwHzoQO6vQDmU..aCi.wPorEBuVz6X6Z.pIvbY1fgktfDaGGLi', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(15, 'eko@ponline.tech', b'1', NULL, 'Eko', '$2a$10$WnAN7g1fRiEWEIFvMjMzf.js2Jwh/7Eca170MZNipDmqPxYf9g.zS', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(16, 'mahmudi@ponline.tech', b'1', NULL, 'Mahmudi', '$2a$10$7jL3/Kj3wE7i.W0tmXaCKeuglTEIm/H6zQMzeqow2qDM02hwDxD0y', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(17, 'tobi_ahmad_fajar@ponline.tech', b'1', NULL, 'Tobi Ahmad Fajar', '$2a$10$1vFNSZvS4aT0J6.9DBBoHe0ETqz402QxMR06YFod566h8dSX439iu', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(18, 'sunarman@ponline.tech', b'1', NULL, 'Sunarman', '$2a$10$q/EA6hV7htAjKImkEpJT6OT1W4774VmhC1EBx4ByGnzmuiRvQYd0S', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL),
(19, 'fredy@ponline.tech', b'1', NULL, 'Fredy Hermawan', '$2a$10$IW4wbszUFSMRJ3e..CAe1OvRm61Nrr8FGgdf.hI24t0qC7C9zJD1i', 'local', NULL, 'ROLE_EMPLOYEE', NULL, NULL);

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
  MODIFY `qriijjxteb` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `tab_jadwal`
--
ALTER TABLE `tab_jadwal`
  MODIFY `khelyqlath` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT for table `tab_kolam`
--
ALTER TABLE `tab_kolam`
  MODIFY `bwvecxxrfj` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tab_komunitas`
--
ALTER TABLE `tab_komunitas`
  MODIFY `zygmqsabko` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tab_user`
--
ALTER TABLE `tab_user`
  MODIFY `cfvdcbqnjl` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

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
