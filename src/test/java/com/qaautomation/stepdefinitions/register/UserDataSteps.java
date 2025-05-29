package com.qaautomation.stepdefinitions.register;

import com.qaautomation.ConnectionDB.MySQLUtils;
import com.qaautomation.context.TestContext;
import com.qaautomation.services.register.UserDataService;
import com.qaautomation.stepdefinitions.Hooks;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserDataSteps {

    private final UserDataService userDataService = new UserDataService();
    private final TestContext context = Hooks.context;

    private Response response;
    private JSONObject userSent;

    @When("envío los datos básicos del usuario con n_id {string} y demás datos generados")
    public void envioDatosBasicos(String n_id) {
        userSent = userDataService.generateValidUserData(n_id);

        // Guardamos el n_id generado o enviado en el contexto
        context.setN_id(userSent.getString("n_id"));

        response = userDataService.sendUserBasicData(context.getToken(), userSent);
    }

    @Then("si hay duplicado, la respuesta debe tener status 502 y mensaje {string}")
    public void validarErrorDuplicado(String expectedMessage) {
        assertEquals(502, response.getStatusCode());
        assertTrue(response.jsonPath().getString("detail").contains(expectedMessage));
    }

    @Then("si es exitoso, la respuesta debe tener {string}")
    public void validarExito(String expectedResponse) {
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedResponse, response.jsonPath().getString("response"));
    }

    @Then("los datos del usuario deben coincidir en la base de datos {string}")
    public void validarDatosEnBase(String database) throws SQLException {
        String query = "SELECT first_name, last_name, n_id, user_status FROM user WHERE id = " + context.getUserId();
        MySQLUtils.QueryResult result = MySQLUtils.executeQuery(query, database);

        assertNotNull("No se pudo ejecutar la consulta", result);
        ResultSet rs = result.getResultSet();

        assertTrue("No se encontró el usuario", rs.next());
        assertEquals(userSent.getString("first_name"), rs.getString("first_name"));
        assertEquals(userSent.getString("last_name"), rs.getString("last_name"));
        assertEquals(userSent.getString("n_id"), rs.getString("n_id"));
        assertEquals("basic_data", rs.getString("user_status"));

        MySQLUtils.closeResources(result);
    }
}
