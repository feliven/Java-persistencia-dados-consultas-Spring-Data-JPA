package br.com.alura.screenmatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByTituloContainingIgnoreCase(String serie);

    List<Serie> findByAtoresNomeContainingIgnoreCase(String nome);

    List<Serie> findByAtoresNomeContainingIgnoreCaseAndAvaliacaoGreaterThan(String nome, double avaliacao);

    List<Serie> findFirst5ByOrderByAvaliacaoDesc();

    List<Serie> findAllByOrderByAvaliacaoDesc();
}
