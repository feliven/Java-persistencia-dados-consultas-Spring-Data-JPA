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

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= 5 AND s.avaliacao >= 7.5")
    List<Serie> listarSeriesPorTemporadaEAvaliacao();

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao")
    List<Serie> filtrarSeriesPorTemporadaEAvaliacao(int totalTemporadas, double avaliacao);

}
