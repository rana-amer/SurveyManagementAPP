drop schema if exists client_platform_ntf_mgt;
create schema if not exists client_platform_ntf_mgt;
use client_platform_ntf_mgt;

CREATE TABLE user (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `username` VARCHAR(45) NOT NULL,
                      `email` VARCHAR(200) NOT NULL,
                      `user_password` VARCHAR(100) NULL,
                      `full_name` VARCHAR(100) NOT NULL,
                      `mobile` VARCHAR(45) NULL,
                      `is_active` INT NULL DEFAULT 0,
                      `gender` VARCHAR(20) NULL,
                      `created_on` DATETIME NULL,
                      PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `event` (
    `id` INT NOT NULL,
    `title` VARCHAR(200) NOT NULL,
    `body` VARCHAR(200) NOT NULL,
    `subject` VARCHAR(200) NOT NULL,
    PRIMARY KEY (`id`));

CREATE TABLE IF NOT EXISTS `notification_request` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `event_id` INT NOT NULL,
    `entity_id` INT NOT NULL,
    `created_on` DATETIME,
    `actor_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_event`
    FOREIGN KEY (`event_id`)
    REFERENCES `event` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    );

CREATE TABLE IF NOT EXISTS `notification` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `notification_request_id` INT NOT NULL,
    `notifier_id` INT NOT NULL,
    `body` VARCHAR(200) NOT NULL,
    `subject` VARCHAR(200) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_notification_request`
    FOREIGN KEY (`notification_request_id`)
    REFERENCES `notification_request` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_notification_notifier_id`
    FOREIGN KEY (`notifier_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `client_platform_ntf_mgt`.`event` (`id`, `title`, `body`, `subject`) VALUES ('1', 'Create Survey', 'Dear {username}, a new survey with Id: {entity_id} has been created, and its deadline is on {deadline}.', 'New Survey');
INSERT INTO `client_platform_ntf_mgt`.`event` (`id`, `title`, `body`, `subject`) VALUES ('2', 'Update Survey', 'Dear {username}, the survey with Id: {entity_id} has been updated. Please make sure to check the updates.', 'Updated Survey');
INSERT INTO `client_platform_ntf_mgt`.`event` (`id`, `title`, `body`, `subject`) VALUES ('3', 'Delete Survey', 'Dear {username}, unfortunately, the survey with Id: {entity_id} has been deleted!', 'Deleted Survey');
