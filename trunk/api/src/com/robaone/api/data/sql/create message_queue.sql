delimiter $$

CREATE TABLE `message_queue` (
  `idmessage_queue` int(11) NOT NULL AUTO_INCREMENT,
  `creationdate` datetime NOT NULL,
  `subject` varchar(128) DEFAULT NULL,
  `from` varchar(128) NOT NULL,
  `to` varchar(256) NOT NULL,
  `cc` varchar(256) DEFAULT NULL,
  `bcc` varchar(256) DEFAULT NULL,
  `body` text NOT NULL,
  `html` int(11) NOT NULL DEFAULT '0',
  `attachments` text,
  PRIMARY KEY (`idmessage_queue`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1$$

