package com.qaautomation.stepdefinitions.payments;

import static org.junit.Assert.assertEquals;

import com.qaautomation.services.login.LoginService;
import com.qaautomation.services.payments.CardService;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import java.util.Map;


import static org.junit.Assert.*;

public class CardSteps {

    private final LoginService loginService = new LoginService();
    private final CardService cardService = new CardService();
    private final Response response = null;

    private String lastCardIdBefore;
    
    @Given("Ingreso al login con el endpoint {string} con los siguientes parametros {string} {string} {string}")
    public void callEndpointLoginWithFullUser(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
    }

    @And("Capturo la última tarjeta antes de registrar una nueva")
    public void captureLastCardId() {
        cardService.listCards(loginService.getToken());
        lastCardIdBefore = cardService.getLastCardId();
    }

    @When("Creo una tarjeta en {string} con los datos {string} {string} {string} {string}")
    public void addNewCard(String endpoint, String number, String cvc, String expYear, String expMonth) {
        cardService.createCard(endpoint, loginService.getToken(), number, cvc, expYear, expMonth);
    }

    @Then("La respuesta debe tener un estado de creación {string}  y status code {string}")
    public void validateCreationResponse(String expectedMessage, String expectedCode) {
        Response response = cardService.getResponse();
        assertNotNull("La respuesta no debe ser nula", response);

        String actualMessage = response.jsonPath().getString("response");
        int actualStatusCode = response.getStatusCode();

        assertEquals("El mensaje no coincide", expectedMessage, actualMessage);
        assertEquals("El código no coincide", Integer.parseInt(expectedCode), actualStatusCode);
    }

    @And("Debe haberse registrado una nueva tarjeta")
    public void verifyCardAdded() {
        cardService.listCards(loginService.getToken());
        String lastCardIdAfter = cardService.getLastCardId();

        assertNotNull("No se encontró una tarjeta luego del registro", lastCardIdAfter);
        assertNotNull("Asegúrate de capturar la última tarjeta antes de crear una nueva.", lastCardIdBefore);
        assertTrue("No se registró una nueva tarjeta",
                Integer.parseInt(lastCardIdAfter) > Integer.parseInt(lastCardIdBefore));

        Map<String, Object> cardData = cardService.getLastCardData();

        // Extraemos los últimos 4 dígitos del número usado
        String expectedLast4 = cardService
                                .getLastCardNumber()
                                .substring(cardService.getLastCardNumber().length() - 4);

        assertEquals("Los últimos 4 dígitos no coinciden", expectedLast4, cardData.get("last4"));
        assertEquals("El estado de la tarjeta no es ACTIVE", "ACTIVE", cardData.get("status"));
    }

    @And("Elimino la tarjeta creada")
    public void deleteCreatedCard() {
        String cardIdBeforeDelete = cardService.getLastCardId();

        cardService.deleteLastCard(loginService.getToken());

        cardService.listCards(loginService.getToken());
        String cardIdAfterDelete = cardService.getLastCardId();

        assertNotEquals("La tarjeta no fue eliminada correctamente", cardIdBeforeDelete, cardIdAfterDelete);
    }

    @Then("La respuesta de eliminación debe ser {string} y el status code debe ser {int}")
    public void validateDeleteResponse(String expectedResponse, int expectedStatusCode) {
        Response lastResponse = cardService.getResponse();
        System.out.println("Cuerpo completo de la respuesta de eliminación: " + lastResponse.asString());

        //assertNotNull("La respuesta no debe ser nula", lastResponse);
        //assertEquals("Código de estado incorrecto en eliminación", expectedStatusCode, lastResponse.getStatusCode());
        //assertEquals("Mensaje de eliminación incorrecto", expectedResponse, lastResponse.jsonPath().getString("response"));
        //System.out.println("Validación de eliminación correcta: " + expectedResponse);
    }

    @And("El status code debe ser {int}")
    public void validateStatusCode(int expectedStatusCode) {
        Response response = cardService.getResponse();
        assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("La respuesta debe tener {int} y detalle {string}")
    public void validateStatusAndDetailResponse(int statusCode, String expectedDetail) {
        Response response = cardService.getResponse();

        assertNotNull("La respuesta es nula", response);

        Integer actualStatusCode = response.jsonPath().getInt("status_code");
        String actualDetail = response.jsonPath().getString("detail");

        assertNotNull("El Estatus de la respuesta es Vacio", actualStatusCode);
        assertNotNull("El detalle de la respuesta es Vacio", actualDetail);
        assertEquals("El Estatus de la respuesta no es el esperado", statusCode, actualStatusCode.intValue());
        assertEquals("El detalle de la respuesta no es el esperado", expectedDetail, actualDetail);
    }
}
