package br.com.alura.screenmatch.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaMistral {

    private static ChatModel chatModel;

    @Autowired
    public void setChatModel(ChatModel chatModel) {
        ConsultaMistral.chatModel = chatModel;
    }

    public static String obterTraducao(String texto) {
        Instant start = Instant.now();

        var response = chatModel.call(
                new Prompt("Traduza para português do Brasil sem explicações, saudações ou comentários: " + texto));

        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        long seconds = timeElapsed.toSeconds();
        System.out.println("Execution time: " + seconds + " seconds");

        return response.getResult().getOutput().getText();
    }
}
