package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosTraducaoMyMemory(
        @JsonAlias("translatedText") String textoTraduzido) {
}
