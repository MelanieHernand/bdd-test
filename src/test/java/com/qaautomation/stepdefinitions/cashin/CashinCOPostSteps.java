package com.qaautomation.stepdefinitions.cashin;

import com.qaautomation.context.TestContext;
import com.qaautomation.services.CashinService;
import com.qaautomation.services.LoginService;
import com.qaautomation.ConnectionDB.MySQLUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static org.junit.Assert.*;

public class CashinCOPostSteps {

    private final TestContext context;
    private final LoginService loginService = new LoginService();
    private final CashinService cashinService = new CashinService();
    private String bankCodeSeleccionado;

    public CashinCOPostSteps(TestContext context) {
        this.context = context;
    }

    @Given("Me logueo con exito con el endpoint {string} con los parametros {string} {string} y {string}")
    public void callEndpointLoginWithFullUser(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
        context.setCountry(country);
        context.setToken(loginService.getToken());
    }

    @When("Obtengo un banco aleatorio de la lista de bancos del país {string}")
    public void getRandomBank(String country) {
        String token = context.getToken();
            bankCodeSeleccionado = cashinService.getRandomBankCode(token);        
        System.out.println("Banco seleccionado aleatoriamente: " + bankCodeSeleccionado);
    }

    @And("Realizo una orden de cashin con el banco seleccionado y monto {string}")
    public void makeCashinOrder(String amount) {
        String token = context.getToken();
        cashinService.postCashin(token, bankCodeSeleccionado, amount);
    }

    @Then("La orden debe estar registrada en la base de datos {string} con el monto {string} y canal {string}")
    public void validarEnBaseDeDatos(String dbName, String monto, String canal) {
        String cashinId = cashinService.getCashinId() ;
        String query = "SELECT * FROM cashin WHERE id = "+ cashinId;
        var result = com.qaautomation.ConnectionDB.MySQLUtils.executeQuery(query, "`be-cashin-co`"); 

        try {
            ResultSet rs = result.getResultSet();
            assertTrue("No se encontró la orden en la base", rs.next());
            String canalDb = rs.getString("channel");
            float montoDb = rs.getFloat("amount");
            assertEquals("El canal no coincide", canal.toLowerCase(), canalDb.toLowerCase());
            assertEquals("El monto no coincide", Float.parseFloat(monto), montoDb, 0.01f);

            System.out.println("Orden verificada en DB correctamente.");
        } catch (SQLException e) {
            fail("Error al validar la orden de cashin en DB: " + e.getMessage());
        } finally {
            MySQLUtils.closeResources(result);
        }
    }

    @Then("La respuesta debe contener un status {string} y un mensaje de respuesta {string}")
    public void validateCashinResponse(String expectedStatusCode, String expectedResponseField) {
    Response response = cashinService.getCashinResponse();
    assertNotNull("La respuesta fue nula", response);

    int actualStatusCode = response.getStatusCode();
    assertEquals("El status code no coincide", Integer.parseInt(expectedStatusCode), actualStatusCode);

    // Si es código de error, validar por el campo "detail"
    if (actualStatusCode >= 500) {
        String detail = response.jsonPath().getString("detail");
        assertNotNull("No se encontró el campo 'detail' en la respuesta", detail);
        assertEquals("El mensaje de detalle no coincide", expectedResponseField, detail);
    } else {
        // Si es éxito, validar por el campo "status"
        String status = response.jsonPath().getString("status");
        assertNotNull("No se encontró el campo 'status' en la respuesta", status);
        assertEquals("El status no coincide", expectedResponseField, status);
    }
    }

    @When("Realizo una orden de cashin con el banco {string} y monto {string}")
    public void realizarOrdenDeCashinConBancoYMonto(String bankCode, String amount) {
        String token = context.getToken();
        assertNotNull("Token inválido o no encontrado. Asegúrate de hacer login primero.", token);
        
        cashinService.postCashin(token, bankCode, amount);
    }
}