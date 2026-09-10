package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosRespostaMyMemory(
        @JsonAlias("responseData") DadosTraducaoMyMemory dadosResposta) {
}
