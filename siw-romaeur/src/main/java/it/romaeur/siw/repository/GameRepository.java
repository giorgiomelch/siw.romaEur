package it.romaeur.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.romaeur.siw.model.Game;

public interface GameRepository extends CrudRepository<Game,Long> {

}
