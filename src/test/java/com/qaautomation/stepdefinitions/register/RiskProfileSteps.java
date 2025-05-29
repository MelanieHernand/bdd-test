package com.qaautomation.stepdefinitions.register;

import com.qaautomation.ConnectionDB.MySQLUtils;
import com.qaautomation.services.register.RegisterService;
import com.qaautomation.services.register.RiskProfileService;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RiskProfileSteps {

    private final RegisterService registerService = new RegisterService(); 
    private final RiskProfileService riskProfileService = new RiskProfileService();
    private Response response;
    private int lastScoreSent;

    @When("configuro el perfil de riesgo con valor {string}")
    public void envioPerfilDeRiesgo(String scoreStr) {
        lastScoreSent = Integer.parseInt(scoreStr);
        response = riskProfileService.sendRiskProfile(registerService.getRegisterToken(), lastScoreSent);
    }

    @Then("se debe reflejar en la respuesta el risk_profile_score igual a {string}")
    public void validarScoreEnRespuesta(String expectedScore) {
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedScore, String.valueOf(response.jsonPath().getInt("risk_profile_score")));
    }

    @Then("el risk_profile_score debe coincidir en la base de datos {string}")
    public void validarScoreEnBaseDeDatos(String database) throws SQLException {
        String query = "SELECT risk_profile_score FROM user WHERE id = " + registerService.getUserId();
        MySQLUtils.QueryResult result = MySQLUtils.executeQuery(query, database);
        assertNotNull("Error ejecutando la consulta", result);

        ResultSet rs = result.getResultSet();
        assertTrue("No se encontró el usuario", rs.next());
        assertEquals(lastScoreSent, rs.getInt("risk_profile_score"));

        MySQLUtils.closeResources(result);
    }
}

