Feature: Realizar orden de cashin en Chile

Scenario Outline: Colocar una orden de cashin en Chile con Fintoc y validar en la BD 
  Given Realizo login en el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Chile con el banco "<INSTITUTION_ID>" y monto "<AMOUNT>"
  Then La respuesta del cashin debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | INSTITUTION_ID    | AMOUNT  | STATUS_CODE | STATUS  | 
  | probandochile@yopmail.com | 123qweasD | CL      | cl_banco_de_chile | 239000  | 200         | CREATED |

Scenario Outline: Realizar cashin con un banco inválido
  Given Realizo login en el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Chile con el banco "<INSTITUTION_ID>" y monto "<AMOUNT>"
  Then La respuesta del cashin debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | INSTITUTION_ID    | AMOUNT | STATUS_CODE | STATUS |
  | probandochile@yopmail.com | 123qweasD | CL      | error             | 239000 | 200         | null   |

Scenario Outline: Realizar cashin con un monto 0
  Given Realizo login en el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Chile con el banco "<INSTITUTION_ID>" y monto "<AMOUNT>"
  Then La respuesta del cashin debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | INSTITUTION_ID    | AMOUNT | STATUS_CODE | STATUS |
  | probandochile@yopmail.com | 123qweasD | CL      | cl_banco_de_chile | 0      | 200         | null   |

Scenario Outline: Realizar cashin con un monto nulo
  Given Realizo login en el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Chile con el banco "<INSTITUTION_ID>" y monto "<AMOUNT>"
  Then La respuesta del cashin debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | INSTITUTION_ID    | AMOUNT | STATUS_CODE | STATUS |
  | probandochile@yopmail.com | 123qweasD | CL      | cl_banco_de_chile |        | 200         | null   |

Scenario Outline: Realizar cashin sin ingresar monto ni banco
  Given Realizo login en el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Chile con el banco "<INSTITUTION_ID>" y monto "<AMOUNT>"
  Then La respuesta del cashin debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | INSTITUTION_ID | AMOUNT | STATUS_CODE | STATUS |
  | probandochile@yopmail.com | 123qweasD | CL      |                |        | 200         | null   |

Scenario Outline: Realizar cashin sin ingresando un monto mayor al máximo
  Given Realizo login en el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Chile con el banco "<INSTITUTION_ID>" y monto "<AMOUNT>"
  Then La respuesta del cashin debe contener un status "<STATUS_CODE>" y un mensaje de respuesta "<STATUS>"

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | INSTITUTION_ID    | AMOUNT         | STATUS_CODE | STATUS |
  | probandochile@yopmail.com | 123qweasD | CL      | cl_banco_de_chile | 60000000000000 | 200         | null   |