-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: bdprojetoscad
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_abasfundoautomatico`
--

DROP TABLE IF EXISTS `tb_abasfundoautomatico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_abasfundoautomatico` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(35) DEFAULT NULL,
  `cadastradopor` varchar(100) DEFAULT NULL,
  `datahora` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_abasfundoautomatico`
--

LOCK TABLES `tb_abasfundoautomatico` WRITE;
/*!40000 ALTER TABLE `tb_abasfundoautomatico` DISABLE KEYS */;
INSERT INTO `tb_abasfundoautomatico` VALUES (1,'Tampa 3 Abas','convidado','2021-07-16 16:46:39'),(2,'Tampa 4 Abas','convidado','2021-07-16 16:46:49');
/*!40000 ALTER TABLE `tb_abasfundoautomatico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_abaslateral`
--

DROP TABLE IF EXISTS `tb_abaslateral`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_abaslateral` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(50) DEFAULT NULL,
  `cadastradopor` varchar(100) DEFAULT NULL,
  `datahora` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_abaslateral`
--

LOCK TABLES `tb_abaslateral` WRITE;
/*!40000 ALTER TABLE `tb_abaslateral` DISABLE KEYS */;
INSERT INTO `tb_abaslateral` VALUES (1,'4 Abas','convidado','2021-07-16 16:45:48'),(2,'Reversa Kalix','convidado','2021-07-16 16:46:00'),(3,'Reversa Veropack','convidado','2021-07-16 16:46:14'),(4,'Asa de Avião','convidado','2021-07-16 16:46:25');
/*!40000 ALTER TABLE `tb_abaslateral` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_abasoutros`
--

DROP TABLE IF EXISTS `tb_abasoutros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_abasoutros` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tipo` varchar(35) DEFAULT NULL,
  `cadastradopor` varchar(100) DEFAULT NULL,
  `datahora` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_abasoutros`
--

LOCK TABLES `tb_abasoutros` WRITE;
/*!40000 ALTER TABLE `tb_abasoutros` DISABLE KEYS */;
INSERT INTO `tb_abasoutros` VALUES (1,'4 Abas','convidado','2021-07-16 16:47:05'),(2,'Nenhum','convidado','2021-07-16 16:47:14');
/*!40000 ALTER TABLE `tb_abasoutros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_caminhoarte`
--

DROP TABLE IF EXISTS `tb_caminhoarte`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_caminhoarte` (
  `id` int NOT NULL AUTO_INCREMENT,
  `endereco` varchar(35) DEFAULT NULL,
  `cadastradopor` varchar(100) DEFAULT NULL,
  `datahora` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_caminhoarte`
--

LOCK TABLES `tb_caminhoarte` WRITE;
/*!40000 ALTER TABLE `tb_caminhoarte` DISABLE KEYS */;
INSERT INTO `tb_caminhoarte` VALUES (1,'C:\\Users\\gmg\\Documents\\Fax','convidado','2021-07-19 16:47:17');
/*!40000 ALTER TABLE `tb_caminhoarte` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_conta`
--

DROP TABLE IF EXISTS `tb_conta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_conta` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(35) DEFAULT NULL,
  `senha` varchar(10) DEFAULT NULL,
  `previlegio` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_conta`
--

LOCK TABLES `tb_conta` WRITE;
/*!40000 ALTER TABLE `tb_conta` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_conta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_desenhos`
--

DROP TABLE IF EXISTS `tb_desenhos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_desenhos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `faca` int DEFAULT NULL,
  `comprimento` float(5,2) DEFAULT NULL,
  `largura` float(5,2) DEFAULT NULL,
  `altura` float(5,2) DEFAULT NULL,
  `colagem` varchar(30) DEFAULT NULL,
  `abas` varchar(30) DEFAULT NULL,
  `berco` varchar(30) DEFAULT NULL,
  `promocional` varchar(30) DEFAULT NULL,
  `cadastradopor` varchar(100) DEFAULT NULL,
  `datahora` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `faca` (`faca`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_desenhos`
--

LOCK TABLES `tb_desenhos` WRITE;
/*!40000 ALTER TABLE `tb_desenhos` DISABLE KEYS */;
INSERT INTO `tb_desenhos` VALUES (1,27031,37.50,37.50,104.00,'Lateral','Reversa Veropack','Sem','Não','convidado','2021-07-16 16:49:00'),(2,26816,175.00,33.30,208.67,'Lateral','Asa de Avião','Sem','Não','convidado','2021-07-16 16:50:12'),(3,23505,60.00,60.00,195.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 16:50:59'),(4,27467,39.35,39.35,107.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 16:52:29'),(5,27017,58.10,54.00,93.20,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 16:55:08'),(6,24690,67.70,49.00,159.11,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 16:56:06'),(7,27860,78.00,36.00,113.50,'Lateral','Reversa Veropack','Sem','Não','convidado','2021-07-16 16:58:49'),(8,27803,143.00,95.00,42.00,'Fundo Automático','Tampa 4 Abas','Sem','Não','convidado','2021-07-16 16:59:57'),(9,24687,92.20,49.00,124.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:01:20'),(10,27843,72.00,16.00,73.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:02:01'),(11,25673,72.00,18.00,75.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:02:45'),(12,27909,46.00,46.00,173.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:03:23'),(13,27852,78.50,46.00,148.00,'Lateral','4 Abas','Sem','Não','convidado','2021-07-16 17:04:18'),(14,14655,39.00,37.50,121.50,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:05:05'),(15,26973,56.00,42.00,133.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:05:44'),(16,27865,56.00,31.00,94.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:06:14'),(17,27957,58.00,32.00,112.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:06:58'),(18,28003,46.00,46.00,173.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:07:42'),(19,27965,80.00,40.00,57.00,'Lateral','4 Abas','Com','Não','convidado','2021-07-16 17:08:30'),(20,27930,89.00,29.00,83.00,'Lateral','Reversa Kalix','Com','Não','convidado','2021-07-16 17:09:10'),(21,27521,45.00,35.00,121.00,'Lateral','Reversa Veropack','Com','Não','convidado','2021-07-16 17:13:58'),(22,28009,54.00,30.00,84.00,'Lateral','4 Abas','Sem','Não','convidado','2021-07-16 17:15:05'),(23,27455,47.00,23.50,120.50,'Lateral','Reversa Veropack','Sem','Não','convidado','2021-07-16 17:15:54'),(24,27991,109.75,44.75,145.50,'Lateral','Asa de Avião','Sem','Não','convidado','2021-07-16 17:16:35'),(25,27052,86.50,22.00,122.00,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:17:41'),(26,28058,125.00,41.50,115.00,'Fundo Automático','Tampa 3 Abas','Sem','Não','convidado','2021-07-16 17:20:54'),(27,26969,65.00,45.00,150.00,'Fundo Automático','Tampa 3 Abas','Sem','Não','convidado','2021-07-16 17:21:38'),(28,28017,125.00,65.00,114.00,'Fundo Automático','Tampa 3 Abas','Sem','Não','convidado','2021-07-16 17:22:55'),(29,28263,47.00,36.00,75.00,'Fundo Automático','Tampa 3 Abas','Com','Não','convidado','2021-07-16 17:23:47'),(30,28317,62.00,85.00,122.00,'Lateral','Asa de Avião','Sem','Não','convidado','2021-07-16 17:25:31'),(31,24891,155.00,123.00,81.00,'Fundo Automático','Tampa 3 Abas','Sem','Não','convidado','2021-07-16 17:26:26'),(32,27462,52.00,32.00,105.00,'Fundo Automático','Tampa 3 Abas','Com','Não','convidado','2021-07-16 17:28:33'),(33,18149,95.50,95.50,17.50,'Lateral','Reversa Kalix','Sem','Não','convidado','2021-07-16 17:34:26'),(34,27056,51.00,23.50,77.50,'Outros','Nenhum','Sem','Não','convidado','2021-07-16 17:36:45');
/*!40000 ALTER TABLE `tb_desenhos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_empresa`
--

DROP TABLE IF EXISTS `tb_empresa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_empresa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  `endereco` varchar(50) DEFAULT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `observacao` varchar(200) DEFAULT NULL,
  `cadastradopor` varchar(100) DEFAULT NULL,
  `datahora` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_empresa`
--

LOCK TABLES `tb_empresa` WRITE;
/*!40000 ALTER TABLE `tb_empresa` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_empresa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bdprojetoscad'
--

--
-- Dumping routines for database 'bdprojetoscad'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-23 10:53:17
