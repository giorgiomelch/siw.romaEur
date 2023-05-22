package it.romaeur.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.romaeur.siw.model.Giocatore;
import it.romaeur.siw.model.Partita;
import it.romaeur.siw.model.Prestazione;
import it.romaeur.siw.repository.GiocatoreRepository;
import it.romaeur.siw.repository.PartitaRepository;
import it.romaeur.siw.repository.PrestazioneRepository;

@Controller
public class PrestazioneController {

	@Autowired PrestazioneRepository prestazioneRepository; 
	@Autowired GiocatoreRepository giocatoreRepository;
	@Autowired PartitaRepository partitaRepository;

	@GetMapping("/formNuovaPrestazione/{idPartita}/{idGiocatore}")
	public String formNuovaPrestazione(@PathVariable("idPartita") Long idPartita ,@PathVariable("idGiocatore") Long idGiocatore , Model model) {
		Partita partita = this.partitaRepository.findById(idPartita).get();
		Giocatore giocatore= this.giocatoreRepository.findById(idGiocatore).get();
		Prestazione newPrestazione= new Prestazione();
		model.addAttribute("partita",partita);
		model.addAttribute("giocatore", giocatore);
		model.addAttribute("prestazione",newPrestazione);
		return "formNuovaPrestazione.html";
	}

	@Transactional
	@PostMapping("prestazione/{idPartita}/{idGiocatore}")
	public String newPrestazione(@ModelAttribute("prestazione") Prestazione prestazione ,
			@PathVariable("idPartita") Long idPartita ,@PathVariable("idGiocatore") Long idGiocatore ,  Model model) {
		
		Partita partita = this.partitaRepository.findById(idPartita).get();   
		prestazione.setPartita(partita);
		Giocatore giocatore= this.giocatoreRepository.findById(idGiocatore).get(); 
		prestazione.setGiocatore(giocatore);
		this.prestazioneRepository.save(prestazione); 
		
		model.addAttribute("partita", partita);
		model.addAttribute("giocatoriPresenti", partita.getGiocatoriDellaPartita());
		model.addAttribute("giocatoriAssenti", this.giocatoreRepository.findAll());//Per ora mostro tutti i giocatori (la query findAllExcept non funziona)
		

		return "formUpdatePrestazioni.html";
	} 




}
