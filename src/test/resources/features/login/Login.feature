Feature: Login de un usuario multicountry

  Scenario Outline: Ingresar a colombia con un usuario con datos validos
    Given Ingreso al login page
    When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    Then La respuesta debe tener un estado del usuario "<STATUS>"
    And el status code debe ser <STATUS_CODE>
    
    @test
  Examples:
    | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | STATUS_CODE | STATUS |
    | userfull@yopmail.com | 123qweasD  | CO      | auth/login | 200         | logged |

  Scenario Outline: Ingresar a colombia con un usuario con mail invalido
     Given Ingreso al login page
     When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
     And el status code debe ser <STATUS_CODE>
     Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL              | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | ghsgam@yopmail.com | 123qweasD  | CO      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar a colombia con un usuario con pass invalida
     Given Ingreso al login page
     When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
     And el status code debe ser <STATUS_CODE>
     Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | userfull@yopmail.com | 111111111  | CO      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar a colombia sin ingresar datos
     Given Ingreso al login page
     When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
     And el status code debe ser <STATUS_CODE>
     Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | null  | null       | CO      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar a peru con un usuario con datos validos
     Given Ingreso al login page
     When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
     Then La respuesta debe tener un estado del usuario "<STATUS>"
     And el status code debe ser <STATUS_CODE>
     And el usuario con email "<EMAIL>" existe en la base de datos "<DATABASENAME>"

    @test
  Examples:
    | EMAIL               | CONTRASENA | COUNTRY | ENDPOINT   | STATUS_CODE | STATUS | DATABASENAME      |
    | fondop3@yopmail.com | 123qweasD  | PE      | auth/login | 200         | logged | `be-user-data-pe` |

  Scenario Outline: Ingresar a peru con un usuario con mail invalido
     Given Ingreso al login page
     When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
     And el status code debe ser <STATUS_CODE>
     Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL              | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | falsem@yopmail.com | 123qweasD  | PE      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar a peru con un usuario con pass invalida
    Given Ingreso al login page
    When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And el status code debe ser <STATUS_CODE>
    Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL                   | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | cuentaperu4@yopmail.com | 111111111  | PE      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar a peru sin ingresar datos
    Given Ingreso al login page
    When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And el status code debe ser <STATUS_CODE>
    Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | null  | null       | PE      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar a chile con un usuario con datos validos
    Given Ingreso al login page
    When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    Then La respuesta debe tener un estado del usuario "<STATUS>"
    And el status code debe ser <STATUS_CODE>
    And el usuario con email "<EMAIL>" existe en la base de datos "<DATABASENAME>"

    @test
  Examples:
    | EMAIL                     | CONTRASENA | COUNTRY | ENDPOINT   | STATUS_CODE | STATUS | DATABASENAME      |
    | probandochile@yopmail.com | 123qweasD  | CL      | auth/login | 200         | logged | `be-user-data-cl` |

  Scenario Outline: Ingresar a chile con un usuario con mail invalido
    Given Ingreso al login page
    When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And el status code debe ser <STATUS_CODE>
    Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL              | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | falsem@yopmail.com | 123qweasD  | CL      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar a chile con un usuario con pass invalida
    Given Ingreso al login page
    When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And el status code debe ser <STATUS_CODE>
    Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL                | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | prprue34@yopmail.com | 111111111  | CL      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar a chile sin ingresar datos
    Given Ingreso al login page
    When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And el status code debe ser <STATUS_CODE>
    Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE             | STATUS_CODE |
    | null  | null       | CL      | auth/login | invalid_credentials | 400         |

  Scenario Outline: Ingresar sin seleccionar pais
    Given Ingreso al login page
    When Invoco al endpoint: "<ENDPOINT>" con los siguientes parametros "<EMAIL>" "<CONTRASENA>" "<COUNTRY>"
    And el status code debe ser <STATUS_CODE>
    Then La respuesta tiene un mensaje "<MESSAGE>"

    @test
  Examples:
    | EMAIL | CONTRASENA | COUNTRY | ENDPOINT   | MESSAGE               | STATUS_CODE |
    | null  | null       | null      | auth/login | invalid_credentials | 400         |