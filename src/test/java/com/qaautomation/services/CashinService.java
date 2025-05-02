package com.qaautomation.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class CashinService {

    private Response bankListResponse;
    private Response cashinResponse;
    private String cashinId;

    public String getRandomBankCode(String token) {
        bankListResponse = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", token)
                .get("https://dev.trii.tech/cashIn/wompi/listBanks")
                .prettyPeek();

        assertEquals(200, bankListResponse.getStatusCode());

        List<String> codes = bankListResponse.jsonPath().getList("financial_institution_code");
        codes.remove("0"); // Quitamos placeholder
        return codes.get(new Random().nextInt(codes.size()));
    }

    public void postCashin(String token, String bankCode, String amount) {
        JSONObject payload = new JSONObject();
        payload.put("BankCode", bankCode);
        payload.put("Destination", "cashin");
        payload.put("TotalAmount", amount);
        payload.put("NewService", true);

        cashinResponse = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", token)
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("https://dev.trii.tech/cashIn/initTransaction")
                .prettyPeek();
                if (cashinResponse.getStatusCode() == 200) {
                    this.cashinId = cashinResponse.jsonPath().getString("cashin_co_id");
                }
    }

    public Response getCashinResponse() {
        return cashinResponse;
    }

    public String getCashinId() {
        return cashinId;
    }
    
}

