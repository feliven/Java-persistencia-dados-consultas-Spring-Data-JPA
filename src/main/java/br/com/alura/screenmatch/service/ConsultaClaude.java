package br.com.alura.screenmatch.service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;

@Service
public class ConsultaClaude {

    private static String apiKey;

    @Value("${anthropic.api-key}")
    public void setApiKey(String apiKey) {
        ConsultaClaude.apiKey = apiKey;
    }

    public static String obterTraducao(String texto) {

        AnthropicClient client = AnthropicOkHttpClient.builder()
                .apiKey(apiKey)
                .build();

        MessageCreateParams params = MessageCreateParams.builder()
                .maxTokens(1024L)
                .addUserMessage("Traduza para português do Brasil sem explicações, saudações ou comentários: " + texto)
                .model(Model.CLAUDE_HAIKU_4_5)
                .build();

        Instant start = Instant.now();

        Message message = client.messages().create(params);

        List<String> respostas = new ArrayList<>();

        for (var block : message.content()) {
            block.text().ifPresent(textBlock -> {
                System.out.println(textBlock.text());
                respostas.add(textBlock.text());
            });
        }

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        long seconds = timeElapsed.toSeconds();
        System.out.println("Execution time: " + seconds + " seconds");

        return respostas.toString();
    }
}
