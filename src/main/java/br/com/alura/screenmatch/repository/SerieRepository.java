package br.com.alura.screenmatch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    List<Serie> findByTituloContainingIgnoreCase(String serie);

    List<Serie> findByAtoresNomeContainingIgnoreCase(String nome);

    List<Serie> findByAtoresNomeContainingIgnoreCaseAndAvaliacaoGreaterThan(String nome, double avaliacao);

    List<Serie> findFirst5ByOrderByAvaliacaoDesc();

    List<Serie> findAllByOrderByAvaliacaoDesc();

    List<Serie> findByGeneros(Categoria categoria);

    List<Serie> findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanOrderByAvaliacaoDesc(
            int totalTemporadas, double avaliacao);

    @Query(value = "SELECT * FROM series WHERE series.total_temporadas <= 5 AND series.avaliacao >= 7.5", nativeQuery = true)
    List<Serie> listarSeriesPorTemporadaEAvaliacao();

}
