Feature: Realizar orden de cashin en Colombia 

Scenario Outline: Colocar una orden de cashin con un banco aleatorio y validar en la BD 
  Given Me logueo con exito con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Obtengo un banco aleatorio de la lista de bancos del país "<COUNTRY>"
  And Realizo una orden de cashin con el banco seleccionado y monto "<AMOUNT>"
  Then La respuesta debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"
  And La orden debe estar registrada en la base de datos "<DATABASE>" con el monto "<AMOUNT>" y canal "<CHANNEL>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | AMOUNT  | STATUS_CODE | STATUS  | DATABASE      | CHANNEL          |
  | userfull@yopmail.com | 123qweasD | CO      | 239000  | 200         | CREATED | be-cashin-co  | accival connect  |

Scenario Outline: Realizar cashin con un banco inválidos
  Given Me logueo con exito con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin con el banco "<BANK_CODE>" y monto "<AMOUNT>"
  Then La respuesta debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | BANKCODE | AMOUNT | STATUS_CODE | STATUS                |
  | userfull@yopmail.com | 123qweasD | CO      | 0        | 1000   | 500         | Internal Server Error | 

Scenario Outline: Realizar cashin con un monto 0 
  Given Me logueo con exito con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Obtengo un banco aleatorio de la lista de bancos del país "<COUNTRY>"
  And Realizo una orden de cashin con el banco seleccionado y monto "<AMOUNT>"
  Then La respuesta debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | AMOUNT | STATUS_CODE | STATUS                | 
  | userfull@yopmail.com | 123qweasD | CO      | 0      | 500         | Internal Server Error |

Scenario Outline: Realizar cashin con un monto nulo
  Given Me logueo con exito con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Obtengo un banco aleatorio de la lista de bancos del país "<COUNTRY>"
  And Realizo una orden de cashin con el banco seleccionado y monto "<AMOUNT>"
  Then La respuesta debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | AMOUNT | STATUS_CODE | STATUS                |
  | userfull@yopmail.com | 123qweasD | CO      |        | 500         | Internal Server Error |

Scenario Outline: Realizar cashin con un sin ingresar monto ni banco
  Given Me logueo con exito con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin con el banco "<BANK_CODE>" y monto "<AMOUNT>"
  Then La respuesta debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | BANKCODE | AMOUNT | STATUS_CODE | STATUS                 |
  | userfull@yopmail.com | 123qweasD | CO      |          |        | 500         | Internal Server Error  | 

Scenario Outline: Realizar cashin con un monto que supere el máximo
  Given Me logueo con exito con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Obtengo un banco aleatorio de la lista de bancos del país "<COUNTRY>"
  And Realizo una orden de cashin con el banco seleccionado y monto "<AMOUNT>"
  Then La respuesta debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | AMOUNT       | STATUS_CODE | STATUS                | 
  | userfull@yopmail.com | 123qweasD | CO      | 30000000000  | 500         | Internal Server Error | 

