package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Atleta;

public interface AtletaRepository extends CrudRepository<Atleta, Long>{
	
	public List<Atleta> findByTeam(String team);
	public boolean existsByTeamAndNumeroDiMaglia(String team,Integer numeroDiMaglia);

}
