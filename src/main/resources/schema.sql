
CREATE SEQUENCE SEQ_ID START WITH 1000;

DROP TABLE IF EXISTS emp;

CREATE TABLE emp (
  id INT  PRIMARY KEY,
  version INT DEFAULT 1,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  career VARCHAR(250) DEFAULT NULL,
  tel VARCHAR(50) DEFAULT NULL,
  addr VARCHAR(50) DEFAULT NULL,
  data double default 0,
  dep_id int DEFAULT NULL
);

insert into emp (id, first_name, last_name, career, tel, addr, dep_id) values(1, 'first1', 'last1', 'writer', '1231', 'CA', 1);
insert into emp (id, first_name, last_name, career, tel, addr, dep_id) values(2, 'first2', 'last2', 'writer', '1232', 'CA', 1);
insert into emp (id, first_name, last_name, career, tel, addr, dep_id) values(3, 'first3', 'last3', 'writer', '1233', 'CA', 1);
insert into emp (id, first_name, last_name, career, tel, addr, dep_id) values(4, 'first4', 'last4', 'writer', '1234', 'CA', 2);
insert into emp (id, first_name, last_name, career, tel, addr, dep_id) values(5, 'first5', 'last5', 'writer', '1235', 'CA', 2);

commit;


DROP TABLE IF EXISTS dep;

CREATE TABLE dep(
  id INT PRIMARY KEY,
  VERSION INT DEFAULT 1,
  dep_name VARCHAR(50) NOT NULL ,
  leader  VARCHAR(50)
);

insert into dep (id, dep_name, leader) values
    (1, 'sport', 'John'),
    (2, 'motor', 'Jeff'),
    (3, 'weather', 'Jeff'),
    (4, 'report', 'Jean'),
    (101, 'accounting', 'John'),
    (102, 'transportation', 'Joe')  ;


commit;
