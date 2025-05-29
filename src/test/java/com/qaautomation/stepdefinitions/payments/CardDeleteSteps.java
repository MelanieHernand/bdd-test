package com.qaautomation.stepdefinitions.payments;

import com.qaautomation.ConnectionDB.MySQLUtils;
import com.qaautomation.services.login.LoginService;
import com.qaautomation.services.payments.CardService;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class CardDeleteSteps {

    private final LoginService loginService = new LoginService();
    private final CardService cardService = new CardService();

    private Response response;

    @And("Capturo la última tarjeta antes de eliminar una")
    public void getLastCard() {
        String token = loginService.getToken();
        System.out.println("JOSUE " + token);
        cardService.listCards(token);
        String cardId = cardService.getLastCardId(); // Se setea internamente también
        System.out.println("📇 Última tarjeta capturada con ID: " + cardId);
    }


    @When("Elimino la tarjeta con el endpoint {string} y el id capturado")
    public void deleteCardById(String endpoint) {
        String token = loginService.getToken();
        System.out.println("👉 Token en eliminación: " + token); // DEBUG
        cardService.deleteLastCard(token);
    }

    @Then("La respuesta debe tener un estado de eliminación {string} y status code {string}")
    public void validateCardDeletionResponse(String expectedMessage, String expectedStatusCode) {
        Response response = cardService.getResponse();
        int actualStatusCode = response.getStatusCode();
        String actualMessage = response.jsonPath().getString("response");

        assertEquals("Código de respuesta inesperado", Integer.parseInt(expectedStatusCode), actualStatusCode);
        assertEquals("Mensaje de respuesta inesperado", expectedMessage, actualMessage);
    }

    @And("La tarjeta eliminada no debe estar en el nuevo listado de tarjetas")
    public void validateCardNotPresentInList() {
        String token = loginService.getToken();
        Response listResponse = cardService.listCards(token);
        String deletedId = cardService.getCardId();

        List<Map<String, Object>> cards = listResponse.jsonPath().getList("cards");

        boolean cardStillExists = cards.stream()
                .anyMatch(card -> card.get("id").equals(deletedId));

        assertFalse("La tarjeta eliminada todavía está presente en el listado", cardStillExists);
    }

    @Given("Un usuario ingresa al login con el endpoint {string} y {string} {string} y {string}")
    public void callEndpointLoginWithUser(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
    }

    @When("Intenta eliminar una tarjeta con el id {string} usando el endpoint {string}")
    public void deleteFakeCardById(String cardId, String endpoint) {
    String token = loginService.getToken();
    response = RestAssured
        .given()
        .relaxedHTTPSValidation()
        .header("sk2", token)
        .header("Accept", "application/json")
        .delete("https://dev.trii.tech" + endpoint + "/" + cardId)
        .prettyPeek();
    }

    @Then("La respuesta debe contener el mensaje de error {string} y status code {string}")
    public void validateDeleteCardError(String expectedMessage, String expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        String actualMessage = response.jsonPath().getString("message");
    
        assertEquals("El código de error no es el esperado", Integer.parseInt(expectedStatusCode), actualStatusCode);
        assertEquals("El mensaje de error no es el esperado", expectedMessage, actualMessage);
    }

    @And("En la base de datos el status de la tarjeta debe ser {string}")
    public void validateStatusInDatabase(String expectedStatus) {
    String cardId = cardService.getCardId();
    String query = "SELECT status FROM card WHERE id = '" + cardId + "'";

    MySQLUtils.QueryResult result = MySQLUtils.executeQuery(query, "`be-payments`");

    if (result == null || result.getResultSet() == null) {
        fail("No se pudo ejecutar la consulta o no se devolvió ningún resultado. Verificá conexión y base.");
    }

    try {
        if (result == null) {
            fail("El resultado de la consulta es nulo.");
            return;
        }
        ResultSet rs = result.getResultSet();
        assertTrue("No se encontró la tarjeta en la base de datos", rs.next());

        String dbStatus = rs.getString("status");
        assertEquals("El estado en la base no es el esperado", expectedStatus, dbStatus);
        System.out.println("Validación de BD: status = " + dbStatus);
    } catch (SQLException e) {
        fail("Error al procesar resultado de la base: " + e.getMessage());
    } finally {
        MySQLUtils.closeResources(result);
    }
    }

}
