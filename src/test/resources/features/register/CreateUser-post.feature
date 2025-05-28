Feature: Creación de nuevo usuario Multicountry

Scenario Outline: Creación de nuevo usuario válido en Colombia
  Given Registro un nuevo usuario con el email, "<PASSWORD>" y "<COUNTRY>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje "<RESPONSE>"
  And La respuesta debe incluir el campo "token" y "user_id"
  Then El usuario debe existir en la base de datos "<DATABASE>" 

@test
Examples:
  | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE | DATABASE |
  | 123qweasD | CO      | 200         | created  | be-auth  |

Scenario Outline: Creación de nuevo usuario válido en Perú
  Given Registro un nuevo usuario con el email, "<PASSWORD>" y "<COUNTRY>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje "<RESPONSE>"
  And La respuesta debe incluir el campo "token" y "user_id"
  Then El usuario debe existir en la base de datos "<DATABASE>" 

@test
Examples:
  | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE | DATABASE |
  | 123qweasD | PE      | 200         | created  | be-auth  |

Scenario Outline: Creación de nuevo usuario válido en Chile
  Given Registro un nuevo usuario con el email, "<PASSWORD>" y "<COUNTRY>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje "<RESPONSE>"
  And La respuesta debe incluir el campo "token" y "user_id"
  Then El usuario debe existir en la base de datos "<DATABASE>" 

@test
Examples:
  | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE | DATABASE |
  | 123qweasD | CL      | 200         | created  | be-auth  |

Scenario Outline: Crear un usuario existente
  Given Intento registrar un usuario con email fijo "<EMAIL>", "<PASSWORD>" y "<COUNTRY>"
  Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje "<RESPONSE>"
  
@test
Examples:
  | EMAIL                | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE    |
  | userfull@yopmail.com | 123qweasD | CO      | 400         | user_exist  |