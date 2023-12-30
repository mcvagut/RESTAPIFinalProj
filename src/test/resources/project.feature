Feature:
  Scenario: Yo como administrador de PawsPal quiero poder crear, actualizar y eliminar un usuario.

    Given using token in localhost
    When send POST request "/api/users/" with body
    """
    {
        "nombre": "Nombre diplomado",
        "apellido": "Apellido diplomado",
        "email": "diplomado@gmail.com",
        "usuario" : "diplomado",
        "password": "diplomado",
        "pais": "Bolivia",
        "ciudad": "La Paz",
        "telefono": "12345678",
        "fechaNacimiento": "1990-01-01",
        "isAdmin": true
    }
    """
    Then response code should be 201
    And the attribute "email" should be equal to "diplomado@gmail.com"
    And save "usuario" as "$USUARIO" from response

    When send POST request "/api/users/logout" with body
    """

    """
    Then response code should be 200

    Given using new token in localhost

    When send PUT request "/api/users/$USUARIO" with body
    """
    {
        "nombre": "Modificando Nombre"
    }
    """
    Then response code should be 200
    And the attribute "nombre" should be equal to "Modificando Nombre"

    When send DELETE request "/api/users/$USUARIO" with body
    """
    """
    Then response code should be 204
