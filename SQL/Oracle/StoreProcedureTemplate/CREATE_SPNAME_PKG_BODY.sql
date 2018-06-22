 /*=======================================================================+
 |                               Signature                               |
 |                                                                       |
 |    Copyright (c) <Year> <Owner>, <City | State>                       |
 |         ALL rights reserved. Use is subject to license terms.         |
 +=======================================================================+
 |
 | FILENAME
 |     File: CREATE_SPNAME_PKG_BODY.sql - Install Package Body
 |
 | DESCRIPTION
 |     Create body definition for package SPNAME_PKG.
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
--  DDL for Package Body SPNAME_PKG
--------------------------------------------------------
CREATE OR REPLACE PACKAGE BODY "SPNAME_PKG" AS
  PROCEDURE procedureFunction
  (
    p_ID_customer                    IN    CUSTOMERS.ID_CUSTOMER%TYPE,
    x_ID_customer                    OUT   CUSTOMERS.ID_CUSTOMER%TYPE,
    x_arrayDynamic                   OUT   sys_refcursor,
    x_return_code                    OUT   NUMBER,
    x_return_message                 OUT   VARCHAR2   
  ) AS
    l_null_error_header_message   VARCHAR2(200);
    l_null_error_message          VARCHAR2(200);
    l_null_error_flag             BOOLEAN;
    INVALID_PARAMETERS            EXCEPTION;
  BEGIN

  OPEN x_arrayDynamic FOR SELECT NULL FROM DUAL CLOSE;

  x_return_code := 0;
  x_return_message := 'Success';

  l_null_error_header_message := 'The following parameters must be completed:';
  l_null_error_message := '';
  l_null_error_flag := false;

  IF (p_ID_cliente IS NULL) THEN
    l_null_error_message := l_null_error_message || ' p_ID_customer';
    l_null_error_flag := true;
  END IF;

  IF (length(l_null_error_message) > 0) THEN
    l_null_error_message := l_null_error_header_message || l_null_error_message || '.';
  END IF;

  IF (l_null_error_flag) THEN
    RAISE INVALID_PARAMETERS;
  END IF;

   -- GET CUSTOMER
   BEGIN 
    SELECT c.ID_CUSTOMER
      INTO x_ID_customer                       
      FROM CUSTOMERS c
      WHERE c.ID_CUSTOMER = p_ID_customer;
    
    EXCEPTION
       WHEN NO_DATA_FOUND THEN
         x_return_code := 2;
         x_return_message := 'No records found';
   END;
  
  -- CURSORS BLOCK
  BEGIN
  
    -- GET AND COMPLETE ARRAY DYNAMIC
    OPEN x_arrayDynamic FOR
      SELECT c.ID_CUSTOMER, c.CUSTOMER_FULLNAME, c.CUSTOMER_PHONE, c.PRODUCT
        FROM CUSTOMERS c
        WHERE c.ID_CUSTOMER = p_ID_customer;

    -- OTHERS ARRAYS TO

	-- FINALLY SAVE DATA INTO ARRAYS
    RETURN;
   END;
 
  -- EXCEPTIONS HANDLER
  EXCEPTION 
    WHEN INVALID_PARAMETERS THEN
        x_return_code := 1;
        x_return_message := l_null_error_message;

    WHEN OTHERS THEN
        x_return_code := SQLCODE;
        x_return_message := 'Unexpected error: ' || SQLERRM;   
  END GET_CLIENT;
END SPNAME_PKG;