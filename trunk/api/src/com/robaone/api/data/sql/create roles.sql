delimiter $$

CREATE TABLE `roles` (
  `idroles` int(11) NOT NULL AUTO_INCREMENT,
  `iduser` int(11) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`idroles`),
  UNIQUE KEY `unique_roles` (`iduser`,`role`),
  KEY `iduser_role` (`iduser`),
  CONSTRAINT `iduser_role` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1$$

