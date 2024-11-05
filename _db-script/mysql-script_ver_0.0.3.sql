use survey_schema;

ALTER TABLE question
    ADD created_on DATETIME DEFAULT CURRENT_TIMESTAMP not null;