package com.qaautomation.services.register;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RiskProfileAcceptanceService {

    private Response response;

    public Response acceptRiskProfile(String token) {
        response = RestAssured
            .given()
                .relaxedHTTPSValidation()
                .header("sk2", token)
                .header("accept", "*/*")
                .header("Content-Type", "application/json")
            .when()
                .post("https://dev.trii.tech/user/accept-risk-profile")
            .prettyPeek();

        return response;
    }

    public Response getResponse() {
        return response;
    }
    
}
