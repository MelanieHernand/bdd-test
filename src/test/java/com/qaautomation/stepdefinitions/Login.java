// src/test/java/com/qaautomation/stepdefinitions/LoginSteps.java
package com.qaautomation.stepdefinitions;

import com.qaautomation.ConnectionDB.MySQLUtils;
import com.qaautomation.context.TestContext;
import com.qaautomation.services.LoginService;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    private final LoginService loginService = new LoginService();  
    private final TestContext context;

    public Login(TestContext context) {
        this.context = context;
    }

    @When("Invoco al endpoint: {string} con los siguientes parametros {string} {string} {string}")
    public void callEndpointLogin(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
        context.setCountry(country); // Guardamos país
    }

    @Then("La respuesta debe tener un estado del usuario {string}")
    public void validateUserStatus(String expectedStatus) {
        Response response = loginService.getResponse();
        assertNotNull(response);
        assertEquals(expectedStatus, response.jsonPath().getString("status"));
    }

    @And("el status code debe ser {int}")
    public void validateStatusCode(int expectedStatusCode) {
        Response response = loginService.getResponse();
        assertNotNull(response);
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    public String getToken() {
        return loginService.getToken();
    }

    public Response getResponse() {
        return loginService.getResponse();
    }

    @Then("La respuesta tiene un mensaje {string}")
    public void validateResponseMessage(String expectedMessage) {
        Response response = loginService.getResponse();
        assertNotNull("La respuesta no debe ser nula", response);

        String actualMessage = response.jsonPath().getString("message");
        Assert.assertEquals("El mensaje de la respuesta no coincide", expectedMessage, actualMessage);

        System.out.println("Mensaje de la respuesta: " + actualMessage);
    }

    @Given("Ingreso al login page")
    public void ingresoAlLoginPage() {
        System.out.println("Accediendo a la página de login...");
    }


    @And("el usuario con email {string} existe en la base de datos {string}")
    public void elUsuarioConEmailExisteEnLaBaseDeDatos(String email, String databaseName) {
        loginService.validateUserInDatabase(email, databaseName);
    }

    @And("el id del usuario debe coincidir con el id registrado en la base de datos para {string}")
public void validarIdEnBaseDeDatos(String country) {
    String token = context.getLoginService().getToken();

    if (token == null || token.isEmpty()) {
        fail("Token no disponible. Asegurate de hacer login correctamente antes de validar el ID.");
    }

    // 1. Obtener el ID del usuario desde /user/info
    Response infoResp = RestAssured
            .given()
            .relaxedHTTPSValidation()
            .header("sk2", token)
            .get("https://dev.trii.tech/user/info")
            .prettyPeek();

    int idApi = infoResp.jsonPath().getInt("id");
    System.out.println("ID obtenido desde API: " + idApi);

    // 2. Determinar base de datos según el país
    String database = switch (country.toUpperCase()) {
        case "CO" -> "be-user-data-co";
        case "CL" -> "be-user-data-cl";
        case "PE" -> "be-user-data-pe";
        default -> throw new IllegalArgumentException("País no soportado: " + country);
    };

    String query = "SELECT id FROM user WHERE id = " + idApi;
    var result = MySQLUtils.executeQuery(query, database);

    try {
        assertNotNull("No se obtuvo resultado de la base de datos", result);
        ResultSet rs = result.getResultSet();
        assertNotNull("El ResultSet está vacío", rs);
        assertTrue("El ID del usuario no fue encontrado en la base: " + database, rs.next());

        System.out.println("ID verificado correctamente en la base de datos: " + database);
    } catch (SQLException e) {
        fail("Error al consultar la base: " + e.getMessage());
    } finally {
        MySQLUtils.closeResources(result);
    }
}
}