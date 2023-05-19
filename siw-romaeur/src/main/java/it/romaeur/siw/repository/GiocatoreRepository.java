package it.romaeur.siw.repository;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;

import it.romaeur.siw.model.Giocatore;

public interface GiocatoreRepository extends CrudRepository<Giocatore,Long>{

	public boolean existsByNomeAndCognomeAndDataDiNascita(String nome, String cognome, LocalDate dataDiNascita);
}
