package com.qaautomation.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RegisterService {

    private Response registerResponse;
    private String token;
    private String userId;
    private String generatedEmail;

    public void registerUser(String password, String country) {
        // Email aleatorio para evitar duplicados
        String email = "test" + UUID.randomUUID().toString().substring(0, 4) + "@yopmail.com";
        this.generatedEmail = email;

        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("country", country);
        payload.put("referral_code", "");

        registerResponse = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("https://dev.trii.tech/auth/register")
                .prettyPeek();

        assertEquals(200, registerResponse.getStatusCode());

        this.token = registerResponse.jsonPath().getString("token");
        this.userId = registerResponse.jsonPath().getString("user_id");

        assertNotNull("Token no generado", token);
        assertNotNull("User ID no generado", userId);
    }

    public Response getRegisterResponse() {
        return registerResponse;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getGeneratedEmail() {
        return generatedEmail;
    }
}
