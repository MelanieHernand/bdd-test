package com.qaautomation.context;

import com.qaautomation.services.CardService;
import com.qaautomation.services.LoginService;

public class TestContext {
    private final LoginService loginService = new LoginService();
    private final CardService cardService = new CardService();

    public LoginService getLoginService() {
        return loginService;
    }

    public CardService getCardService() {
        return cardService;
    }
}

