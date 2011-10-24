delimiter $$

CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL,
  `first_name` varchar(128) DEFAULT NULL,
  `last_name` varchar(128) DEFAULT NULL,
  `failed_attempts` int(1) NOT NULL DEFAULT '0',
  `password` varchar(128) DEFAULT NULL,
  `active` int(1) DEFAULT '0',
  `modified_by` varchar(128) DEFAULT NULL,
  `created_by` varchar(128) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `creation_host` varchar(32) DEFAULT NULL,
  `modification_host` varchar(32) DEFAULT NULL,
  `meta_data` text,
  `reset` int(1) DEFAULT '0',
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COMMENT='User table'$$

