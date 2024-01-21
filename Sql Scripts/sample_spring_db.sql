CREATE DATABASE  IF NOT EXISTS `sample_spring_db`;
USE `sample_spring_db`;

--
-- Table structure for table `kafka_message_template`
--

DROP TABLE IF EXISTS `kafka_message_template`;

CREATE TABLE `kafka_message_template` (
  `kafka_message_template_id` int NOT NULL AUTO_INCREMENT,
  `message`varchar(45) DEFAULT NULL,
  `message_name`varchar(45) DEFAULT NULL,
  `created_date` datetime DEFAULT current_timestamp,
  PRIMARY KEY (`kafka_message_template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Table structure for table `kafka_message_data`
--

DROP TABLE IF EXISTS `kafka_message_data`;

CREATE TABLE `kafka_message_data` (
  `message_data_id` int NOT NULL AUTO_INCREMENT,
  `user_id`varchar(45) DEFAULT NULL,
  `created_date` datetime DEFAULT current_timestamp,
  `kafka_message_template_id` int DEFAULT NULL,
  PRIMARY KEY (`message_data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;



