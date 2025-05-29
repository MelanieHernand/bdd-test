package com.qaautomation.stepdefinitions.register;

import com.qaautomation.context.TestContext;
import com.qaautomation.services.register.OtpBypassService;
import com.qaautomation.stepdefinitions.*;
import com.qaautomation.ConnectionDB.MySQLUtils;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.sql.ResultSet;

import static org.junit.Assert.*;

public class OtpSteps {

    private final TestContext context = Hooks.context;
    private final OtpBypassService otpBypassService = new OtpBypassService();

    @When("realizo el bypass de OTP para el usuario")
    public void realizoBypassOtp() {
        int userId = context.getUserId();
        otpBypassService.bypassOtp(userId);
    }

    @Then("los campos de confirmación OTP deben estar presentes en la base de datos {string}")
    public void validarConfirmacionesOtpEnBase(String database) {
        int userId = context.getUserId();
        String query = "SELECT date_otp_confirm_email, date_otp_confirm_phone FROM user WHERE id = " + userId;

        var result = MySQLUtils.executeQuery(query, "`" + database + "`");

        if (result == null) {
        fail("No se pudo ejecutar la consulta en la base de datos: " + database);
        }

        try {
            ResultSet rs = result.getResultSet();
            assertTrue("No se encontró el usuario", rs.next());

            assertNotNull("date_otp_confirm_email es NULL", rs.getTimestamp("date_otp_confirm_email"));
            assertNotNull("date_otp_confirm_phone es NULL", rs.getTimestamp("date_otp_confirm_phone"));

            System.out.println("OTP confirmado en base para email y teléfono (user_id: " + userId + ")");
        } catch (Exception e) {
            fail("Error al validar confirmaciones OTP en base: " + e.getMessage());
        } finally {
            MySQLUtils.closeResources(result);
        }
    }
}

