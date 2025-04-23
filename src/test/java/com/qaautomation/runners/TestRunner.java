package com.qaautomation.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.qaautomation.stepdefinitions",
        plugin = {
            "pretty",
            "html:target/cucumber-report.html",
            "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = "@test",
        monochrome = true
)
public class TestRunner {
}
