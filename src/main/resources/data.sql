DROP TABLE IF EXISTS enrollment;

CREATE TABLE enrollment (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(50) NOT NULL
);

INSERT INTO enrollment (name) VALUES
  ('ALIKO'),
  ('BILL'),
  ('HENRY');

DROP TABLE IF EXISTS category;

CREATE TABLE category (
   id INT AUTO_INCREMENT  PRIMARY KEY,
   category_id INT NOT NULL
);

INSERT INTO category (category_id) VALUES
   (4), (5), (7);


DROP TABLE IF EXISTS numeric_rules;

CREATE TABLE numeric_rules (
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    operator VARCHAR(50) NOT NULL,
    attribute_name VARCHAR(50) NOT NULL,
    value DOUBLE NOT NULL
);

INSERT INTO numeric_rules (name, operator, attribute_name, value) VALUES
('price_min','GREATER_THAN','price',160.0);