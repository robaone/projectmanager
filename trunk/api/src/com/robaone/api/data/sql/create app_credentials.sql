delimiter $$

CREATE TABLE `app_credentials` (
  `idapp_credentials` int(11) NOT NULL AUTO_INCREMENT COMMENT '	',
  `request_token` varchar(128) NOT NULL,
  `access_token` varchar(128) DEFAULT NULL,
  `created_by` varchar(128) DEFAULT NULL,
  `creation_host` varchar(32) DEFAULT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '0',
  `idapps` int(11) NOT NULL,
  `iduser` int(11) DEFAULT NULL,
  `token_secret` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`idapp_credentials`),
  KEY `idcred_app` (`idapps`),
  KEY `idcred_user` (`iduser`),
  CONSTRAINT `idcred_app` FOREIGN KEY (`idapps`) REFERENCES `apps` (`idapps`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idcred_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=latin1$$

