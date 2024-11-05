use survey_schema;

ALTER TABLE `user`
    ADD COLUMN `mobile`        VARCHAR(20)  NOT NULL AFTER `email`,
    ADD COLUMN `is_active`     INT          NOT NULL AFTER `mobile`,
    ADD COLUMN `user_password` VARCHAR(100) NOT NULL AFTER `is_active`,
    ADD COLUMN `gender`        VARCHAR(20)  NOT NULL AFTER `user_password`;