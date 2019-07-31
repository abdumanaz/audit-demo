-- AUDIT TRAIL DDL
create table AUDIT_TRAIL (
AUDIT_ID NUMBER PRIMARY KEY,
USER_ID VARCHAR2(10) NOT NULL,
MODULE VARCHAR2(10) NOT NULL,
FUNCTION_NAME VARCHAR2(50) NOT NULL,
INVOCATION_DATE TIMESTAMP NOT NULL,
PARAMS CLOB
);
-- SEQUENCE DDL
CREATE SEQUENCE AUDIT_TRAIL_ID_SEQ START WITH 1 MINVALUE 1 MAXVALUE 9999999 INCREMENT BY 1 NOCACHE NOCYCLE;

--DROP TABLE COMMAND
drop table audit_trail;

--SELECT TABLE COMMAND
select * from audit_trail;