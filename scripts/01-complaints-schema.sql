SET search_path TO complaints_schema;

CREATE TABLE parameter
(
    parameter_id BIGINT      NOT NULL,
    code         VARCHAR(255) NOT NULL,
    value        VARCHAR(255) NOT NULL
);

ALTER TABLE parameter
    ADD CONSTRAINT parameter_pk PRIMARY KEY (parameter_id);

CREATE TABLE citizen
(
    citizen_id      BIGINT      NOT NULL,
    email           VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    document_type   VARCHAR(1)   NOT NULL,
    document_number VARCHAR(255) NOT NULL,
    first_name      VARCHAR(255) NOT NULL,
    last_name       VARCHAR(255) NOT NULL,
    age             INTEGER
);

ALTER TABLE citizen
    ADD CONSTRAINT citizen_pk PRIMARY KEY (citizen_id);

CREATE TABLE complaint
(
    complaint_id   BIGINT      NOT NULL,
    address        VARCHAR(255) NOT NULL,
    latitude       VARCHAR(255) NOT NULL,
    longitude      VARCHAR(255) NOT NULL,
    complaint_type VARCHAR(255) NOT NULL,
    commentary     VARCHAR(255) NOT NULL,
    citizen_id     BIGINT      NOT NULL
);

ALTER TABLE complaint
    ADD CONSTRAINT complaint_pk PRIMARY KEY (complaint_id);

ALTER TABLE complaint
    ADD CONSTRAINT complaint_citizen_fk FOREIGN KEY (citizen_id)
        REFERENCES citizen (citizen_id);

CREATE SEQUENCE CITIZEN_SEQ START WITH 1 INCREMENT BY 5;
ALTER SEQUENCE CITIZEN_SEQ OWNER TO COMPLAINTS_USER;

CREATE SEQUENCE COMPLAINT_SEQ START WITH 1 INCREMENT BY 5;
ALTER SEQUENCE COMPLAINT_SEQ OWNER TO COMPLAINTS_USER;

GRANT ALL PRIVILEGES ON SCHEMA complaints_schema to complaints_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA complaints_schema TO complaints_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA complaints_schema TO complaints_user;