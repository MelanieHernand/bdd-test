Feature: Realizar pagos de suscripción trii pro en Colombia y Chile

Scenario Outline: Realizar pago exitoso de suscripción trii pro anual en Colombia
  Given Me logueo con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Preparo una tarjeta activa para el pago
  And Realizo un pago con el plan "<PLAN>" y el monto "<AMOUNT>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"
  And El pago debe estar registrado en la base de datos con el monto "<AMOUNT>" y metodo "<METHOD>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | PLAN   | AMOUNT  | STATUS_CODE | RESPONSE | METHOD |
  | userfull@yopmail.com | 123qweasD | CO      | yearly | 239000  | 200         | changed  | CARD   |

Scenario Outline: Realizo pago de suscripción trii pro mensual en Colombia 
  Given Me logueo con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Preparo una tarjeta activa para el pago
  And Realizo un pago con el plan "<PLAN>" y el monto "<AMOUNT>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"
  And El pago debe estar registrado en la base de datos con el monto "<AMOUNT>" y metodo "<METHOD>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | PLAN    | AMOUNT | STATUS_CODE | RESPONSE | METHOD |
  | userfull@yopmail.com | 123qweasD | CO      | monthly | 23900  | 200         | changed  | CARD   |

Scenario Outline: Realizo pago de plan inválido en Colombia 
  Given Me logueo con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Preparo una tarjeta activa para el pago
  And Realizo un pago con el plan "<PLAN>" y el monto "<AMOUNT>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | PLAN    | AMOUNT | STATUS_CODE | RESPONSE  | 
  | userfull@yopmail.com | 123qweasD | CO      | Erroneo | 23900  | 400         | Not Found |

Scenario Outline: Realizo pago de plan sin monto en Colombia 
  Given Me logueo con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Preparo una tarjeta activa para el pago
  And Realizo un pago con el plan "<PLAN>" y el monto "<AMOUNT>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"

@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | PLAN    | AMOUNT | STATUS_CODE | RESPONSE    | 
  | userfull@yopmail.com | 123qweasD | CO      | monthly |        | 400         | Bad Request |

Scenario Outline: Realizar pago exitoso de suscripción trii pro anual en Chile
  Given Me logueo con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Preparo una tarjeta activa para el pago
  And Realizo un pago con el plan "<PLAN>" y el monto "<AMOUNT>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"
  And El pago debe estar registrado en la base de datos con el monto "<AMOUNT>" y metodo "<METHOD>"
  And Actualizo el status del último pago a "<PAYMENT_STATUS>" en la base de datos

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | PLAN      | AMOUNT | STATUS_CODE | RESPONSE | METHOD  | PAYMENT_STATUS |
  | probandochile@yopmail.com | 123qweasD | CL      | cl_yearly | 59990  | 200         | changed  | BALANCE | DECLINED       |

Scenario Outline: Realizar pago exitoso de suscripción trii pro mensual en Chile
  Given Me logueo con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Preparo una tarjeta activa para el pago
  And Realizo un pago con el plan "<PLAN>" y el monto "<AMOUNT>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"
  And El pago debe estar registrado en la base de datos con el monto "<AMOUNT>" y metodo "<METHOD>"
  And Actualizo el status del último pago a "<PAYMENT_STATUS>" en la base de datos

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | PLAN       | AMOUNT | STATUS_CODE | RESPONSE | METHOD  | PAYMENT_STATUS |
  | probandochile@yopmail.com | 123qweasD | CL      | cl_monthly | 5999   | 200         | changed  | BALANCE | DECLINED       |

Scenario Outline: Realizo pago de plan inválido en Chile 
  Given Me logueo con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Preparo una tarjeta activa para el pago
  And Realizo un pago con el plan "<PLAN>" y el monto "<AMOUNT>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | PLAN    | AMOUNT | STATUS_CODE | RESPONSE  | 
  | probandochile@yopmail.com | 123qweasD | CL      | Erroneo | 50000  | 400         | Not Found |

Scenario Outline: Realizo pago de plan sin monto en Chile 
  Given Me logueo con el endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Preparo una tarjeta activa para el pago
  And Realizo un pago con el plan "<PLAN>" y el monto "<AMOUNT>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"

@test
Examples:
  | EMAIL                     | PASSWORD  | COUNTRY | PLAN       | AMOUNT | STATUS_CODE | RESPONSE    | 
  | probandochile@yopmail.com | 123qweasD | CL      | cl_monthly |        | 400         | Bad Request |

  