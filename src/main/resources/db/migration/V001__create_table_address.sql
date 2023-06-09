CREATE TABLE ADDRESS (
    ID INT NOT NULL,
    STREET VARCHAR(255),
    NUMBER VARCHAR(255),
    DISTRICT VARCHAR(255),
    CITY VARCHAR(255),
    STATE VARCHAR(255),
    ZIP_CODE VARCHAR(255),
    COUNTRY VARCHAR(255),
    PRIMARY KEY (ID)
);

CREATE SEQUENCE ADDRESS_SEQ START WITH 1 INCREMENT BY 1;
ALTER SEQUENCE ADDRESS_SEQ NOCACHE;