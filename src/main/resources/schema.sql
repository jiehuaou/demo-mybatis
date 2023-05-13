
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

insert into emp (id, first_name, last_name, career, tel, addr, dep_id) values
 (1, 'first1', 'last1', 'writer',  '123-1', 'CA', 1)
,(2, 'first2', 'last2', 'writer',  '123-2', 'CA', 1)
,(3, 'first3', 'last3', 'student', '123-3', 'CA', 1)
,(4, 'first4', 'last4', 'techer',  '123-4', 'CA', 2)
,(5, 'first5', 'last5', 'writer',  '123-5', 'CA', 2)
,(6, 'first6', 'last3', 'student', '123-6', 'CA', 2)
,(7, 'first7', 'last4', 'techer',  '123-7', 'CA', 3)
,(8, 'first8', 'last5', 'writer',  '123-8', 'CA', null)
;

commit;

DROP TABLE IF EXISTS task;

CREATE TABLE task(
  id INT AUTO_INCREMENT PRIMARY KEY,
  job VARCHAR(50) NOT NULL ,
  emp_id int DEFAULT NULL
);

insert into task (job, emp_id) values
    ('sport-1', 1),
    ('motor-2', 1),
    ('sport-3', 2),
    ('motor-4', 2),
    ('report-5', 3),
    ('develop-6', 3);

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


