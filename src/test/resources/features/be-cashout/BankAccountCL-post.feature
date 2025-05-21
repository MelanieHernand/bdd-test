Feature: Crear cuenta de banco en Chile - Cashout

Scenario Outline: Crear cuenta de banco para retiros válida 
    Given Obtengo el token del endpoint "auth/login" con el usuario "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
    When Agrego una cuenta de banco con el numero "<ACCOUNT_NUMBER>" y id del banco "<BANK_ID>"
    Then El banco debe estar creado y la respuesta debe tener un status "<STATUS_CODE>"
    And La cuenta creada debe estar en el listado de cuentas disponibles 

@test1
Examples:
    | EMAIL                     | PASSWORD  | COUNTRY | ACCOUNT_NUMBER | BANK_ID | STATUS_CODE |
    | probandochile@yopmail.com | 123qweasD | CL      | 101010101010   | 61      | 200         |

Scenario Outline: Crear cuenta de banco para retiros sin bankId 
    Given Obtengo el token del endpoint "auth/login" con el usuario "<EMAIL>" "<PASSWORD>" y "<COUNTRY>"
    When Agrego una cuenta de banco con el numero "<ACCOUNT_NUMBER>" y id del banco "<BANK_ID>"
    Then El banco debe estar creado y la respuesta debe tener un status "<STATUS_CODE>"

@test1
Examples:
    | EMAIL                     | PASSWORD  | COUNTRY | ACCOUNT_NUMBER | BANK_ID | STATUS_CODE |
    | probandochile@yopmail.com | 123qweasD | CL      | 101010101010   |         | 400         |