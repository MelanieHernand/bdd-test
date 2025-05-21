Feature: Mostrar listado de cashin en Perú

Scenario Outline: Obtener la lista de transacciones de cashin de Perú
    Given Ingreso al login "auth/login" para obtener el token con las credenciales de "<EMAIL>" , "<PASSWORD>" , "<COUNTRY>"
    When Consulto la lista de cashin del usuario en Perú
    Then La respuesta debe contener una lista de transacciones con IDs y mostrar cuántas tiene

@test
Examples:
  | EMAIL                       | PASSWORD  | COUNTRY | 
  | fullaccionespe7@yopmail.com | 123qweasD | PE      |
