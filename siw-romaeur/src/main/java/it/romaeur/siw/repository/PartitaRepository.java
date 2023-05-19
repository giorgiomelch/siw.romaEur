package it.romaeur.siw.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.romaeur.siw.model.Partita;

public interface PartitaRepository extends CrudRepository<Partita,Long> {

	public boolean existsByNomeSquadraAvversariaAndData(String nomeSquadraAvversaria, LocalDate data);
	}
