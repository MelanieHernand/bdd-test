package com.qaautomation.services.register;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.UUID;
import java.util.Random;

public class UserDataService {

    private Response response;

    public Response sendUserBasicData(String token, JSONObject userData) {
        this.response = RestAssured
            .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("sk2", token)
                .body(userData.toString())
            .when()
                .post("https://dev.trii.tech/user/set-data")
            .prettyPeek();

        return response;
    }

    public Response getResponse() {
        return response;
    }

    public JSONObject generateValidUserData(String n_id) {
        if (n_id == null || n_id.isEmpty()) {
            // Generar un número aleatorio de 6 dígitos
            Random random = new Random();
            int numeroAleatorio = 100000 + random.nextInt(900000);
            n_id = "79" + numeroAleatorio;
        }

        JSONObject json = new JSONObject();
        json.put("accept_privacy", true);
        json.put("date_born", "2000-03-26 00:00:00.000");
        json.put("first_name", "GUSTAVO");
        json.put("first_name_2", "ADOLFO");
        json.put("last_name", "ROBAYO");
        json.put("last_name_2", "GARCIA");
        json.put("phone", "3144442153");
        json.put("phone_code", "56");
        json.put("type_n_id", "CC");
        json.put("n_id", n_id);
        json.put("ciuu_code", "10");
        json.put("resident", "N");
        json.put("pay_rent", "N");
        json.put("gender", "M");
        json.put("civil_status", "S");
        json.put("city_code", "127");
        json.put("state_code", "111");
        json.put("address", "Calle 100");
        json.put("date_n_id", "2025-05-20 00:00:00.000");
        return json;
    }
}