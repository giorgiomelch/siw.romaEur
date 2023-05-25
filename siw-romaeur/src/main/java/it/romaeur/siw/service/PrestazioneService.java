package it.romaeur.siw.service;

import it.romaeur.siw.repository.PrestazioneRepository;
import it.romaeur.siw.model.Giocatore;
import it.romaeur.siw.model.Partita;
import it.romaeur.siw.model.Prestazione;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestazioneService {

	@Autowired PrestazioneRepository prestazioneRepository;

	public List<Prestazione> findAllByIdGiocatore(Long id) {
		return this.prestazioneRepository.findAllByIdGiocatore(id);
	}
	public void save(Prestazione prestazione) {
		this.prestazioneRepository.save(prestazione);
	}
	public Prestazione findById(Long idPrestazione) {
		return this.prestazioneRepository.findById(idPrestazione).orElse(null);
	}
	public void setAndSavePartitaAndGiocatoreToPrestazione(Partita partita, Giocatore giocatore,Prestazione prestazione) {
		prestazione.setPartita(partita);
		prestazione.setGiocatore(giocatore);
		this.prestazioneRepository.save(prestazione);
	}
	public void delete(Prestazione prestazione) {
		this.prestazioneRepository.delete(prestazione);
		
	}
	
	
}
