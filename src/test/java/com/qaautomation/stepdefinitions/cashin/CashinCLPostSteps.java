package com.qaautomation.stepdefinitions.cashin;

import com.qaautomation.context.TestContext;
import com.qaautomation.services.cashIn.CashinService;
import com.qaautomation.services.login.LoginService;
import com.qaautomation.ConnectionDB.MySQLUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static org.junit.Assert.*;

public class CashinCLPostSteps {

    private final TestContext context = new TestContext();
    private final LoginService loginService = new LoginService();
    private final CashinService cashinService = new CashinService();

    @Given("Realizo login en el endpoint {string} con los parametros {string} {string} y {string}")
    public void loginWithFullUser(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
        context.setCountry(country);
        context.setToken(loginService.getToken()); 
    }

    @When("Realizo una orden de cashin en Chile con el banco {string} y monto {string}")
    public void realizarOrdenCashinChile(String institutionId, String amount) {
        String token = loginService.getToken();
        assertNotNull("Token inválido o no encontrado. Asegúrate de hacer login primero.", token);

        cashinService.postCashinChile(token, institutionId, amount);
    }

    @Then("La respuesta del cashin debe contener un status {string} y un mensaje de respuesta {string}")
    public void validateCashinResponse(String expectedStatusCode, String expectedMessage) {
        Response response = cashinService.getCashinResponse();
        assertNotNull("La respuesta fue nula", response);
        assertEquals("El status code no coincide", Integer.parseInt(expectedStatusCode), response.getStatusCode());
    
        if ("null".equalsIgnoreCase(expectedMessage)) {
            // Escenario negativo: debe tener data=null
            Object dataField = response.jsonPath().get("data");
            assertNull("Se esperaba que el campo 'data' fuera null", dataField);
    
            String errorField = response.jsonPath().getString("error");
            assertNotNull("Se esperaba un mensaje en el campo 'error'", errorField);
    
            System.out.println("Validación de error OK: data=null y error presente.");
        } else {
            // Escenario positivo: validamos status dentro de data
            String status = response.jsonPath().getString("data.status");
            assertNotNull("No se encontró el campo 'data.status'", status);
            assertEquals("El status de la data no coincide", expectedMessage.toLowerCase(), status.toLowerCase());
    
            System.out.println("Validación de success OK: status dentro de data = " + status);
        }
    }



}
