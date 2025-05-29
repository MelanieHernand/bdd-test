package com.qaautomation.stepdefinitions.register;

import com.qaautomation.services.register.RegisterService;
import com.qaautomation.services.register.UserDataService;
import com.qaautomation.ConnectionDB.MySQLUtils;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

import io.cucumber.java.en.Given;

public class UserDataSteps {

    private final RegisterService registerService = new RegisterService();
    private final UserDataService userDataService = new UserDataService();
    private Response response;
    private JSONObject userSent;

    @Given("Envío los datos básicos del usuario con n_id {string} y demás datos generados")
    public void envioDatosBasicos(String n_id) {
        userSent = userDataService.generateValidUserData(n_id);
        response = userDataService.sendUserBasicData(registerService.getRegisterToken(), userSent);
    }

    @Then("Si hay duplicado, la respuesta debe tener status {string} y mensaje {string}")
    public void validarErrorDuplicado(String expectedMessage) {
        assertEquals(502, response.getStatusCode());
        assertTrue(response.jsonPath().getString("detail").contains(expectedMessage));
    }

    @Then("Si es exitoso, la respuesta debe tener {string}")
    public void validarExito(String expectedResponse) {
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedResponse, response.jsonPath().getString("response"));
    }

    @Then("los datos del usuario deben coincidir en la base de datos {string}")
    public void validarDatosEnBase(String database) throws SQLException {
        String query = "SELECT first_name, last_name, n_id, user_status FROM " + database + ".user WHERE id = " + registerService.getUserId();
        ResultSet rs = MySQLUtils.executeQuery(query);

        assertTrue("No se encontró el usuario en la base", rs.next());
        assertEquals(userSent.getString("first_name"), rs.getString("first_name"));
        assertEquals(userSent.getString("last_name"), rs.getString("last_name"));
        assertEquals(userSent.getString("n_id"), rs.getString("n_id"));
        assertEquals("basic_data", rs.getString("user_status"));

        rs.close();
    }
}

