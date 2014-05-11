CREATE DATABASE  IF NOT EXISTS `pr07carmonagil` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pr07carmonagil`;
-- MySQL dump 10.13  Distrib 5.5.35, for debian-linux-gnu (i686)
--
-- Host: 127.0.0.1    Database: pr07carmonagil
-- ------------------------------------------------------
-- Server version	5.5.35-0ubuntu0.12.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ranking`
--

DROP TABLE IF EXISTS `ranking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ranking` (
  `login` varchar(15) NOT NULL,
  `puntos` float(3,1) NOT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ranking`
--

LOCK TABLES `ranking` WRITE;
/*!40000 ALTER TABLE `ranking` DISABLE KEYS */;
INSERT INTO `ranking` VALUES ('amarillo',12.0),('and',-3.0),('andas',0.0),('andasd',0.0),('andd',0.0),('andres',-11.5),('andsd',0.0),('andsdf',0.0),('andsdfDD33',0.0),('andsdfSS3',0.0),('b',-10.0),('blanco',4.0),('gil',4.0),('lolo',-1.5),('lucia',4.0),('manolo',11.0),('negro',2.0),('p',-1.0),('pardo',9.0),('ppgrillo',7.0),('rojo',1.0),('Rojo1',0.0),('rosa',23.0),('yomisma',8.0);
/*!40000 ALTER TABLE `ranking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generos`
--

DROP TABLE IF EXISTS `generos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `generos` (
  `genero` varchar(50) NOT NULL,
  PRIMARY KEY (`genero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generos`
--

LOCK TABLES `generos` WRITE;
/*!40000 ALTER TABLE `generos` DISABLE KEYS */;
INSERT INTO `generos` VALUES ('Accion'),('Comedia'),('Drama'),('Humor'),('Policiaco'),('Thriller');
/*!40000 ALTER TABLE `generos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `concurso`
--

DROP TABLE IF EXISTS `concurso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `concurso` (
  `login` varchar(15) NOT NULL,
  `idfotograma` int(11) NOT NULL,
  `puntos` float(2,1) NOT NULL,
  PRIMARY KEY (`login`,`idfotograma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `concurso`
--

LOCK TABLES `concurso` WRITE;
/*!40000 ALTER TABLE `concurso` DISABLE KEYS */;
INSERT INTO `concurso` VALUES ('and',1,-0.5),('and',2,-0.5),('and',3,-0.5),('and',4,-0.5),('and',6,-0.5),('andres',2,-0.5),('andres',3,-0.5),('andres',4,-0.5),('andres',5,-0.5),('andres',6,-0.5),('b',1,-0.5),('b',3,-0.5),('lolo',1,-0.5),('lolo',2,-0.5),('lolo',4,-0.5),('p',2,-0.5),('p',5,-0.5),('rosa',1,1.0),('rosa',4,1.0),('rosa',5,1.0),('rosa',6,1.0);
/*!40000 ALTER TABLE `concurso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `login` varchar(15) NOT NULL,
  `clave` varchar(15) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`login`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('a','a',NULL),('and','a','asdf'),('andas','asdf','asd..'),('andasd','sadf','asdf'),('andd','d','dfg.'),('andres','a',NULL),('andsd','sdf','fssdf'),('andsdf','asd','asda'),('andsdfDD33','sdf','sdf'),('andsdfSS3','salsdjAAA3','sf'),('b','b','b'),('carmona','a','andres'),('lolo','lolo','lolo'),('lucia','lucia',NULL),('p','p','p'),('ppgrillo','ppgrillo',NULL),('Rojo1','Rojo1','rojo'),('rosa','rosa',NULL),('yomisma','yomisma',NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `fotogramaconcurso`
--

DROP TABLE IF EXISTS `fotogramaconcurso`;
/*!50001 DROP VIEW IF EXISTS `fotogramaconcurso`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `fotogramaconcurso` (
  `idFotogramas` tinyint NOT NULL,
  `archivo` tinyint NOT NULL,
  `titPelicula` tinyint NOT NULL,
  `anyoEstreno` tinyint NOT NULL,
  `director` tinyint NOT NULL,
  `genero` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `fotogramas`
--

DROP TABLE IF EXISTS `fotogramas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fotogramas` (
  `idFotogramas` int(11) NOT NULL,
  `archivo` varchar(45) NOT NULL,
  `titPelicula` varchar(255) NOT NULL,
  `anyoEstreno` int(11) NOT NULL,
  `director` varchar(128) NOT NULL,
  `genero` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idFotogramas`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotogramas`
--

LOCK TABLES `fotogramas` WRITE;
/*!40000 ALTER TABLE `fotogramas` DISABLE KEYS */;
INSERT INTO `fotogramas` VALUES (1,'1.jpg','Chacal',1936,'Fred Zimmenann','Policiaco'),(2,'2.jpg','Ciudadano Kane',1939,'Orson Welles','Thriller'),(3,'3.jpg','Circo de carreras',1950,'Eduardo Ponce','Humor'),(4,'4.jpg','El arbo de la vida',1999,'Roxana','Policiaco'),(5,'5.jpg','Prueba2',1971,'Eduardo Ponce','Policiaco'),(6,'6.jpg','Prueba3',1989,'Eduardo','Humor');
/*!40000 ALTER TABLE `fotogramas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `fotogramaconcurso`
--

/*!50001 DROP TABLE IF EXISTS `fotogramaconcurso`*/;
/*!50001 DROP VIEW IF EXISTS `fotogramaconcurso`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `fotogramaconcurso` AS select `fotogramas`.`idFotogramas` AS `idFotogramas`,`fotogramas`.`archivo` AS `archivo`,`fotogramas`.`titPelicula` AS `titPelicula`,`fotogramas`.`anyoEstreno` AS `anyoEstreno`,`fotogramas`.`director` AS `director`,`fotogramas`.`genero` AS `genero` from `fotogramas` where (not(`fotogramas`.`idFotogramas` in (select `concurso`.`idfotograma` from `concurso` where (`concurso`.`login` = 'andres')))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-09 19:48:45
