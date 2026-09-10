package br.com.alura.screenmatch.model;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

import br.com.alura.screenmatch.service.ConsultaGemini;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private List<Categoria> generos;
    private List<String> atores;
    private String poster;
    private String sinopse;

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble
                .of(
                        Double.valueOf(dadosSerie.avaliacao()))
                .orElse(0);
        this.generos = Categoria.fromStringArray(dadosSerie.genero().split(","));
        this.atores = Arrays.asList(
                dadosSerie.atores().split(", "));
        this.poster = dadosSerie.poster();
        this.sinopse = ConsultaGemini.obterTraducao(dadosSerie.sinopse());
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public List<Categoria> getGeneros() {
        return generos;
    }

    public List<String> getAtores() {
        return atores;
    }

    public String getPoster() {
        return poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    @Override
    public String toString() {
        return "Serie [titulo=" + titulo + ", totalTemporadas=" + totalTemporadas
                + ", avaliacao=" + avaliacao + ", generos=" + generos.toString()
                + ", atores=" + atores.toString() + ", poster=" + poster + ", sinopse=" + sinopse + "]";
    }

}
