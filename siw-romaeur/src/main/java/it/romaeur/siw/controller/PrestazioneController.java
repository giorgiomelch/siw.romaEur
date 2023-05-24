package it.romaeur.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
import jakarta.validation.Valid;

@Controller
public class PrestazioneController {

	@Autowired PrestazioneRepository prestazioneRepository; 
	@Autowired GiocatoreRepository giocatoreRepository;
	@Autowired PartitaRepository partitaRepository;

	@GetMapping("/formNuovaPrestazione/{idPartita}/{idGiocatore}")
	public String formNuovaPrestazione(@PathVariable("idPartita") Long idPartita ,@PathVariable("idGiocatore") Long idGiocatore , Model model) {
		Partita partita = this.partitaRepository.findById(idPartita).get();
		Giocatore giocatore= this.giocatoreRepository.findById(idGiocatore).get(); 
		model.addAttribute("partita",partita);
		model.addAttribute("giocatore", giocatore);
		model.addAttribute("prestazione",new Prestazione());
		return "formNuovaPrestazione.html";
	}

	@Transactional
	@PostMapping("prestazione/{idPartita}/{idGiocatore}")
	public String newPrestazione(@Valid @ModelAttribute("prestazione") Prestazione prestazione , BindingResult bindingResult, 
			@PathVariable("idPartita") Long idPartita ,@PathVariable("idGiocatore") Long idGiocatore ,  Model model) {
		if(!bindingResult.hasErrors()) {
			Partita partita = this.partitaRepository.findById(idPartita).get();
			partita.getPrestazioni().add(prestazione);
			prestazione.setPartita(partita);
			Giocatore giocatore= this.giocatoreRepository.findById(idGiocatore).get(); 
			giocatore.getPrestazioni().add(prestazione);
			prestazione.setGiocatore(giocatore);
			this.prestazioneRepository.save(prestazione); 
			
			model.addAttribute("partita", partita);
			model.addAttribute("prestazioni", partita.getPrestazioni());
			if(partita.getGiocatoriDellaPartita().isEmpty())
				model.addAttribute("giocatoriAssenti", this.giocatoreRepository.findAll());
			else
				model.addAttribute("giocatoriAssenti", this.giocatoreRepository.findAllExcept(partita.getGiocatoriDellaPartita()));
			
			return "formUpdatePrestazioni.html";
		}
		model.addAttribute("giocatore", this.giocatoreRepository.findById(idGiocatore).get());
		model.addAttribute("partita",this.partitaRepository.findById(idPartita).get());
		return "formNuovaprestazione.html";
	} 

	@GetMapping("formConfirmDeletePrestazione/{idPrestazione}")
	public String formConfirmDeletePrestazione(@PathVariable ("idPrestazione") Long idPrestazione, Model model) {
		Prestazione prestazione = this.prestazioneRepository.findById(idPrestazione).get();
		model.addAttribute("prestazione",prestazione);
		return "formConfirmDeletePrestazione.html";
	}
	@GetMapping("deletePrestazione/{idPrestazione}")
	public String deletePrestazione(@PathVariable ("idPrestazione") Long idPrestazione, Model model) {
		Prestazione prestazione = this.prestazioneRepository.findById(idPrestazione).get();
		Partita partita= prestazione.getPartita();
		partita.getPrestazioni().remove(prestazione);
		this.partitaRepository.save(partita);
		
		Giocatore giocatore= prestazione.getGiocatore();
		giocatore.getPrestazioni().remove(prestazione);
		this.giocatoreRepository.save(giocatore);
		
		this.prestazioneRepository.delete(prestazione);	
		
		model.addAttribute("partita", partita);
		model.addAttribute("prestazioni", partita.getPrestazioni());
		model.addAttribute("giocatoriAssenti", this.giocatoreRepository.findAll());
		
		return "formUpdatePrestazioni.html";
	}

	


}
