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

public class CashinPEPostSteps {

    private final TestContext context = new TestContext();
    private final LoginService loginService = new LoginService();
    private final CashinService cashinService = new CashinService();
    
    @Given("Obtengo el token del endpoint {string} con los parametros {string} {string} y {string}")
    public void loginWithFullUserString(String endpoint, String email, String pass, String country) {
        loginService.login(endpoint, email, pass, country);
        context.setCountry(country);
        context.setToken(loginService.getToken()); 
    }

    @When("Realizo una orden de cashin en Peru enviando la imagen del comprobante {string}")
    public void placeCashinOrderInPeru(String filePath) {
        String token = loginService.getToken();
        assertNotNull("Token inválido o no encontrado. Asegúrate de hacer login primero.", token);

        cashinService.postCashinPeru(token, filePath);
    }

    @Then("La respuesta del cashin de Perú debe tener un status {string} y un mensaje de respuesta {string}")
    public void validateCashinResponsePeru(String expectedStatusCode, String expectedResponse) {
    Response response = cashinService.getCashinResponse();

    assertNotNull("La respuesta fue nula", response);
    assertEquals("El status code no coincide", Integer.parseInt(expectedStatusCode), response.getStatusCode());

    // Caso exitoso: existe el campo "response"
    if ("ok".equalsIgnoreCase(expectedResponse)) {
        String actualResponse = response.jsonPath().getString("response");
        assertNotNull("Se esperaba un campo 'response' en la respuesta", actualResponse);
        assertEquals("El campo 'response' no coincide", expectedResponse.toLowerCase(), actualResponse.toLowerCase());
        System.out.println("✅ Validación exitosa de cashin Perú: response = " + actualResponse);
    } else {
        // Caso de error: existe el campo "message"
        String actualMessage = response.jsonPath().getString("message");
        assertNotNull("Se esperaba un campo 'message' en la respuesta", actualMessage);
        assertEquals("El mensaje de error no coincide", expectedResponse.toLowerCase(), actualMessage.toLowerCase());
        System.out.println("⚠️ Validación de error cashin Perú: message = " + actualMessage);
        }
    }

    @And("La orden debe estar registrada en la base de datos de Perú {string}")
    public void validateInDataBase(String dbName) {
        String cashinId = cashinService.getCashinId();
        assertNotNull("El cashinId es nulo. No se puede validar en la base.", cashinId);

    String query = "SELECT * FROM cashin WHERE id = " + cashinId;
    var result = com.qaautomation.ConnectionDB.MySQLUtils.executeQuery(query, "`be-cashin-pe`");
    assertNotNull("No se obtuvo resultado de la base. Verifica la conexión o la query.", result);

    try {
        ResultSet rs = result.getResultSet();
        assertTrue("No se encontró la orden en la base", rs.next());

        String idDb = rs.getString("id");
        assertEquals("El ID no coincide con el registrado", cashinId, idDb);

        System.out.println("✅ Orden verificada en la base de datos de Perú correctamente.");
    } catch (SQLException e) {
        fail("Error al acceder a los resultados: " + e.getMessage());
    } finally {
        com.qaautomation.ConnectionDB.MySQLUtils.closeResources(result);
    }

    }

}
