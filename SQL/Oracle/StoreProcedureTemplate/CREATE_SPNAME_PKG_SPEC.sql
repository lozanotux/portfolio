/*=======================================================================+
 |                               Signature                               |
 |                                                                       |
 |    Copyright (c) <Year> <Owner>, <City | State>                       |
 |         ALL rights reserved. Use is subject to license terms.         |
 +=======================================================================+
 |
 | FILENAME
 |     File: CREATE_SPNAME_PKG_SPEC.sql - Install Package Specs
 |
 | DESCRIPTION
 |     Create specs for package SPNAME_PKG.
 |
 | SOURCE CONTROL
 |     Author: <author fullname>
 |     Revision: <revision version>
 |     Date: <Creation date: 01-JAN-2018>
 |
 | HISTORY
 |     Log:
 |     FECHA              DESCRIPCION                    RELEASE  
 |     01-JAN-2018        Version inicial                1.0
 *=======================================================================*/
--------------------------------------------------------
--  DDL for Package Specs SPNAME_PKG
--------------------------------------------------------
CREATE OR REPLACE PACKAGE "SPNAME_PKG" AS
  PROCEDURE procedureFunction
  (
--  p of parameter                   type  dataType
    p_ID_customer                    IN    CUSTOMERS.ID_CUSTOMER%TYPE,
    x_ID_customer                    OUT   CUSTOMERS.ID_CUSTOMER%TYPE,
    x_arrayDynamic                   OUT   sys_refcursor,
    x_return_code                    OUT   NUMBER,
    x_return_message                 OUT   VARCHAR2   
  ); 
END SPNAME_PKG;