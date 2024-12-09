package com.example.musicapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;  // Inyecta la clave API desde application.properties

    private final RestTemplate restTemplate;

    public OpenAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método para obtener información sobre un cantante
    public String getSingerInfo(String singerName) {
        String url = "https://api.openai.com/v1/chat/completions";

        // Cuerpo de la solicitud, usando el modelo GPT-3.5-turbo
        String requestBody = String.format("{\"model\": \"gpt-3.5-turbo\",\"messages\": [{\"role\": \"system\", \"content\": \"Eres un experto en música. Ayuda a obtener información sobre cantantes.\"}, {\"role\": \"user\", \"content\": \"Dame información sobre el cantante %s\"}]}",
                singerName);

        // Configuración de las cabeceras para la autenticación
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crear la entidad de la solicitud HTTP
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Hacer la solicitud POST a la API de OpenAI
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Verificar el estado de la respuesta
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();  // Devuelve la respuesta del servidor
        } else {
            return "Error al obtener la información.";
        }
    }
}
