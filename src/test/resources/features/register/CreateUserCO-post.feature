Feature: Creación de nuevo usuario en Colombia

Scenario Outline: Creación de nuevo usuario válido en Colombia
    Given Registro un nuevo usuario con el email, "<PASSWORD>" y "<COUNTRY>"
    Then La respuesta debe tener un status "<STATUS_CODE>" y un mensaje "<RESPONSE>"
    And La respuesta debe incluir el campo "token" y "user_id"

@test
Examples:
  | PASSWORD  | COUNTRY | STATUS_CODE | RESPONSE |
  | 123qweasD | CO      | 200         | created  |

