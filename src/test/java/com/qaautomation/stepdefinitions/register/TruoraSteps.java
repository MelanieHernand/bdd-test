package com.qaautomation.stepdefinitions.register;

import com.qaautomation.context.TestContext;
import com.qaautomation.services.register.TruoraService;
import com.qaautomation.stepdefinitions.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TruoraSteps {
    
    private final TruoraService docService = new TruoraService();
    private final TestContext context = Hooks.context;
    private Response response;

    @Given("envío la imagen del documento con ruta {string}, tipo {string} y lado {string}")
    public void envioImagenDocumento(String filePath, String documentType, String side) {
        response = docService.validateDocument(context.getToken(), filePath, documentType, side);
        context.setSelectedCardId(side); 
    }

    @Then("la respuesta de validación debe tener status {string}")
    public void validarStatusDeValidacion(String expectedStatusCode) {
        assertNotNull("La respuesta es nula", response);
        assertEquals(Integer.parseInt(expectedStatusCode), response.getStatusCode());
    }

}
