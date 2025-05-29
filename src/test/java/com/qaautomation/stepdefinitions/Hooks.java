package com.qaautomation.stepdefinitions;

import com.qaautomation.context.TestContext;
import io.cucumber.java.Before;

public class Hooks {
    public static final TestContext context = new TestContext();

    @Before
    public void beforeScenario() {
        // Agregar logs, limpieza de variables, etc.
    }
}
