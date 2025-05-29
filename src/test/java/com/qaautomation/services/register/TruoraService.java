package com.qaautomation.services.register;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

public class TruoraService {

    private Response response;

    /**
     * Envía validación de documento (front-id o back-id)
     *
     * @param token
     * @param filePath
     * @param documentType Tipo de documento (ej. national-id, foreing-id, pep, passport)
     * @param side
     * @return 
     */
    public Response validateDocument(String token, String filePath, String documentType, String side) {
        String endpoint = side.equalsIgnoreCase("back-id")
                ? "https://be-compliance-331873046716.us-east1.run.app/truora/validation/back-id"
                : "https://be-compliance-331873046716.us-east1.run.app/truora/validation/front-id";

        RequestSpecification request = RestAssured
            .given()
                .relaxedHTTPSValidation()
                .header("sk2", token)
                .header("accept", "application/json");

        if (filePath != null && !filePath.trim().isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                request
                    .multiPart("file", file)
                    .multiPart("document_type", documentType);
            } else {
                System.out.println("⚠️ La ruta del archivo no existe: " + filePath);
            }
        }

        this.response = request
            .log().all() // Para depuración
            .post(endpoint)
            .prettyPeek(); // Muestra respuesta completa
        return response;
    }

    public Response getResponse() {
        return response;
    }
}
