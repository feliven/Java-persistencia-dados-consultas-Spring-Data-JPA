package br.com.alura.screenmatch.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

import br.com.alura.screenmatch.service.LangChain4jRequesty;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @ElementCollection(targetClass = Categoria.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Categoria> generos;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> atores;
    private String poster;
    private String sinopse;
    @Transient
    private List<Episodio> episodios = new ArrayList<>();

    public Serie() {
    }

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
        // this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse());
        this.sinopse = LangChain4jRequesty.obterTraducao(dadosSerie.sinopse());
    }

    public long getId() {
        return id;
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

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    @Override
    public String toString() {
        return "Serie [titulo=" + titulo + ", totalTemporadas=" + totalTemporadas
                + ", avaliacao=" + avaliacao + ", generos=" + generos.toString()
                + ", atores=" + atores.toString() + ", poster=" + poster + ", sinopse=" + sinopse + "]";
    }

}
