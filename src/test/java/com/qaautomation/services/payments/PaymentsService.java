package com.qaautomation.services.payments;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import com.qaautomation.ConnectionDB.MySQLUtils;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentsService {

    private Response response;

    public void paySubscription(String sk2Token, String country, String plan, String idCard, String amount) {
        JSONObject requestParams = new JSONObject();
    
        if (country.equalsIgnoreCase("CO")) {
            requestParams.put("plan", plan);
            requestParams.put("id_card", idCard);
            requestParams.put("is_pay_bm", false);
            requestParams.put("amount", amount);
            requestParams.put("payment_method", "CARD");
        } else if (country.equalsIgnoreCase("CL")) {
            requestParams.put("plan", plan);
            requestParams.put("amount", amount);
            requestParams.put("payment_method", "BALANCE");
        } else {
            throw new IllegalArgumentException("País no válido: " + country);
        }
    
        this.response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", sk2Token)
                .header("Content-type", "application/json")
                .body(requestParams.toString())
                .post("https://dev.trii.tech/subscription")
                .prettyPeek();
    
        //assertEquals("Fallo el pago de la suscripción", 200, response.getStatusCode());
    }    

    public Response getResponse() {
        return response;
    }

    public void validatePaymentInDatabase(int userId, String expectedAmount, String expectedMethod) {
    String query = "SELECT * FROM payments WHERE ref_user_id = " + userId +
                   " ORDER BY date_creation DESC LIMIT 1";

    MySQLUtils.QueryResult result = MySQLUtils.executeQuery(query, "tu_db");

    try {
        ResultSet rs = result.getResultSet();
        if (rs.next()) {
            assertEquals("Método de pago incorrecto", expectedMethod, rs.getString("payment_method_type"));
            assertEquals("Monto incorrecto", expectedAmount, String.valueOf(rs.getFloat("amount")));
        } else {
            fail("No se encontró un pago reciente para el usuario");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        fail("Error al validar el pago en base de datos");
    } finally {
        MySQLUtils.closeResources(result);
    }
}

}