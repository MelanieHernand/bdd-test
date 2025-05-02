package com.qaautomation.context;

import com.qaautomation.services.CardService;
import com.qaautomation.services.LoginService;
import com.qaautomation.services.PaymentsService;

public class TestContext {
    
    private final LoginService loginService = new LoginService();
    private final CardService cardService = new CardService();
    private final PaymentsService paymentsService = new PaymentsService();

    private String token;
    private String country;
    private String selectedCardId;

    private int userId;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

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
}


