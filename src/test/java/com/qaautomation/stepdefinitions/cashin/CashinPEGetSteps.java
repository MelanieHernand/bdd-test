package com.qaautomation.stepdefinitions.cashin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import com.qaautomation.context.TestContext;
import com.qaautomation.services.cashIn.CashinService;
import com.qaautomation.services.login.LoginService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class CashinPEGetSteps {

    private final TestContext context = new TestContext();
    private final LoginService loginService = new LoginService();
    private final CashinService cashinService = new CashinService();

    @Given("Ingreso al login {string} para obtener el token con las credenciales de {string} , {string} , {string}")
    public void loginWithFullUserString(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
        context.setCountry(country);
        context.setToken(loginService.getToken()); 
    }

    @When("Consulto la lista de cashin del usuario en Perú")
    public void getCashinList() {
        String token = loginService.getToken();
        assertNotNull("Token no disponible", token);

    cashinService.getCashinListPeru(token);
    }   

    @Then("La respuesta debe contener una lista de transacciones con IDs y mostrar cuántas tiene")
    public void validarListaCashinPeru() {
        Response response = cashinService.getCashinResponse();
        assertNotNull("La respuesta fue nula", response);
        assertEquals(200, response.getStatusCode());

    List<Map<String, Object>> cashins = response.jsonPath().getList("");
        assertNotNull("La lista está vacía", cashins);
        assertFalse("No hay transacciones", cashins.isEmpty());

    int count = 0;
    for (Map<String, Object> cashin : cashins) {
        Object id = cashin.get("id");
        assertNotNull("Se esperaba un campo 'id' en cada transacción", id);
        count++;
    }

    System.out.println("Total de transacciones de cashin encontradas: " + count);
    }

}


