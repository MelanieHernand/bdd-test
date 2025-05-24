package com.qaautomation.stepdefinitions.register;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.qaautomation.ConnectionDB.MySQLUtils;
import com.qaautomation.context.TestContext;
import com.qaautomation.services.RegisterService;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;


public class CreateUserCOSteps {

    private final RegisterService registerService = new RegisterService();
    private Response response;

    @Given("Registro un nuevo usuario con el email, {string} y {string}")
    public void createNewUserCO(String password, String country) {
        registerService.registerUser(password, country);
        response = registerService.getRegisterResponse();
    }

    @Then("La respuesta debe incluir el campo {string} y {string}")
    public void laRespuestaDebeTenerUnStatusYUnMensaje(String expectedStatusCode, String expectedResponse) {
        assertNotNull("La respuesta fue nula", response);
        assertEquals("El status no coincide", Integer.parseInt(expectedStatusCode), response.getStatusCode());

        String actualResponse = response.jsonPath().getString("response");
        assertEquals("El mensaje de respuesta no coincide", expectedResponse.toLowerCase(), actualResponse.toLowerCase());
    }


    @Then("La respuesta debe tener un status {string} y un mensaje {string}")
    public void La_respuesta_debe_tener_un_status_y_un_mensaje(String s, String s2) {
        // Write code here that turns the phrase above into concrete actions
    }

}