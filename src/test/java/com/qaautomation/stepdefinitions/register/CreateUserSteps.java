package com.qaautomation.stepdefinitions.register;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.qaautomation.ConnectionDB.*;

import com.qaautomation.services.RegisterService;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

public class CreateUserSteps {

    private final RegisterService registerService = new RegisterService();
    private Response response;

    @Given("Registro un nuevo usuario con el email, {string} y {string}")
    public void crearNuevoUsuarioEnColombia(String password, String country) {
        registerService.registerUser(password, country);
        response = registerService.getRegisterResponse();
    }

    @Given("Intento registrar un usuario con email fijo {string}, {string} y {string}")
    public void registrarUsuarioConEmailFijo(String email, String password, String country) {
        registerService.registerUser(password, country, email);
        response = registerService.getRegisterResponse();
    }

    @Then("La respuesta debe tener un status {string} y un mensaje {string}")
    public void validarStatusYMensajeDeRespuesta(String expectedStatusCode, String expectedResponse) {
        assertNotNull("La respuesta del registro es nula", response);
        assertEquals("El status code no coincide", Integer.parseInt(expectedStatusCode), response.getStatusCode());

        String actualMessage;
        if (response.jsonPath().get("response") != null) {
        actualMessage = response.jsonPath().getString("response");
        } else if (response.jsonPath().get("message") != null) {
        actualMessage = response.jsonPath().getString("message");
        } else {
        fail("No se encontró ni 'response' ni 'message' en la respuesta.");
        return;
        }

    assertEquals("El mensaje no coincide", expectedResponse.toLowerCase(), actualMessage.toLowerCase());

    System.out.println("✅ Validación correcta de status code y mensaje: " + actualMessage);
}

    @Then("La respuesta debe incluir el campo {string} y {string}")
    public void validarTokenYUserId(String tokenField, String userIdField) {
        assertNotNull("La respuesta es nula", response);

        String token = response.jsonPath().getString(tokenField);
        int userId = response.jsonPath().getInt(userIdField);

        assertNotNull("El token no está presente en la respuesta", token);
        assertFalse("El token está vacío", token.isEmpty());
        assertTrue("El user_id debe ser mayor a 0", userId > 0);

        System.out.println("Token y user_id presentes y válidos.");
    }

    @Then("El usuario debe existir en la base de datos {string}")
    public void validateUserInDatabase(String database) {
        int userId = registerService.getUserId();
        String query = "SELECT * FROM user WHERE id = " + userId;
    
        var result = com.qaautomation.ConnectionDB.MySQLUtils.executeQuery(query, "`" + database + "`");

    try {
        ResultSet rs = result.getResultSet();
        assertTrue("No se encontró el usuario en la base de datos", rs.next());

        String emailDb = rs.getString("email");
        assertEquals("El email no coincide con el generado", registerService.getGeneratedEmail(), emailDb);

        System.out.println("✅ Usuario verificado en DB correctamente: ID " + userId + " | Email " + emailDb);
    } catch (SQLException e) {
        fail("Error al validar el usuario en la base de datos: " + e.getMessage());
        } finally {
        MySQLUtils.closeResources(result);
        }
    }

}
