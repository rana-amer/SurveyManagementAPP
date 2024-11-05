drop schema if exists client_platform_user_mgt;
create schema if not exists client_platform_user_mgt;
use client_platform_user_mgt;

CREATE TABLE user (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `username` VARCHAR(45) NOT NULL,
                      `email` VARCHAR(200) NOT NULL,
                      `user_password` VARCHAR(100) NOT NULL,
                      `full_name` VARCHAR(100) NOT NULL,
                      `mobile` VARCHAR(45) NULL,
                      `is_active` INT NULL DEFAULT 1,
                      `gender` VARCHAR(20) NULL,
                      `created_on` DATETIME NULL,
                      PRIMARY KEY (`id`)
);
INSERT INTO `user` (`id`, `username`, `email`, `user_password`, `full_name`, `is_active`) VALUES ('1', 'admin', 'admin@gmail.com', '$2a$10$tEHnDGCs9eMZamnyptotdOFlzCrdH5kSu7ofAUTFo4CaRjwCHOWra', 'admin', '1');

CREATE TABLE `role` (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `title` VARCHAR(45) NOT NULL,
                        PRIMARY KEY (`id`));
INSERT INTO `role` (`id`, `title`) VALUES ('1', 'admin');
INSERT INTO `role` (`id`, `title`) VALUES ('2', 'manager');
INSERT INTO `role` (`id`, `title`) VALUES ('3', 'client');

CREATE TABLE `user-role` (
                             `user_id` INT NOT NULL,
                             `role_id` INT NOT NULL,
                             PRIMARY KEY (`user_id`, `role_id`),
                             INDEX `role_id_idx` (`role_id` ASC) VISIBLE,
                             CONSTRAINT `user_id`
                                 FOREIGN KEY (`user_id`)
                                     REFERENCES `client_platform_user_mgt`.`user` (`id`)
    ,
                             CONSTRAINT `role_id`
                                 FOREIGN KEY (`role_id`)
                                     REFERENCES `client_platform_user_mgt`.`role` (`id`)
);
INSERT INTO `user-role` (`user_id`, `role_id`) VALUES ('1', '1');





