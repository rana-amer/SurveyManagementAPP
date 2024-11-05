drop schema if exists survey_schema;
create schema if not exists survey_schema;
use survey_schema;

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
INSERT INTO survey (id, title, description, created_on, status_id, start_date, end_date, closed_on)
VALUES (1, 'Survey 1', 'Description of Survey 1', '2023-08-20', 1, '2023-09-15', '2023-10-30', '2023-08-30'),
       (2, 'Survey 2', 'Description of Survey 2', '2023-08-20', 2, '2023-09-6', '2023-10-05', null),
       (3, 'Survey 3', 'Description of Survey 3', '2023-08-20', 3, '2023-09-1', '2023-10-10', '2023-09-10');
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

-- Create question table
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
INSERT INTO question (id, survey_id, question_type_id, question_text, is_mandatory, question_order,
                      is_selecting_multi_choice_allowed)
VALUES (1, 1, 3, 'Please provide your feedback:', 1, 1, 0),
       (2, 1, 3, 'What did you like about the event?', 0, 2, 0),
       (3, 1, 2, 'Did you find the information useful?', 1, 3, 0),
       (4, 1, 1, 'MCQRate your overall satisfaction:', 1, 4, 1),

       (5, 2, 1, 'MCQThe effects of junk food may include', 1, 1, 0),
       (6, 2, 1, 'MCQJunk food can include these kinds of food', 1, 2, 0),
       (7, 2, 1, 'MCQJunk food is related to stress eating', 1, 3, 0),
       (8, 2, 2, 'Gravity pulls objects towards the sky, is it true?', 1, 4, 0),
       (9, 2, 3, 'Any additional comments?', 0, 5, 0),
       (10, 2, 3, 'Suggestions for improvement?', 0, 6, 0),
       (11, 2, 3, 'What did you like the most?', 0, 7, 0),


       (12, 3, 1, 'Is the moon made of cheese?', 1, 1, 0),
       (13, 3, 2, 'Does a day have 24 hours?', 1, 2, 0),
       (14, 3, 2, 'Is lightning hotter than the sun?', 1, 3, 0),
       (15, 3, 3, 'Share your thoughts:', 0, 4, 0),
       (16, 3, 3, 'Any suggestions for future surveys?', 0, 5, 0);
CREATE TABLE question_choice
(
    id          INT auto_increment  NOT NULL,
    question_id INT  NOT NULL,
    choice      VARCHAR(300) NOT NULL,
    choice_order INT NOT NULL,
    CONSTRAINT pk_question_choice PRIMARY KEY (id),
    CONSTRAINT fk_question_choice_question FOREIGN KEY (question_id) REFERENCES question (id)
);
INSERT INTO question_choice (id, question_id, choice, choice_order)
VALUES (1, 4, 'No direct effect', 1),
       (2, 4, 'Big effect', 2),
       (3, 4, 'Unsure', 3),
       (4, 5, 'Eating alot', 1),
       (5, 5, 'Stress food', 2),
       (6, 5, 'None', 3),
       (7, 6, 'Sweets', 1),
       (8, 6, 'Icecream', 2),
       (9, 6, 'None', 3),
       (10, 7, 'Junk food is unhealthy', 1),
       (11, 7, 'no escape from junk', 2),
       (12, 7, 'none', 3),
       (13, 12, 'Eating alot is not good for the health', 1),
       (14, 12, 'Stress eating is a disease', 2),
       (15, 12, 'none', 3);


-- Create user table
CREATE TABLE user
(
    id        INT     auto_increment     NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    username  VARCHAR(50)  NOT NULL,
    email     VARCHAR(100) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

-- Insert user entries
INSERT INTO user (id, full_name, username, email)
VALUES (1, 'user 1', 'user1', 'user1@example.com'),
       (2, 'user 2', 'user2', 'user2@example.com'),
       (3, 'user 3', 'user3', 'user3@example.com'),
       (4, 'user 4', 'user4', 'user4@example.com'),
       (5, 'user 5', 'user5', 'user5@example.com'),
       (6, 'user 6', 'user6', 'user6@example.com'),
       (7, 'user 7', 'user7', 'user7@example.com'),
       (8, 'user 8', 'user8', 'user8@example.com'),
       (9, 'user 9', 'user9', 'user9@example.com'),
       (10, 'user 10', 'user10', 'user10@example.com');
CREATE TABLE survey_answer_status
(
    id    INT         NOT NULL,
    title VARCHAR(50) NOT NULL,
    CONSTRAINT pk_survey_answer_status PRIMARY KEY (id)
);

INSERT INTO survey_answer_status (id, title)
VALUES (1, 'In Progress'),
       (2, 'Submitted'),
       (3, 'Expired');
-- Create survey_distribution table
CREATE TABLE survey_distribution
(
    id                      INT auto_increment NOT NULL,
    survey_id               INT  NOT NULL,
    user_id                 INT  NOT NULL,
    survey_answer_status_id INT  NOT NULL,
    created_on              DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    submit_on               DATETIME,
    CONSTRAINT pk_survey_distribution PRIMARY KEY (id),
    CONSTRAINT fk_survey_distribution_survey FOREIGN KEY (survey_id) REFERENCES survey (id),
    CONSTRAINT fk_survey_distribution_user FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_survey_distribution_status FOREIGN KEY (survey_answer_status_id) REFERENCES survey_answer_status (id)
);

-- Insert survey_distribution entries
INSERT INTO survey_distribution
(id, survey_id, user_id, survey_answer_status_id, created_on, submit_on)
VALUES
    -- Survey 1 Distribution
    (1, 1, 1, 1, '2023-08-20', '2023-9-20'),
    (2, 1, 2, 2, '2023-08-20', '2023-09-20'),

    -- Survey 2 Distribution
    (3, 2, 1, 1, '2023-08-10', '2023-09-30'),
    (4, 2, 3, 2, '2023-08-20', '2023-09-10'),
    (5, 2, 5, 3, '2023-08-20', '2023-09-13'),
    (6, 2, 6, 1, '2023-08-20', '2023-09-17'),
    (7, 2, 10, 2, '2023-08-20', '2023-9-13'),

    -- Survey 3 Distribution
    (8, 3, 4, 1, '2023-08-10', '2023-09-15'),
    (9, 3, 5, 2, '2023-08-20', '2023-08-20'),
    (10, 3, 7, 3, '2023-08-22', '2023-08-22'),
    (11, 3, 8, 1, '2023-08-23', '2023-08-22'),
    (12, 3, 9, 1, '2023-08-24', '2023-08-22'),
    (13, 3, 10, 2, '2023-08-25', '2023-08-22');

CREATE TABLE question_answer
(
    id                     INT auto_increment NOT NULL,
    survey_distribution_id INT NOT NULL,
    question_id            INT NOT NULL,
    answer                 TEXT NOT NULL,
    CONSTRAINT pk_question_answer PRIMARY KEY (id),
    CONSTRAINT fk_question_answer_distribution FOREIGN KEY (survey_distribution_id) REFERENCES survey_distribution (id),
    CONSTRAINT fk_question_answer_question FOREIGN KEY (question_id) REFERENCES question (id)
);

INSERT INTO question_answer (id, survey_distribution_id, question_id, answer)
VALUES
    -- User 1, Survey 1 Answers
    (1, 1, 1, 'Good'),
    (2, 1, 2, 'The speakers were knowledgeable.'),
    (3, 1, 3, 'Yes'),
    (4, 1, 4, 'Very Satisfied'),
    -- User 2, Survey 1 Answers
    (5, 2, 1, 'Average'),
    (6, 2, 2, 'I found the event to be informative.'),
    (7, 2, 3, 'Yes'),
    (8, 2, 4, 'Satisfied'),
    -- User 1, Survey 2 Answers
    (9, 3, 5, 'No'),
    (10, 3, 6, 'Yes'),
    (11, 3, 7, 'False'),
    (12, 3, 8, 'False'),
    (13, 3, 9, 'No Comments'),
    (14, 3, 10, 'Keep up the good work!'),
    (15, 3, 11, 'The variety of topics was impressive.'),

    -- User 3, Survey 2 Answers - Continued
    (16, 4, 5, 'Yes'),
    (17, 4, 6, 'No'),
    (18, 4, 7, 'True'),
    (19, 4, 8, 'False'),
    (20, 4, 9, 'No additional comments'),
    (21, 4, 10, 'I enjoyed the variety of topics.'),
    (22, 4, 11, 'The event was well-organized.'),
    -- User 4, Survey 2 Answers
    (23, 5, 5, 'Yes'),
    (24, 5, 6, 'Yes'),
    (25, 5, 7, 'False'),
    (26, 5, 8, 'False'),
    (27, 5, 9, 'No additional comments'),
    (28, 5, 10, 'I found the event to be informative.'),
    (29, 5, 11, 'The speakers were knowledgeable.'),
    -- User 5, Survey 2 Answers
    (30, 6, 5, 'Yes'),
    (31, 6, 6, 'No'),
    (32, 6, 7, 'False'),
    (33, 6, 8, 'True'),
    (34, 6, 9, 'No additional comments'),
    (35, 6, 10, 'The event was engaging and informative.'),
    (36, 6, 11, 'The variety of topics was impressive.'),
    -- User 6, Survey 2 Answers
    (37, 7, 5, 'No'),
    (38, 7, 6, 'Yes'),
    (39, 7, 7, 'True'),
    (40, 7, 8, 'False'),
    (41, 7, 9, 'No additional comments'),
    (42, 7, 10, 'I appreciated the interactive sessions.'),
    (43, 7, 11, 'The event had a good mix of speakers.'),
    -- User 7, Survey 3 Answers
    (44, 8, 12, 'No'),
    (45, 8, 13, 'Yes'),
    (46, 8, 14, 'False'),
    (47, 8, 15, 'False'),
    (48, 8, 16, 'No additional comments'),
    -- User 8, Survey 3 Answers
    (49, 9, 12, 'Yes'),
    (50, 9, 13, 'No'),
    (51, 9, 14, 'True'),
    (52, 9, 15, 'True'),
    (53, 9, 16, 'No additional comments'),
    -- User 9, Survey 3 Answers
    (54, 10, 12, 'Yes'),
    (55, 10, 13, 'Yes'),
    (56, 10, 14, 'False'),
    (57, 10, 15, 'True'),
    (58, 10, 16, 'No additional comments'),
    -- User 10, Survey 3 Answers
    (59, 11, 12, 'Yes'),
    (60, 11, 13, 'No'),
    (61, 11, 14, 'True'),
    (62, 11, 15, 'True'),
    (63, 11, 16, 'No additional comments')