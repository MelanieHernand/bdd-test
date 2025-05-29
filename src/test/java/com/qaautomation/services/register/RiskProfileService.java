package com.qaautomation.services.register;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

public class RiskProfileService {

    private Response response;

    public Response sendRiskProfile(String token, int score) {
        JSONObject payload = new JSONObject();
        payload.put("risk_profile", score);

        this.response = RestAssured
            .given()
                .relaxedHTTPSValidation()
                .header("Content-Type", "application/json")
                .header("sk2", token)
                .body(payload.toString())
            .when()
                .post("https://dev.trii.tech/user/set-risk-profile")
            .prettyPeek();

        return this.response;
    }

    public Response getResponse() {
        return this.response;
    }
}

