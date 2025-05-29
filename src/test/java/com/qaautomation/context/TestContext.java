package com.qaautomation.context;

import com.qaautomation.services.login.LoginService;
import com.qaautomation.services.payments.CardService;
import com.qaautomation.services.payments.PaymentsService;

public class TestContext {

    // Servicios compartidos
    private final LoginService loginService = new LoginService();
    private final CardService cardService = new CardService();
    private final PaymentsService paymentsService = new PaymentsService();

    // Variables generales
    private String token;
    private String country;
    private String selectedCardId;
    private int userId;

    // Variables para flujo de registro
    private String email;
    private String n_id;
    private int riskProfileScore;

    // Getters y Setters generales
    public LoginService getLoginService() {
        return loginService;
    }

    public CardService getCardService() {
        return cardService;
    }

    public PaymentsService getPaymentsService() {
        return paymentsService;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSelectedCardId() {
        return selectedCardId;
    }

    public void setSelectedCardId(String selectedCardId) {
        this.selectedCardId = selectedCardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Getters y Setters de flujo de registro
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getN_id() {
        return n_id;
    }

    public void setN_id(String n_id) {
        this.n_id = n_id;
    }

    public int getRiskProfileScore() {
        return riskProfileScore;
    }

    public void setRiskProfileScore(int riskProfileScore) {
        this.riskProfileScore = riskProfileScore;
    }
}
