CREATE TABLE IF NOT EXISTS account (
  id        INTEGER         NOT NULL AUTO_INCREMENT,
  username VARCHAR(128)    NOT NULL,
  password   VARCHAR(512)    NOT NULL,
  PRIMARY KEY (id)
);