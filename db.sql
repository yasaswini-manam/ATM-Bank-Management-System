CREATE DATABASE bankmanagementsystem;
SHOW DATABASES;
USE bankmanagementsystem;

CREATE TABLE signup (
    form_no VARCHAR(20), 
    name VARCHAR(20), 
    Father_Name VARCHAR(40), 
    dob VARCHAR(40), 
    Gender VARCHAR(20), 
    Email VARCHAR(50), 
    Marital_Status VARCHAR(40), 
    Address VARCHAR(150), 
    city VARCHAR(80), 
    State VARCHAR(80), 
    Nationality VARCHAR(40)
);
CREATE TABLE signup2 (
    form_no VARCHAR(20), 
    Religion VARCHAR(40), 
    Category VARCHAR(40), 
    Income VARCHAR(40), 
    Educational_Qualif VARCHAR(20), 
    Occupation VARCHAR(40), 
    Pan_Card VARCHAR(50), 
    AadharCard VARCHAR(40), 
    Citizen VARCHAR(40), 
    Existing_Acc VARCHAR(40)
);
CREATE TABLE signup3 (
    form_no VARCHAR(20),
    account_type VARCHAR(50),
    cardno VARCHAR(50),
    pin VARCHAR(50),
    facility VARCHAR(100)
);

CREATE TABLE login (
form_no varchar(50),
    cardno VARCHAR(50),
    pin VARCHAR(50)
);


CREATE TABLE bank (
    pin VARCHAR(10),
    mode varchar(50),
    date VARCHAR(100),
    amount VARCHAR(20)
);

SELECT * FROM signup;
SELECT * FROM signup2;
SELECT * FROM signup3;
SELECT * FROM login;
SELECT * FROM bank;



drop table signup;
drop table signup2;
drop table signup3;
drop table login;
drop table bank;

