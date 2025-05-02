Feature: Gestión de tarjetas - Colombia y Chile

  Scenario Outline: Crear una tarjeta válida en Colombia
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And Capturo la última tarjeta antes de registrar una nueva
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    Then La respuesta debe tener un estado de creación "<RESPONSE>"  y status code "<STATUS_CODE>"
    And Debe haberse registrado una nueva tarjeta

    @test
    Examples:
      | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER           | CVC | EXP_YEAR | EXP_MONTH | RESPONSE                | STATUS_CODE |
      | userfull@yopmail.com | 123qweasD  | CO      | auth/login | /card         | 4242424242424242 | 123 | 27       | 12        | Card added successfully | 200         |

  Scenario Outline: Crear una tarjeta válida con 4 dígitos en el cvc - CO
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And Capturo la última tarjeta antes de registrar una nueva
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    Then La respuesta debe tener un estado de creación "<RESPONSE>"  y status code "<STATUS_CODE>"
    And Debe haberse registrado una nueva tarjeta

    @test
    Examples:
      | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER           | CVC  | EXP_YEAR | EXP_MONTH | RESPONSE                | STATUS_CODE |
      | userfull@yopmail.com | 123qweasD  | CO      | auth/login | /card         | 4242424242424242 | 1234 | 27       | 12        | Card added successfully | 200         |

  Scenario Outline: Crear una tarjeta con un número inválido - CO
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    And El status code debe ser <STATUS_CODE>
    Then La respuesta debe tener <STATUS_RESPONSE> y detalle "<DETAIL>"

    @test
    Examples:
      | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER | CVC | EXP_YEAR | EXP_MONTH | STATUS_CODE | STATUS_RESPONSE | DETAIL      |
      | userfull@yopmail.com | 123qweasD  | CO      | auth/login | /card         | 411111 | 333 | 27       | 03        | 500         | 502             | Bad Gateway |

  Scenario Outline: Crear una tarjeta con un cvc inválido - CO
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    And El status code debe ser <STATUS_CODE>
    Then La respuesta debe tener <STATUS_RESPONSE> y detalle "<DETAIL>"

    @test
    Examples:
      | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER           | CVC | EXP_YEAR | EXP_MONTH | STATUS_CODE | STATUS_RESPONSE | DETAIL      |
      | userfull@yopmail.com | 123qweasD  | CO      | auth/login | /card         | 4242424242424242 | 1   | 27       | 03        | 500         | 502             | Bad Gateway |

  Scenario Outline: Crear una tarjeta con datos incompletos - CO
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    And El status code debe ser <STATUS_CODE>
    Then La respuesta debe tener <STATUS_RESPONSE> y detalle "<DETAIL>"

    @test
    Examples:
      | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER  | CVC | EXP_YEAR | EXP_MONTH | STATUS_CODE | STATUS_RESPONSE | DETAIL      |
      | userfull@yopmail.com | 123qweasD  | CO      | auth/login | /card         | 1       | 1   | 2        | 0         | 500         | 502             | Bad Gateway |

  Scenario Outline: Crear tarjeta válida en Chile
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And Capturo la última tarjeta antes de registrar una nueva
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    Then La respuesta debe tener un estado de creación "<RESPONSE>"  y status code "<STATUS_CODE>"
    And Debe haberse registrado una nueva tarjeta

    @test
    Examples:
      | EMAIL                     | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER           | CVC | EXP_YEAR | EXP_MONTH | RESPONSE                | STATUS_CODE |
      | probandochile@yopmail.com | 123qweasD  | CL      | auth/login | /card         | 4242424242424242 | 123 | 27       | 12        | Card added successfully | 200         |

  Scenario Outline: Crear una tarjeta con un número inválido - CL
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    And El status code debe ser <STATUS_CODE>
    Then La respuesta debe tener <STATUS_RESPONSE> y detalle "<DETAIL>"

    @test
    Examples:
      | EMAIL                     | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER | CVC | EXP_YEAR | EXP_MONTH | STATUS_CODE | STATUS_RESPONSE | DETAIL      |
      | probandochile@yopmail.com | 123qweasD  | CL      | auth/login | /card         | 411111 | 333 | 27       | 03        | 500         | 502             | Bad Gateway |  

  Scenario Outline: Crear una tarjeta con un cvc inválido - CL
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    And El status code debe ser <STATUS_CODE>
    Then La respuesta debe tener <STATUS_RESPONSE> y detalle "<DETAIL>"

    @test
    Examples:
      | EMAIL                     | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER           | CVC | EXP_YEAR | EXP_MONTH | STATUS_CODE | STATUS_RESPONSE | DETAIL      |
      | probandochile@yopmail.com | 123qweasD  | CL      | auth/login | /card         | 4242424242424242 | 1   | 27       | 03        | 500         | 502             | Bad Gateway |

  Scenario Outline: Crear una tarjeta con datos incompletos - CL
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    And El status code debe ser <STATUS_CODE>
    Then La respuesta debe tener <STATUS_RESPONSE> y detalle "<DETAIL>"

    @test
    Examples:
      | EMAIL                     | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER  | CVC | EXP_YEAR | EXP_MONTH | STATUS_CODE | STATUS_RESPONSE | DETAIL      |
      | probandochile@yopmail.com | 123qweasD  | CL      | auth/login | /card         | 1       | 1   | 2        | 0         | 500         | 502             | Bad Gateway |

  Scenario Outline: Crear una tarjeta con datos incompletos - CL
    Given Ingreso al login con el endpoint "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    When Creo una tarjeta en "<CARD_ENDPOINT>" con los datos "<NUMBER>" "<CVC>" "<EXP_YEAR>" "<EXP_MONTH>"
    And El status code debe ser <STATUS_CODE>
    Then La respuesta debe tener <STATUS_RESPONSE> y detalle "<DETAIL>"

    @test
    Examples:
      | EMAIL                     | CONTRASENA | COUNTRY | ENDPOINT   | CARD_ENDPOINT | NUMBER  | CVC | EXP_YEAR | EXP_MONTH | STATUS_CODE | STATUS_RESPONSE | DETAIL      |
      | probandochile@yopmail.com | 123qweasD  | CL      | auth/login | /card         | 1       | 1   | 2        | 0         | 500         | 502             | Bad Gateway |    



