package com.qaautomation.services.register;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

public class TruoraService {

    private Response response;

    /**
     * Envía una validación de documento (front-id o back-id)
     *
     * @param token        Token de autenticación (sk2)
     * @param filePath     Ruta al archivo de imagen
     * @param documentType Tipo de documento (ej: national-id)
     * @param side         "front" o "back"
     * @return Response de la API
     */
    public Response validateDocument(String token, String filePath, String documentType, String side) {
        String endpoint = side.equalsIgnoreCase("back")
                ? "https://be-compliance-331873046716.us-east1.run.app/truora/validation/back-id"
                : "https://be-compliance-331873046716.us-east1.run.app/truora/validation/front-id";

        RequestSpecification request = RestAssured
            .given()
                .relaxedHTTPSValidation()
                .header("sk2", token)
                .header("accept", "application/json")
                .header("Content-Type", "multipart/form-data")
                .multiPart("document_type", documentType);

        // Validación y carga del archivo
        if (filePath != null && !filePath.trim().isEmpty()) {
            File file = new File(filePath);
            if (file.exists()) {
                request.multiPart("file", file);
            } else {
                System.out.println("⚠️ La ruta del archivo no existe: " + filePath);
            }
        }

        this.response = request.post(endpoint).prettyPeek();
        return response;
    }

    public Response getResponse() {
        return response;
    }
}