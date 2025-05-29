package com.qaautomation.services.cashIn;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.io.File;
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

    public void postCashinChile(String token, String institutionId, String amount) {
        JSONObject payload = new JSONObject();
        payload.put("institution_id", institutionId);
        payload.put("amount", amount);
    
        this.cashinResponse = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", token)
                .header("Content-Type", "application/json")
                .body(payload.toString())
                .post("https://dev.trii.tech/cashIn/fintoc/start/transaction")
                .prettyPeek();
    
        if (cashinResponse.getStatusCode() == 200) {
            this.cashinId = cashinResponse.jsonPath().getString("data.id_cashin_cl");
        }
    }

    public void postCashinPeru(String token, String filePath) {
        var request = RestAssured
        .given()
        .relaxedHTTPSValidation()
        .header("sk2", token);

    // Solo adjunta archivo si la ruta es válida
    if (filePath != null && !filePath.trim().isEmpty()) {
        File file = new File(filePath);
        if (file.exists()) {
            request.multiPart("file", file);
        } else {
            System.out.println("⚠️ La ruta del archivo no existe: " + filePath);
        }
    }

    this.cashinResponse = request
        .post("https://dev.trii.tech/cashIn/initTransaction")
        .prettyPeek();

    // Guardar el ID solo si la respuesta fue exitosa
    if (cashinResponse.getStatusCode() == 200 && cashinResponse.jsonPath().get("id") != null) {
        this.cashinId = cashinResponse.jsonPath().getString("id");
    }
    }

    public void getCashinListPeru(String token) {
    this.cashinResponse = RestAssured
        .given()
        .relaxedHTTPSValidation()
        .header("sk2", token)
        .get("https://dev.trii.tech/cashIn/list")
        .prettyPeek();
    }

}

