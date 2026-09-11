package br.com.alura.screenmatch.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import dev.langchain4j.model.openai.OpenAiChatModel;

@Service
public class LangChain4jRequesty {

    private static String apiKey;

    @Value("${requesty-key}")
    public void setApiKey(String apiKey) {
        LangChain4jRequesty.apiKey = apiKey;
    }

    public static String obterTraducao(String texto) {

        try {
            OpenAiChatModel model = OpenAiChatModel.builder()
                    .baseUrl("https://router.requesty.ai/v1")
                    .apiKey(apiKey)
                    .modelName("policy/java-alura")
                    .build();

            Instant start = Instant.now();

            String response = model
                    .chat("Traduza para português do Brasil sem explicações, saudações ou comentários: " + texto);

            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);
            long seconds = timeElapsed.toSeconds();
            System.out.println("Execution time: " + seconds + " seconds");

            return response;
        } catch (Exception e) {
            return texto;
        }

    }
}
