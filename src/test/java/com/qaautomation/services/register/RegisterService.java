package com.qaautomation.services.register;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.UUID;

public class RegisterService {

    private Response registerResponse;
    private String token;
    private String generatedEmail;
    private int userId;

    // Método principal con posibilidad de usar email fijo
    public void registerUser(String password, String country, String optionalEmail) {
        // Si se pasa un email, lo usamos; si no, generamos uno aleatorio
        this.generatedEmail = (optionalEmail != null && !optionalEmail.isEmpty())
                ? optionalEmail
                : "test" + UUID.randomUUID().toString().substring(0, 4) + "@yopmail.com";

        JSONObject payload = new JSONObject();
        payload.put("email", generatedEmail);
        payload.put("password", password);
        payload.put("country", country);
        payload.put("referral_code", "");

        this.registerResponse = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("https://dev.trii.tech/auth/register")
                .prettyPeek();

        // Solo si es exitoso (200), guardamos token e ID
        if (registerResponse.getStatusCode() == 200) {
            this.token = registerResponse.jsonPath().getString("token");
            this.userId = registerResponse.jsonPath().getInt("user_id");
        }
    }

    // Método de compatibilidad que usa email aleatorio
    public void registerUser(String password, String country) {
        registerUser(password, country, null);
    }

    // Getters
    public Response getRegisterResponse() {
        return registerResponse;
    }

    public String getRegisterToken() {
        return token;
    }

    public int getUserId() {
        return userId;
    }

    public String getGeneratedEmail() {
        return generatedEmail;
    }
}

