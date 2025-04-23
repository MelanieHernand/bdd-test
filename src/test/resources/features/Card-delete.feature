Feature: Eliminar tarjetas creadas en Colombia y Chile

  Scenario Outline: Eliminar tarjeta válida existente - Colombia     
   Given Un usuario ingresa al login con el endpoint "<ENDPOINT>" y "<EMAIL>" "<CONTRASENA>" y "<COUNTRY>"
   And Capturo la última tarjeta antes de eliminar una 
   When Elimino la tarjeta con el endpoint "<DELETE_ENDPOINT>" y el id capturado
   Then La respuesta debe tener un estado de eliminación "<RESPONSE>" y status code "<STATUS_CODE>"
   And La tarjeta eliminada no debe estar en el nuevo listado de tarjetas
   And En la base de datos el status de la tarjeta debe ser "<DB_STATUS>"

   @test
    Examples:
      | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | DELETE_ENDPOINT | RESPONSE | STATUS_CODE | DB_STATUS |
      | userfull@yopmail.com | 123qweasD  | CO      | auth/login | /card           | deleted  | 200         | DELETED   |

  Scenario Outline: Eliminar una tarjeta inexistente - Colombia
    Given Un usuario ingresa al login con el endpoint "<ENDPOINT>" y "<EMAIL>" "<CONTRASENA>" y "<COUNTRY>"
    When Intenta eliminar una tarjeta con el id "<FAKE_CARD_ID>" usando el endpoint "<DELETE_ENDPOINT>"
    Then La respuesta debe contener el mensaje de error "<ERROR_MESSAGE>" y status code "<STATUS_CODE>"

   @test
    Examples:
      | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | DELETE_ENDPOINT | FAKE_CARD_ID | ERROR_MESSAGE     | STATUS_CODE |
      | userfull@yopmail.com | 123qweasD  | CO      | auth/login | /card           | 8000000      | Card not found    | 400         |

  Scenario Outline: Eliminar tarjeta válida existente - Chile     
   Given Un usuario ingresa al login con el endpoint "<ENDPOINT>" y "<EMAIL>" "<CONTRASENA>" y "<COUNTRY>"
   And Capturo la última tarjeta antes de eliminar una 
   When Elimino la tarjeta con el endpoint "<DELETE_ENDPOINT>" y el id capturado
   Then La respuesta debe tener un estado de eliminación "<RESPONSE>" y status code "<STATUS_CODE>"
   And La tarjeta eliminada no debe estar en el nuevo listado de tarjetas
   And En la base de datos el status de la tarjeta debe ser "<DB_STATUS>"

   @test
    Examples:
      | EMAIL                     | CONTRASENA | COUNTRY | ENDPOINT   | DELETE_ENDPOINT | RESPONSE | STATUS_CODE | DB_STATUS |
      | probandochile@yopmail.com | 123qweasD  | CL      | auth/login | /card           | deleted  | 200         | DELETED   |

  Scenario Outline: Eliminar una tarjeta inexistente - Chile
    Given Un usuario ingresa al login con el endpoint "<ENDPOINT>" y "<EMAIL>" "<CONTRASENA>" y "<COUNTRY>"
    When Intenta eliminar una tarjeta con el id "<FAKE_CARD_ID>" usando el endpoint "<DELETE_ENDPOINT>"
    Then La respuesta debe contener el mensaje de error "<ERROR_MESSAGE>" y status code "<STATUS_CODE>"

   @test
    Examples:
      | EMAIL                     | CONTRASENA | COUNTRY | ENDPOINT   | DELETE_ENDPOINT | FAKE_CARD_ID | ERROR_MESSAGE     | STATUS_CODE |
      | probandochile@yopmail.com | 123qweasD  | CL      | auth/login | /card           | 8000000      | Card not found    | 400         |




