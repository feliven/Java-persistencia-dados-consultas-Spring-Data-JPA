package br.com.alura.screenmatch.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

@Service
public class ConsultaGemini {

    private static String apiKey;

    @Value("${gemini.api-key}")
    public void setApiKey(String apiKey) {
        ConsultaGemini.apiKey = apiKey;
    }

    public static String obterTraducao(String texto) {
        Client client = Client.builder()
                .apiKey(apiKey)
                .build();

        Instant start = Instant.now();

        GenerateContentResponse response = client.models.generateContent(
                "gemini-3.6-flash",
                "Traduza para português do Brasil sem explicações, saudações ou comentários: " + texto,
                null);

        client.close();

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        long seconds = timeElapsed.toSeconds();
        System.out.println("Execution time: " + seconds + " seconds");

        return response.text() != null ? response.text().trim() : "ERRO??";
    }
}
