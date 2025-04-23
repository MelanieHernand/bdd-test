// src/test/java/com/qaautomation/stepdefinitions/LoginSteps.java
package com.qaautomation.stepdefinitions;

import com.qaautomation.services.LoginService;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.junit.Assert;

import static org.junit.Assert.*;

public class Login {

    private final LoginService loginService = new LoginService();

    @When("Invoco al endpoint: {string} con los siguientes parametros {string} {string} {string}")
    public void callEndpointLogin(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
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
}
