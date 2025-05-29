package com.qaautomation.stepdefinitions.cashout;

import com.qaautomation.context.TestContext;
import com.qaautomation.services.cashout.CashoutService;
import com.qaautomation.services.login.LoginService;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static org.junit.Assert.*;

public class BankAccountCLPostSteps {
    
    private final TestContext context = new TestContext();
    private final LoginService loginService = new LoginService();
    private final CashoutService cashoutService = new CashoutService();

    @Given("Obtengo el token del endpoint {string} con el usuario {string} {string} y {string}")
    public void loginWithFullUser(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
        context.setCountry(country);
        context.setToken(loginService.getToken()); 
    }

    @When("Agrego una cuenta de banco con el numero {string} y id del banco {string}")
    public void addBankAccount(String accountNumber, String bankId) {
        String token = loginService.getToken();
        assertNotNull("Token inválido o no encontrado. Asegúrate de hacer login primero.", token);

        cashoutService.createBankAccount(token, accountNumber, bankId);
    }

    @Then("El banco debe estar creado y la respuesta debe tener un status {string}")
    public void validateAccountResponse(String expectedStatusCode) {
        Response response = cashoutService.getCreateBankAccountResponse();
        assertNotNull("La respuesta fue nula", response);
        assertEquals("El status code no coincide", Integer.parseInt(expectedStatusCode), response.getStatusCode());

        if (response.getStatusCode() == 200) {
            String accountId = cashoutService.getCreatedAccountId();
            assertNotNull("El account_id no fue retornado en la respuesta", accountId);
            System.out.println("Cuenta de banco creada con ID: " + accountId);
        } else {
            String detail = response.jsonPath().getString("detail");
            assertEquals("El detalle del error no coincide", "Bad Request", detail);
            System.out.println("Error esperado en creación de cuenta de banco: " + detail);
        }
    }

    @Then("La cuenta creada debe estar en el listado de cuentas disponibles")
    public void La_cuenta_creada_debe_estar_en_el_listado_de_cuentas_disponibles() {
        // Write code here that turns the phrase above into concrete actions
    }



}
