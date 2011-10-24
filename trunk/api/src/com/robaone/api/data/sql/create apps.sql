delimiter $$

CREATE TABLE `apps` (
  `idapps` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `callback_url` varchar(256) NOT NULL,
  `description` text,
  `consumer_key` varchar(128) DEFAULT NULL,
  `consumer_secret` varchar(128) DEFAULT NULL,
  `active` int(11) DEFAULT '0',
  `iduser` int(11) NOT NULL,
  `modified_by` varchar(32) DEFAULT NULL,
  `created_by` varchar(32) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  `creation_host` varchar(32) DEFAULT NULL,
  `modification_host` varchar(32) DEFAULT NULL,
  `meta_data` text,
  PRIMARY KEY (`idapps`),
  KEY `app_iduser` (`iduser`),
  CONSTRAINT `app_iduser` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1$$

