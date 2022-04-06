CREATE TABLE IF NOT EXISTS `neon`.`statuses` (
  `value` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`value`)
);
INSERT IGNORE INTO `neon`.`statuses`
  VALUES('Created');
INSERT IGNORE INTO `neon`.`statuses`
  VALUES('In Development');
INSERT IGNORE INTO `neon`.`statuses`
  VALUES('On DEV');
INSERT IGNORE INTO `neon`.`statuses`
  VALUES('QA Done on DEV');
INSERT IGNORE INTO `neon`.`statuses`
  VALUES('On staging');
INSERT IGNORE INTO `neon`.`statuses`
  VALUES('QA done on STAGING');
INSERT IGNORE INTO `neon`.`statuses`
  VALUES('On PROD');
INSERT IGNORE INTO `neon`.`statuses`
  VALUES('Done');
CREATE TABLE IF NOT EXISTS `neon`.`releases` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `releaseDate` DATE NOT NULL,
  `createdAt` DATETIME NOT NULL,
  `lastUpdatedAt` DATETIME NOT NULL,
  PRIMARY KEY (`id`));

