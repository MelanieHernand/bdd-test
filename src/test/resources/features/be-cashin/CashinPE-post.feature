Feature: Realizar orden de cashin en Perú

Scenario Outline: Colocar una orden de cashin en Perú y validar en la BD
  Given Obtengo el token del endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Peru enviando la imagen del comprobante "<FILE>"
  Then La respuesta del cashin de Perú debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"
  And La orden debe estar registrada en la base de datos de Perú "<DATABASE>" 

@test
Examples:
  | EMAIL                       | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE | DATABASE     | FILE                              |
  | fullaccionespe7@yopmail.com | 123qweasD | PE      | 200         | ok       | be-cashin-pe | src/test/resources/data/image.jpg |

Scenario: Intentar realizar cashin en Perú con un archivo txt
  Given Obtengo el token del endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Peru enviando la imagen del comprobante "<FILE>"
  Then La respuesta del cashin de Perú debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"
  And La orden debe estar registrada en la base de datos de Perú "<DATABASE>"

@test
Examples:
  | EMAIL                       | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE | DATABASE     | FILE                                 |
  | fullaccionespe7@yopmail.com | 123qweasD | PE      | 200         | ok       | be-cashin-pe | src/test/resources/data/invalido.txt |

Scenario Outline: Intentar realizar cashin en Perú sin enviar ninguna imagen
  Given Obtengo el token del endpoint "auth/login" con los parametros "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
  When Realizo una orden de cashin en Peru enviando la imagen del comprobante "<FILE>"
  Then La respuesta del cashin de Perú debe tener un status "<STATUS_CODE>" y un mensaje de respuesta "<RESPONSE>"

@test
Examples:
  | EMAIL                       | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE     | DATABASE     | FILE  |
  | fullaccionespe7@yopmail.com | 123qweasD | PE      | 500         | invalid_file | be-cashin-pe |       | 
