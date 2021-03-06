CREATE DATABASE QLTT;
USE QLTT;

CREATE TABLE STUDENTS (
	STU_ID INT AUTO_INCREMENT,
	STU_EMAIL VARCHAR(100),
    STU_NAME NVARCHAR(100),
    STU_PHONE VARCHAR(10),
    STU_ADDRESS NVARCHAR(100),
    STU_GENDER CHAR(1),
    STU_BIRTHDATE DATE,
    STU_JOINDATE DATE,
    STU_SCHOOL NVARCHAR(100),
	STU_AVATAR VARCHAR(100),
    PRIMARY KEY (STU_ID)
);

CREATE TABLE TEACHERS (
	TEA_ID INT AUTO_INCREMENT,
	TEA_EMAIL VARCHAR(100),
    TEA_NAME NVARCHAR(100),
    TEA_PHONE VARCHAR(10),
    TEA_ADDRESS NVARCHAR(100),
    TEA_GENDER CHAR(1),
    TEA_BIRTHDATE DATE,
    TEA_HIREDATE DATE,
    TEA_SCHOOL NVARCHAR(100),
	TEA_AVATAR VARCHAR(100),
    TEA_SALARYRATE FLOAT,
    PRIMARY KEY (TEA_ID)
);

CREATE TABLE ACCOUNTS (
	ACC_USERNAME VARCHAR(100),
    ACC_PASSWORD VARCHAR(100),
    ACC_ISACTIVE CHAR(1),
    ACC_ROLE INT,
	PRIMARY KEY (ACC_USERNAME)
);

CREATE TABLE SUBJECTS (
	SUB_ID INT AUTO_INCREMENT,
    SUB_NAME NVARCHAR(100),
    SUB_STARTDATE DATE,
    SUB_SCHEDULE NVARCHAR(50),
    SUB_ROOM NVARCHAR(50),
    SUB_TUITION INT,
    TEA_ID INT,
    PRIMARY KEY (SUB_ID)
);

CREATE TABLE STUDENT_SCORES (
	STU_ID INT,
    SUB_ID INT,
    SCORE FLOAT,
    PRIMARY KEY (STU_ID, SUB_ID)
);

CREATE TABLE ANNOUNCEMENTS (
	ANN_ID INT AUTO_INCREMENT,
    ANN_TITLE NVARCHAR(100),
    ANN_TYPE INT,
    ANN_LINK VARCHAR(200),
    SUB_ID INT,
    PRIMARY KEY (ANN_ID)
);

CREATE TABLE SUBMIT_HOMEWORK (
	ANN_ID INT,
    STU_ID INT,
    SUBMIT_DATE DATE,
    SUBMIT_LINK VARCHAR(200),
    PRIMARY KEY (ANN_ID, STU_ID)
);

CREATE TABLE SALARY (
	TEA_ID INT,
    SAL_MONTH INT,
    SUB_ID INT,
    SAL_MONEY INT,
    PRIMARY KEY (TEA_ID, SAL_MONTH, SUB_ID)
);

CREATE TABLE TUITION (
	TUI_DATE DATE,
    STU_ID INT,
    SUB_ID INT,
    TUI_MONEY INT,
    TUI_DESCRIPTION NVARCHAR(200),
    PRIMARY KEY (TUI_DATE, STU_ID, SUB_ID)
);

ALTER TABLE SUBJECTS ADD FOREIGN KEY (TEA_ID) REFERENCES TEACHERS(TEA_ID);

ALTER TABLE STUDENT_SCORES ADD FOREIGN KEY (STU_ID) REFERENCES STUDENTS(STU_ID);
ALTER TABLE STUDENT_SCORES ADD FOREIGN KEY (SUB_ID) REFERENCES SUBJECTS(SUB_ID);

ALTER TABLE ANNOUNCEMENTS ADD FOREIGN KEY (SUB_ID) REFERENCES SUBJECTS(SUB_ID);

ALTER TABLE SUBMIT_HOMEWORK ADD FOREIGN KEY (ANN_ID) REFERENCES ANNOUNCEMENTS(ANN_ID);
ALTER TABLE SUBMIT_HOMEWORK ADD FOREIGN KEY (STU_ID) REFERENCES STUDENTS(STU_ID);

ALTER TABLE SALARY ADD FOREIGN KEY (TEA_ID) REFERENCES TEACHERS(TEA_ID);
ALTER TABLE SALARY ADD FOREIGN KEY (SUB_ID) REFERENCES SUBJECTS(SUB_ID);

ALTER TABLE TUITION ADD FOREIGN KEY (STU_ID) REFERENCES STUDENTS(STU_ID);
ALTER TABLE TUITION ADD FOREIGN KEY (SUB_ID) REFERENCES SUBJECTS(SUB_ID);