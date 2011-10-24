delimiter $$

CREATE TABLE `credentials` (
  `idcredentials` int(11) NOT NULL AUTO_INCREMENT,
  `iduser` int(11) NOT NULL,
  `authenticator` varchar(128) NOT NULL,
  `authdata` text NOT NULL,
  `created_by` varchar(128) DEFAULT NULL,
  `modified_by` varchar(128) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `modification_date` datetime DEFAULT NULL,
  `creation_host` varchar(32) DEFAULT NULL,
  `modification_host` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`idcredentials`),
  KEY `iduser` (`iduser`),
  CONSTRAINT `iduser` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=latin1 COMMENT='Additional credentials'$$

