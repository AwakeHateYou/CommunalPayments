#
# SQL Export
# Encoding: Unicode (UTF-8)
#


CREATE DATABASE IF NOT EXISTS `Payments` DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;
USE `Payments`;




SET @PREVIOUS_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;



CREATE TABLE `Payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fio` varchar(128) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `price_done` double NOT NULL DEFAULT '0',
  `default_pay_type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;




