package it.romaeur.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.romaeur.siw.controller.validator.PartitaValidator;
import it.romaeur.siw.model.Partita;
import it.romaeur.siw.repository.GiocatoreRepository;
import it.romaeur.siw.repository.PartitaRepository;
import jakarta.validation.Valid;

@Controller
public class PartitaController {
	
	@Autowired PartitaRepository partitaRepository; 
	@Autowired PartitaValidator partitaValidator;
	@Autowired GiocatoreRepository giocatoreRepository;
	
	@GetMapping("/calendario")
	public String calendario(Model model) {
		model.addAttribute("calendario", this.partitaRepository.findAll());
		return "calendario.html";
	}
	
	@GetMapping(value="/formNuovaPartita")
	public String formNuovaPartita(Model model) {
		model.addAttribute("partita", new Partita());
		return "formNuovaPartita.html";
	}
	
	@PostMapping("/partita")
	public String newPartita(@Valid @ModelAttribute("partita") Partita partita,BindingResult bindingResult, Model model) {
		this.partitaValidator.validate(partita, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.partitaRepository.save(partita); 
			model.addAttribute("partita", partita);
			return "partita.html";
		}
		return "formNuovaPartita.html";
	} 
	
	@GetMapping("/partita/{id}")
	public String getPartita(@PathVariable("id") Long id, Model model) {
		Partita partita = this.partitaRepository.findById(id).get();
		model.addAttribute("partita", partita);
		model.addAttribute("prestazioni",partita.getPrestazioni());
		return "partita.html";
	}
	
	

	@GetMapping("/formUpdatePartita/{id}")
	public String formUpdatePartita(@PathVariable("id") Long id, Model model) {
		Partita partita=  this.partitaRepository.findById(id).get();
		model.addAttribute("partita", partita);
		model.addAttribute("prestazioni", partita.getPrestazioni());
		if(partita.getGiocatoriDellaPartita().isEmpty())
			model.addAttribute("giocatoriAssenti", this.giocatoreRepository.findAll());
		else
			model.addAttribute("giocatoriAssenti", this.giocatoreRepository.findAllExcept(partita.getGiocatoriDellaPartita()));
		return "formUpdatePrestazioni.html";
	}

}
