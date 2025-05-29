package com.qaautomation.services.register;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class OtpBypassService {

    private Response response;

    public Response bypassOtp(int userId) {
        String endpoint = "https://be-auth-331873046716.us-east1.run.app/services/bypass-otp?id_user=" + userId;

        response = RestAssured
            .given()
                .relaxedHTTPSValidation()
                .header("accept", "application/json")
            .when()
                .post(endpoint)
            .prettyPeek();

        return response;
    }

    public Response getResponse() {
        return response;
    }
}

