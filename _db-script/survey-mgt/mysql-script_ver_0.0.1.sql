drop schema if exists client_platform_survey_mgt2;
create schema if not exists client_platform_survey_mgt2;
use client_platform_survey_mgt2;
CREATE TABLE question_type
(
    id    INT         NOT NULL,
    title VARCHAR(50) NOT NULL,
    CONSTRAINT pk_question_type PRIMARY KEY (id)
);

-- Insert question_type entries
INSERT INTO question_type (id, title)
VALUES (1, 'MCQ'),
       (2, 'True & False'),
       (3, 'Free Text');
create table survey_status
(
    id    int         not null,
    title varchar(30) not null,
    constraint pk_survey_status primary key (id)
);

INSERT INTO survey_status (id, title)
VALUES (1, 'New'),
       (2, 'In Progress'),
       (3, 'Closed');
create table survey
(
    id         int   auto_increment      not null,
    title      varchar(150) not null,
    description    varchar(1000)        not null,
    created_on DATETIME DEFAULT CURRENT_TIMESTAMP      not null,
    status_id  int         not null,
    start_date DATE,
    end_date   DATE,
    closed_on  DATETIME,
    constraint pk_survey primary key (id),
    constraint fk_survey_status foreign key (status_id) references survey_status (id)
);
-- INSERT INTO survey (id, title, description, created_on, status_id, start_date, end_date, closed_on)
-- VALUES (1, 'Survey 1', 'Description of Survey 1', '2023-08-20', 1, '2023-09-15', '2023-10-30', null),
--        (2, 'Survey 2', 'Description of Survey 2', '2023-08-20', 2, '2023-09-6', '2023-10-05', null),
--        (3, 'Survey 3', 'Description of Survey 3', '2023-08-20', 3, '2023-09-1', '2023-10-10', null);

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


CREATE TABLE survey_distribution
(
    id                     INT NOT NULL AUTO_INCREMENT,
    survey_id               INT  NOT NULL,
    user_id                 INT  NOT NULL,
    created_on              DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    submit_on               DATETIME,
    CONSTRAINT pk_survey_distribution PRIMARY KEY (id),
    CONSTRAINT fk_survey_distribution_survey FOREIGN KEY (survey_id) REFERENCES survey (id),
    CONSTRAINT fk_survey_distribution_user FOREIGN KEY (user_id) REFERENCES user (id)
);
CREATE TABLE question
(
    id                                INT  auto_increment   NOT NULL,
    survey_id                         INT     NOT NULL,
    question_type_id                  INT     NOT NULL,
    question_text                     VARCHAR(500)    NOT NULL,
    is_mandatory                      INT 	  NOT NULL,
    question_order                    INT     NOT NULL,
    is_selecting_multi_choice_allowed INT DEFAULT 0 NOT NULL,
    CONSTRAINT pk_question PRIMARY KEY (id),
    CONSTRAINT fk_question_survey FOREIGN KEY (survey_id) REFERENCES survey (id),
    CONSTRAINT fk_question_question_type FOREIGN KEY (question_type_id) REFERENCES question_type (id)
);

-- Insert question entries
-- INSERT INTO question (id, survey_id, question_type_id, question_text, is_mandatory, question_order,
--                       is_selecting_multi_choice_allowed)
-- VALUES (1, 1, 3, 'Please provide your feedback:', 1, 1, 0),
--        (2, 1, 3, 'What did you like about the event?', 0, 2, 0),
--        (3, 1, 2, 'Did you find the information useful?', 1, 3, 0),
--        (4, 1, 1, 'MCQRate your overall satisfaction:', 1, 4, 1),
--
--        (5, 2, 1, 'MCQThe effects of junk food may include', 1, 1, 0),
--        (6, 2, 1, 'MCQJunk food can include these kinds of food', 1, 2, 0),
--        (7, 2, 1, 'MCQJunk food is related to stress eating', 1, 3, 0),
--        (8, 2, 2, 'Gravity pulls objects towards the sky, is it true?', 1, 4, 0),
--        (9, 2, 3, 'Any additional comments?', 0, 5, 0),
--        (10, 2, 3, 'Suggestions for improvement?', 0, 6, 0),
--        (11, 2, 3, 'What did you like the most?', 0, 7, 0),
--
--
--        (12, 3, 1, 'Is the moon made of cheese?', 1, 1, 0),
--        (13, 3, 2, 'Does a day have 24 hours?', 1, 2, 0),
--        (14, 3, 2, 'Is lightning hotter than the sun?', 1, 3, 0),
--        (15, 3, 3, 'Share your thoughts:', 0, 4, 0),
--        (16, 3, 3, 'Any suggestions for future surveys?', 0, 5, 0);
CREATE TABLE question_choice
(
    id          INT auto_increment  NOT NULL,
    question_id INT  NOT NULL,
    choice      VARCHAR(300) NOT NULL,
    choice_order INT NOT NULL,
    CONSTRAINT pk_question_choice PRIMARY KEY (id),
    CONSTRAINT fk_question_choice_question FOREIGN KEY (question_id) REFERENCES question (id)
);
-- INSERT INTO question_choice (id, question_id, choice, choice_order)
-- VALUES (1, 4, 'No direct effect', 1),
--        (2, 4, 'Big effect', 2),
--        (3, 4, 'Unsure', 3),
--        (4, 5, 'Eating alot', 1),
--        (5, 5, 'Stress food', 2),
--        (6, 5, 'None', 3),
--        (7, 6, 'Sweets', 1),
--        (8, 6, 'Icecream', 2),
--        (9, 6, 'None', 3),
--        (10, 7, 'Junk food is unhealthy', 1),
--        (11, 7, 'no escape from junk', 2),
--        (12, 7, 'none', 3),
--        (13, 12, 'Eating alot is not good for the health', 1),
--        (14, 12, 'Stress eating is a disease', 2),
--        (15, 12, 'none', 3);
ALTER TABLE question_choice
    MODIFY COLUMN choice_order INT NULL;
CREATE TABLE survey_answers (
                                id INT NOT NULL AUTO_INCREMENT,
                                survey_distribution_id INT NOT NULL,
                                value VARCHAR(255),
                                PRIMARY KEY (id),
                                FOREIGN KEY (survey_distribution_id) REFERENCES survey_distribution (id)
);