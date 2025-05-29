Feature: Creación de nuevo usuario Multicountry

Scenario Outline: Registro completo exitoso en Colombia
  Given Registro un nuevo usuario con el email, "<PASSWORD>" y "<COUNTRY>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje "<RESPONSE>"
  And La respuesta debe incluir el campo "token" y "user_id"
  Then El usuario debe existir en la base de datos "<DATABASE1>"

  When valido el email y número de teléfono
  Then se deben completar los campos en la base de datos "<DATABASE1>"

  When envío los datos básicos del usuario con n_id "<N_ID>" y demás datos generados
  Then si es exitoso, la respuesta debe tener "updated"
  And los datos del usuario deben coincidir en la base de datos "<DATABASE2>" 

  When configuro el perfil de riesgo con valor "<RISK_SCORE>"
  Then se debe reflejar en la respuesta el risk_profile_score igual a "<RISK_SCORE>"
  And el risk_profile_score debe coincidir en la base de datos "<DATABASE2>"

  When acepto los terminos y condiciones del perfil de riesgo
  Then se debe visualizar en la respuesta "update user"
  And se debe visualizar la fecha de aceptación en la base de datos "<DATABASE2>"

  When envio la foto frontal y trasera de mi documento de identidad
  Then 

@test1
Examples:
  | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE | DATABASE2         | N_ID | RISK_SCORE | DATABASE1  |
  | 123qweasD | CO      | 200         | created  | be-user-data-co   |      | 21         | be-auth    |

Scenario Outline: Envío de datos con n_id duplicado en Colombia
  Given Registro un nuevo usuario con el email, "<PASSWORD>" y "<COUNTRY>"
  Then La respuesta debe tener un status "200" y un mensaje "created"
  And La respuesta debe incluir el campo "token" y "user_id"
  Then El usuario debe existir en la base de datos "<DATABASE>"

  When envío los datos básicos del usuario con n_id "<N_ID>" y demás datos generados
  Then si hay duplicado, la respuesta debe tener status 502 y mensaje "Bad Gateway"

@test
Examples:
  | PASSWORD  | COUNTRY | DATABASE         | N_ID       |
  | 123qweasD | CO      | be-user-data-co  | 7952370112 |

