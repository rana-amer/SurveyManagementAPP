use survey_schema;

ALTER TABLE user
    ADD created_on DATETIME DEFAULT CURRENT_TIMESTAMP not null;