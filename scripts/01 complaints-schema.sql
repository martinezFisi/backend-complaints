CREATE TABLE complaints.parameter (
    parameter_id   INTEGER NOT NULL,
    code           VARCHAR2(255) NOT NULL,
    value          VARCHAR2(255) NOT NULL
);

ALTER TABLE complaints.parameter ADD CONSTRAINT parameter_pk PRIMARY KEY ( parameter_id );

CREATE TABLE complaints.citizen (
    citizen_id           INTEGER NOT NULL,
    email             VARCHAR2(255) NOT NULL,
    password          VARCHAR2(255) NOT NULL,
    document_type     VARCHAR2(1) NOT NULL,
    document_number   VARCHAR2(255) NOT NULL,
    first_name        VARCHAR2(255) NOT NULL,
    last_name         VARCHAR2(255) NOT NULL,
    age               INTEGER
);

ALTER TABLE complaints.citizen ADD CONSTRAINT citizen_pk PRIMARY KEY ( citizen_id );

CREATE TABLE complaints.complaint (
    complaint_id     INTEGER NOT NULL,
    address          VARCHAR2(255) NOT NULL,
    latitude         VARCHAR2(255) NOT NULL,
    longitude        VARCHAR2(255) NOT NULL,
    complaint_type   VARCHAR2(255) NOT NULL,
    commentary       VARCHAR2(255) NOT NULL,
    citizen_id          INTEGER NOT NULL
);

ALTER TABLE complaints.complaint ADD CONSTRAINT complaint_pk PRIMARY KEY ( complaint_id );

ALTER TABLE complaints.complaint
    ADD CONSTRAINT complaint_citizen_fk FOREIGN KEY ( citizen_id )
        REFERENCES complaints.citizen ( citizen_id );

CREATE SEQUENCE CITIZEN_SEQ START WITH 1 INCREMENT BY 5;
CREATE SEQUENCE COMPLAINT_SEQ START WITH 1 INCREMENT BY 5;

