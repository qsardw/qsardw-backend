-- MySQL dump 10.13  Distrib 5.5.35, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: qsardw
-- ------------------------------------------------------
-- Server version	5.5.35-0ubuntu0.13.10.2

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
-- Table structure for table `dataset`
--

DROP TABLE IF EXISTS `dataset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dataset_name` varchar(255) NOT NULL,
  `description` mediumtext NOT NULL,
  `original_file` varchar(255) NOT NULL,
  `file_type` int(10) unsigned NOT NULL,
  `initial_molecules` int(10) unsigned NOT NULL DEFAULT '0',
  `distinct_molecules` int(10) unsigned NOT NULL DEFAULT '0',
  `is_cleaned` tinyint(1) NOT NULL DEFAULT '0',
  `owner` int(10) unsigned NOT NULL,
  `created_on` datetime NOT NULL,
  `multiple_molecules_strategy` int(10) unsigned NOT NULL DEFAULT '1',
  `on_duplicates_strategy` int(10) unsigned NOT NULL DEFAULT '1',
  `status` int(10) unsigned NOT NULL DEFAULT '0',
  `visibility` int(10) unsigned NOT NULL DEFAULT '1',
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataset`
--

LOCK TABLES `dataset` WRITE;
/*!40000 ALTER TABLE `dataset` DISABLE KEYS */;
INSERT INTO `dataset` VALUES (6,'Test Dataset','Test Dataset','/var/uploads/qsardw/1/2014/03/30/dataset_533889fba666e.sdf',1,0,0,0,1,'2014-03-30 23:17:47',2,2,2,2,'2014-04-19 22:01:00'),(7,'Dataset BD 2011','Dataset BD 2011','/var/uploads/qsardw/1/2014/04/01/dataset_533b33bf789fb.sdf',1,0,0,0,1,'2014-04-01 23:46:39',2,3,2,2,'2014-04-19 22:01:07');
/*!40000 ALTER TABLE `dataset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dataset_errors`
--

DROP TABLE IF EXISTS `dataset_errors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset_errors` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dataset` int(10) unsigned NOT NULL,
  `molecule` int(11) NOT NULL DEFAULT '0',
  `error_message` text NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_dataset_errors_dataset_id_idx` (`dataset`),
  CONSTRAINT `fk_dataset_errors_dataset_id` FOREIGN KEY (`dataset`) REFERENCES `dataset` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataset_errors`
--

LOCK TABLES `dataset_errors` WRITE;
/*!40000 ALTER TABLE `dataset_errors` DISABLE KEYS */;
/*!40000 ALTER TABLE `dataset_errors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dataset_raw_molecules`
--

DROP TABLE IF EXISTS `dataset_raw_molecules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset_raw_molecules` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dataset` int(10) unsigned NOT NULL,
  `molecule_number` int(11) NOT NULL,
  `smile` text NOT NULL,
  `inchi` text NOT NULL,
  `inchi_key` varchar(255) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_dataset_raw_molecules_dataset_idx` (`dataset`),
  KEY `idx_dataset_molecule` (`dataset`,`molecule_number`),
  KEY `idx_dataset_inchi_key` (`dataset`,`inchi_key`),
  CONSTRAINT `fk_dataset_raw_molecules_dataset` FOREIGN KEY (`dataset`) REFERENCES `dataset` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dataset_raw_molecules`
--

LOCK TABLES `dataset_raw_molecules` WRITE;
/*!40000 ALTER TABLE `dataset_raw_molecules` DISABLE KEYS */;
/*!40000 ALTER TABLE `dataset_raw_molecules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `duplicated_molecules_strategy`
--

DROP TABLE IF EXISTS `duplicated_molecules_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `duplicated_molecules_strategy` (
  `id` int(10) unsigned NOT NULL,
  `strategy_name` varchar(255) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `duplicated_molecules_strategy`
--

LOCK TABLES `duplicated_molecules_strategy` WRITE;
/*!40000 ALTER TABLE `duplicated_molecules_strategy` DISABLE KEYS */;
INSERT INTO `duplicated_molecules_strategy` VALUES (1,'Manual review','2014-02-26 23:00:30'),(2,'Discard all duplicates','2014-02-26 23:00:30'),(3,'Keep first moleculed found','2014-02-26 23:00:30');
/*!40000 ALTER TABLE `duplicated_molecules_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multiple_molecules_strategy`
--

DROP TABLE IF EXISTS `multiple_molecules_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multiple_molecules_strategy` (
  `id` int(10) unsigned NOT NULL,
  `strategy_name` varchar(255) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multiple_molecules_strategy`
--

LOCK TABLES `multiple_molecules_strategy` WRITE;
/*!40000 ALTER TABLE `multiple_molecules_strategy` DISABLE KEYS */;
INSERT INTO `multiple_molecules_strategy` VALUES (1,'Manual review','2014-03-30 21:16:50'),(2,'Biggest molecule automatic selection','2014-02-26 21:53:00');
/*!40000 ALTER TABLE `multiple_molecules_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `objects_visibility`
--

DROP TABLE IF EXISTS `objects_visibility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `objects_visibility` (
  `id` int(10) unsigned NOT NULL,
  `visibility_name` varchar(255) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `objects_visibility`
--

LOCK TABLES `objects_visibility` WRITE;
/*!40000 ALTER TABLE `objects_visibility` DISABLE KEYS */;
INSERT INTO `objects_visibility` VALUES (1,'Private','2014-03-10 22:08:41'),(2,'Group only','2014-03-10 22:08:41'),(3,'Public','2014-03-10 22:08:41');
/*!40000 ALTER TABLE `objects_visibility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(255) NOT NULL,
  `complete_name` varchar(255) NOT NULL,
  `photo` varchar(255) NOT NULL,
  `ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'javier.caride@gmail.com','5FZ2Z8QIkA7UTZ4BYkoC+GsReLf569mSKDsfods6LYQ8t+a8EW9oaircfMpmaLbPBh4FOBiiFyLfuZmTSUwzZg==','QSARDW_USER','Javier Caride','/images/user.png','2014-02-27 23:17:12');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-05-14 22:57:59
