CREATE TABLE IF NOT EXISTS `statuses` (
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`value`)
);
INSERT INTO `statuses`
  VALUES('Created');
INSERT INTO `statuses`
  VALUES('In Development');
INSERT INTO `statuses`
  VALUES('On DEV');
INSERT INTO `statuses`
  VALUES('QA Done on DEV');
INSERT INTO `statuses`
  VALUES('On staging');
INSERT INTO `statuses`
  VALUES('QA done on STAGING');
INSERT INTO `statuses`
  VALUES('On PROD');
INSERT INTO `statuses`
  VALUES('Done');
CREATE TABLE IF NOT EXISTS `releases` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `releaseDate` DATE NOT NULL,
  `createdAt` DATETIME NOT NULL,
  `lastUpdatedAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

