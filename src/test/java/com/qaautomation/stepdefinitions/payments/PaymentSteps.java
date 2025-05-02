package com.qaautomation.stepdefinitions.payments;

import com.qaautomation.ConnectionDB.MySQLUtils;
import com.qaautomation.context.TestContext;
import com.qaautomation.services.LoginService;

import io.cucumber.java.en.*;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.restassured.response.Response;

public class PaymentSteps {

    private final LoginService loginService = new LoginService();
    private final TestContext context;

    public PaymentSteps(TestContext context) {
        this.context = context;
    }

    @Given("Me logueo con el endpoint {string} con los parametros {string} {string} y {string}")
    public void callEndpointLoginWithUser(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
        context.setCountry(country);
        context.setToken(loginService.getToken());
    }

    @When("Preparo una tarjeta activa para el pago")
    public void prepareCardForPayment() {
        String sk2Token = context.getToken();
        assertNotNull("El token es nulo. Verifica que el login fue exitoso.", sk2Token);

        System.out.println("🛡 Token recibido para preparar tarjeta: " + sk2Token);

        String cardId = context.getCardService().prepareCardForPayment(sk2Token);
        assertNotNull("No se pudo obtener una tarjeta activa", cardId);

        context.setSelectedCardId(cardId);
        System.out.println("Tarjeta activa seleccionada: " + cardId);
    }

    @And("Realizo un pago con el plan {string} y el monto {string}")
    public void makeThePayment(String plan, String amount) {
        String sk2Token = context.getToken();
        assertNotNull("El token es nulo antes de realizar el pago", sk2Token);

        String country = context.getCountry();
        String cardId = context.getSelectedCardId();

        if (country.equalsIgnoreCase("CO")) {
            context.getPaymentsService().paySubscription(sk2Token, country, plan, cardId, amount);
        } else if (country.equalsIgnoreCase("CL")) {
            context.getPaymentsService().paySubscription(sk2Token, country, plan, null, amount);
        } else {
            fail("País no soportado: " + country);
        }
    }

    @Then("La respuesta debe tener un status {string} y un mensaje de respuesta {string}")
    public void validatePaymentResponse(String expectedStatusCode, String expectedResponse) {
        var response = context.getPaymentsService().getResponse();
        assertNotNull("La respuesta del pago es nula", response);
    
        int actualStatus = response.getStatusCode();
        assertEquals("El código de estado no coincide", Integer.parseInt(expectedStatusCode), actualStatus);
    
        // En respuestas exitosas, usamos el campo "response"
        if (actualStatus == 200) {
            String actualMessage = response.jsonPath().getString("response");
            assertEquals("El mensaje de respuesta no coincide", expectedResponse, actualMessage);
        } else {
            // En errores, usamos el campo "detail"
            String detailMessage = response.jsonPath().getString("detail");
            assertEquals("El detalle de error no coincide", expectedResponse, detailMessage);
        }
    }
    

    @And("El pago debe estar registrado en la base de datos con el monto {string} y metodo {string}")
    public void validatePaymentInDatabase(String expectedAmount, String expectedMethod) {
    String query = "SELECT amount, payment_method_type FROM payments ORDER BY date_creation DESC LIMIT 1";

    var result = com.qaautomation.ConnectionDB.MySQLUtils.executeQuery(query, "`be-payments`");

    if (result == null || result.getResultSet() == null) {
        fail("No se pudo ejecutar la consulta o no se devolvió ningún resultado. Verificá conexión y base.");
    }

    try {
        ResultSet rs = result.getResultSet();
        assertTrue("No se encontró un pago reciente en la base de datos", rs.next());

        String dbMethod = rs.getString("payment_method_type");
        float dbAmount = rs.getFloat("amount");

        System.out.println("📦 Último pago en BD → Método: " + dbMethod + " | Monto: " + dbAmount);

        assertEquals("Método de pago incorrecto en base", expectedMethod, dbMethod);
        assertEquals("Monto incorrecto en base", Float.parseFloat(expectedAmount), dbAmount, 0.01f);
    } catch (SQLException e) {
        fail("Error al procesar resultado de la base: " + e.getMessage());
    } finally {
        com.qaautomation.ConnectionDB.MySQLUtils.closeResources(result);
    }

    System.out.println("✔ Validación de pago en BD completada correctamente.");
    }

}