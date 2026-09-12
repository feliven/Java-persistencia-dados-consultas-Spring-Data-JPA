package br.com.alura.screenmatch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.screenmatch.model.Ator;

public interface AtorRepository extends JpaRepository<Ator, Long> {
    Optional<Ator> findByNomeIgnoreCase(String nome);
}
