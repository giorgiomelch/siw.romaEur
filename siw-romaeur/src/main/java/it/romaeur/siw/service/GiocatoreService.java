package it.romaeur.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.romaeur.siw.model.Giocatore;
import it.romaeur.siw.model.Prestazione;
import it.romaeur.siw.repository.GiocatoreRepository;

@Service
public class GiocatoreService {

	@Autowired private GiocatoreRepository giocatoreRepository;
	
	public Iterable<Giocatore> findAll(){
		return this.giocatoreRepository.findAll();
	}
	public void save(Giocatore giocatore) {
		this.giocatoreRepository.save(giocatore);
	}
	public Giocatore findById(Long id) {
		return this.giocatoreRepository.findById(id).orElse(null);
	}
	public boolean alreadyExists(Giocatore giocatore) {
		return giocatore.getNome()!=null && giocatore.getCognome()!=null && giocatore.getDataDiNascita()!=null
				&& this.giocatoreRepository.existsByNomeAndCognomeAndDataDiNascita
				(giocatore.getNome(), giocatore.getCognome(),giocatore.getDataDiNascita());
	}
	public List<Giocatore> findAllExcept(List<Giocatore> giocatoriDellaPartita) {
		return this.giocatoreRepository.findAllExcept(giocatoriDellaPartita);
	}
	public void addNewPrestazione(Giocatore giocatore, Prestazione prestazione) {
		giocatore.getPrestazioni().add(prestazione);
		
	}
	public void removePrestazioneAssociation(Prestazione prestazione) {
		Giocatore giocatore= prestazione.getGiocatore();
		giocatore.getPrestazioni().remove(prestazione);
		this.giocatoreRepository.save(giocatore);
	}
}
