package com.qaautomation.services;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CardService {

    private Response response;
    private String cardId;
    private String lastCardNumber;

    private List<Map<String, Object>> cardList;

    public void createCard(String endpoint, String sk2Token, String number, String cvc, String expYear, String expMonth) {
        this.lastCardNumber = number; // Guardamos para extraer los últimos 4 después

        JSONObject requestParams = new JSONObject();
        requestParams.put("number", number);
        requestParams.put("cvc", cvc);
        requestParams.put("exp_year", expYear);
        requestParams.put("exp_month", expMonth);
        requestParams.put("card_holder", "Mel He");
        requestParams.put("ip", "192.168.1.1");
        requestParams.put("deviceId", "TEST-DEVICE-123");
        requestParams.put("is_pay_bm", false);

        this.response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", sk2Token)
                .header("Content-type", "application/json")
                .header("Accept", "application/json")
                .body(requestParams.toString())
                .post("https://dev.trii.tech" + endpoint)
                .prettyPeek();

        if (response.getStatusCode() == 200) {
            cardId = response.jsonPath().getString("id");
        }
    }

    public Response listCards(String sk2Token) {
        this.response = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", sk2Token)
                .header("Accept", "application/json")
                .get("https://dev.trii.tech/card/list")
                .prettyPeek();

        this.cardList = response.jsonPath().getList("cards");
        return this.response;
    }

    public String getLastCardId() {
        if (cardList == null || cardList.isEmpty()) return null;

        Map<String, Object> latestCard = cardList.stream()
                .filter(card -> card.get("date_creation") != null)
                .max((c1, c2) -> {
                    String date1 = c1.get("date_creation").toString();
                    String date2 = c2.get("date_creation").toString();
                    return date1.compareTo(date2);
                })
                .orElse(null);

        if (latestCard != null) {
            this.cardId = latestCard.get("id").toString(); // Almacenamos también
            return cardId;
        }

        return null;
    }

    public Map<String, Object> getLastCardData() {
        if (cardList == null || cardList.isEmpty()) return null;
        return cardList.get(cardList.size() - 1);
    }

    public void deleteCard(String sk2Token) {
        if (cardId == null) {
            System.out.println("No hay tarjeta para eliminar.");
            return;
        }

        Response deleteResponse = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", sk2Token)
                .delete("https://dev.trii.tech/card/" + cardId)
                .prettyPeek();

        assertEquals("Falló la eliminación de la tarjeta", 200, deleteResponse.getStatusCode());
        System.out.println("Tarjeta eliminada correctamente.");
    }

    public void deleteLastCard(String sk2Token) {
        String lastIdToDelete = getLastCardId();
        if (lastIdToDelete == null) {
            System.out.println("No hay tarjeta para eliminar.");
            return;
        }

        Response deleteResponse = RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("sk2", sk2Token)
                .header("Accept", "application/json")
                .delete("https://dev.trii.tech/card/" + lastIdToDelete)
                .prettyPeek();

        this.response = deleteResponse;
        int statusCode = deleteResponse.getStatusCode();
        String responseMessage = deleteResponse.jsonPath().getString("response");

        assertEquals("Falló la eliminación de la tarjeta (código)", 200, statusCode);
        assertEquals("La tarjeta no fue eliminada correctamente (mensaje)", "deleted", responseMessage);

        //System.out.println("✅ Tarjeta eliminada correctamente. ID: " + lastIdToDelete);
    }

    public Response getResponse() {
        return response;
    }

    public String getCardId() {
        return cardId;
    }

    public String getLastCardNumber() {
        return lastCardNumber;
    }
}
