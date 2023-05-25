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
import it.romaeur.siw.service.GiocatoreService;
import it.romaeur.siw.service.PartitaService;
import it.romaeur.siw.service.PrestazioneService;
import jakarta.validation.Valid;

@Controller
public class PrestazioneController {

	@Autowired PrestazioneService prestazioneService; 
	@Autowired GiocatoreService giocatoreService;
	@Autowired PartitaService partitaService;

	@GetMapping("/formNuovaPrestazione/{idPartita}/{idGiocatore}")
	public String formNuovaPrestazione(@PathVariable("idPartita") Long idPartita ,@PathVariable("idGiocatore") Long idGiocatore , Model model) {
		model.addAttribute("partita", this.partitaService.findById(idPartita));
		model.addAttribute("giocatore", this.giocatoreService.findById(idGiocatore));
		model.addAttribute("prestazione",new Prestazione());
		return "formNuovaPrestazione.html";
	}

	@Transactional
	@PostMapping("prestazione/{idPartita}/{idGiocatore}")
	public String newPrestazione(@Valid @ModelAttribute("prestazione") Prestazione prestazione , BindingResult bindingResult, 
			@PathVariable("idPartita") Long idPartita ,@PathVariable("idGiocatore") Long idGiocatore ,  Model model) {
		
		Partita partita = this.partitaService.findById(idPartita);
		Giocatore giocatore= this.giocatoreService.findById(idGiocatore); 
		if(partita.getGiocatoriDellaPartita().contains(giocatore))
			bindingResult.reject("prestazione.duplicate");
		if(!bindingResult.hasErrors()) {
			this.partitaService.addAndSaveNewPrestazione(partita, prestazione);
			this.giocatoreService.addAndSaveNewPrestazione(giocatore, prestazione);
			this.prestazioneService.setAndSavePartitaAndGiocatoreToPrestazione(partita, giocatore, prestazione);
			
			model.addAttribute("partita", partita);
			model.addAttribute("prestazioni", partita.getPrestazioni());
			if(this.partitaService.hasNoPrestazioni(partita))
				model.addAttribute("giocatoriAssenti", this.giocatoreService.findAll());
			else
				model.addAttribute("giocatoriAssenti", this.giocatoreService.findAllExcept(partita.getGiocatoriDellaPartita()));
			
			return "formUpdatePrestazioni.html";
		}
		model.addAttribute("giocatore", this.giocatoreService.findById(idGiocatore));
		model.addAttribute("partita",this.partitaService.findById(idPartita));
		return "formNuovaprestazione.html";
	} 

	@GetMapping("formConfirmDeletePrestazione/{idPrestazione}")
	public String formConfirmDeletePrestazione(@PathVariable ("idPrestazione") Long idPrestazione, Model model) {
		Prestazione prestazione = this.prestazioneService.findById(idPrestazione);
		model.addAttribute("prestazione",prestazione);
		return "formConfirmDeletePrestazione.html";
	}
	@GetMapping("deletePrestazione/{idPrestazione}")
	public String deletePrestazione(@PathVariable ("idPrestazione") Long idPrestazione, Model model) {
		Prestazione prestazione = this.prestazioneService.findById(idPrestazione);
		Partita partita= prestazione.getPartita();
		this.partitaService.removePrestazioneAssociation(prestazione);
		this.giocatoreService.removePrestazioneAssociation(prestazione);
		this.prestazioneService.delete(prestazione);	
		
		model.addAttribute("partita", partita);
		model.addAttribute("prestazioni", partita.getPrestazioni());
		model.addAttribute("giocatoriAssenti", this.giocatoreService.findAll());
		
		return "formUpdatePrestazioni.html";
	}

	


}
