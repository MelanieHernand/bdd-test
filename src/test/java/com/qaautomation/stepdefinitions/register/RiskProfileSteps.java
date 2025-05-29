package com.qaautomation.stepdefinitions.register;

import com.qaautomation.ConnectionDB.MySQLUtils;
import com.qaautomation.context.TestContext;
import com.qaautomation.services.register.RiskProfileAcceptanceService;
import com.qaautomation.services.register.RiskProfileService;
import com.qaautomation.stepdefinitions.Hooks;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class RiskProfileSteps {

    private final RiskProfileService riskProfileService = new RiskProfileService();
    private final TestContext context = Hooks.context;

    private Response response;

    @When("configuro el perfil de riesgo con valor {string}")
    public void envioPerfilDeRiesgo(String scoreStr) {
        int score = Integer.parseInt(scoreStr);

        // Guardar en el contexto
        context.setRiskProfileScore(score);

        response = riskProfileService.sendRiskProfile(context.getToken(), score);
    }

    @Then("se debe reflejar en la respuesta el risk_profile_score igual a {string}")
    public void validarScoreEnRespuesta(String expectedScore) {
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedScore, String.valueOf(response.jsonPath().getInt("risk_profile_score")));
    }

    @Then("el risk_profile_score debe coincidir en la base de datos {string}")
    public void validarScoreEnBaseDeDatos(String database) throws SQLException {
        String query = "SELECT risk_profile_score FROM user WHERE id = " + context.getUserId();
        MySQLUtils.QueryResult result = MySQLUtils.executeQuery(query, "`" + database + "`");
        assertNotNull("No se pudo ejecutar la consulta", result);

        ResultSet rs = result.getResultSet();
        assertTrue("No se encontró el usuario", rs.next());
        assertEquals(context.getRiskProfileScore(), rs.getInt("risk_profile_score"));

        MySQLUtils.closeResources(result);
    }

    @When("acepto los términos del perfil de riesgo")
    public void aceptoTerminosPerfilRiesgo() {
        RiskProfileAcceptanceService acceptanceService = new RiskProfileAcceptanceService();
        response = acceptanceService.acceptRiskProfile(context.getToken());
    }

    @Then("el campo risk_profile_accept_timestamp debe estar presente en la base de datos {string}")
    public void validarRiskProfileAceptadoEnDB(String database) {
        int userId = context.getUserId();
        String query = "SELECT risk_profile_accept_timestamp FROM user WHERE id = " + userId;

        var result = MySQLUtils.executeQuery(query, "`" + database + "`");

    try {
        ResultSet rs = result.getResultSet();
        assertTrue("No se encontró el usuario en la base de datos", rs.next());
        assertNotNull("El campo risk_profile_accept_timestamp está NULL", rs.getTimestamp("risk_profile_accept_timestamp"));

        System.out.println("✅ risk_profile_accept_timestamp confirmado para user_id: " + userId);
    } catch (Exception e) {
        fail("Error al validar risk_profile_accept_timestamp: " + e.getMessage());
    } finally {
        MySQLUtils.closeResources(result);
    }
    }

}
