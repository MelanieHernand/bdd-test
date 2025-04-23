package com.qaautomation.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import com.qaautomation.ConnectionDB.MySQLUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class LoginService {

    private String token;
    private Response response;

    public void login(String endpoint, String email, String pass, String country) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("email", email);
        requestParams.put("password", pass);
        requestParams.put("country", country);

        Response resp = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-type", "application/json")
                .body(requestParams.toString())
                .post("https://dev.trii.tech/" + endpoint)
                .prettyPeek();

        if (resp.getStatusCode() == 200) {
            String tokenResp = resp.jsonPath().getString("token");
            if (tokenResp != null && !tokenResp.isEmpty()) {
                this.token = tokenResp;
            }
        }

        this.response = resp;
    }

    public String getToken() {
        return token;
    }

    public Response getResponse() {
        return response;
    }


    public void validateUserInDatabase(String email, String databaseName) {
        String query = "SELECT * FROM user WHERE email = '" + email + "'";
        MySQLUtils.QueryResult queryResult = MySQLUtils.executeQuery(query, databaseName);

        try {
            if (queryResult != null && queryResult.getResultSet() != null) {
                ResultSet resultSet = queryResult.getResultSet();
                assertTrue("El usuario con email " + email + " no existe en la base de datos", resultSet.next());
                System.out.println("Usuario encontrado: " + resultSet.getString("email"));
            } else {
                fail("No se pudo ejecutar la consulta o no se devolvió ningún resultado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al procesar el resultado: " + e.getMessage());
        } finally {
            MySQLUtils.closeResources(queryResult);
        }
    }

}
