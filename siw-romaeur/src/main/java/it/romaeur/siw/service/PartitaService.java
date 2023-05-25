package it.romaeur.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.romaeur.siw.model.Partita;
import it.romaeur.siw.model.Prestazione;
import it.romaeur.siw.repository.PartitaRepository;

@Service
public class PartitaService {
	 
	@Autowired PartitaRepository partitaRepository;

	public boolean alreadyExists(Partita partita) {
		return partita.getNomeSquadraAvversaria()!=null && partita.getData()!=null && 
				this.partitaRepository.existsByNomeSquadraAvversariaAndData
				(partita.getNomeSquadraAvversaria(), partita.getData());
	}

	public Partita findById(Long idPartita) {
		return this.partitaRepository.findById(idPartita).orElse(null);
	}

	public void addAndSaveNewPrestazione(Partita partita, Prestazione prestazione) {
		partita.getPrestazioni().add(prestazione);
		this.partitaRepository.save(partita);
	}
	
	public boolean hasNoPrestazioni(Partita partita) {
		return partita.getGiocatoriDellaPartita().isEmpty();
	}

	public void removePrestazioneAssociation(Prestazione prestazione) {
		Partita partita= prestazione.getPartita();
		partita.getPrestazioni().remove(prestazione);
		this.partitaRepository.save(partita);
	}

	

}
