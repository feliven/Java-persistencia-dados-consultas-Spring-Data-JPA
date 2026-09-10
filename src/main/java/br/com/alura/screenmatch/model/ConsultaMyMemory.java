package br.com.alura.screenmatch.model;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import tools.jackson.databind.ObjectMapper;

import br.com.alura.screenmatch.service.ConsumoApi;

public class ConsultaMyMemory {

    public static String obterTraducao(String text) {
        ObjectMapper mapper = new ObjectMapper();

        ConsumoApi consumo = new ConsumoApi();

        String texto = URLEncoder.encode(text, StandardCharsets.UTF_8);
        String langpair = URLEncoder.encode("en|pt-br", StandardCharsets.UTF_8);

        String url = "https://api.mymemory.translated.net/get?q=" + texto + "&langpair=" + langpair;

        String json = consumo.obterDados(url);

        DadosRespostaMyMemory resposta = mapper.readValue(json, DadosRespostaMyMemory.class);

        DadosTraducaoMyMemory traducao = resposta.dadosResposta();

        return traducao.textoTraduzido().trim();
    }
}
