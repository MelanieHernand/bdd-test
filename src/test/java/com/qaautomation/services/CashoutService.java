package com.qaautomation.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class CashoutService {

    private Response createBankAccountResponse;
    private String createdAccountId;

    public void createBankAccount(String token, String accountNumber, String bankId) {
        JSONObject payload = new JSONObject();
        payload.put("account_number", accountNumber);
        payload.put("bank_id", Integer.parseInt(bankId));

        this.createBankAccountResponse = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", token)
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("https://dev.trii.tech/user/bank-acc/add")
                .prettyPeek();

        if (createBankAccountResponse.getStatusCode() == 200) {
            this.createdAccountId = createBankAccountResponse.jsonPath().getString("account_id");
        }
    }

    public Response getCreateBankAccountResponse() {
        return createBankAccountResponse;
    }

    public String getCreatedAccountId() {
        return createdAccountId;
    }


}
    
