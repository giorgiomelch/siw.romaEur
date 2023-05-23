package it.romaeur.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.romaeur.siw.controller.validator.GiocatoreValidator;
import it.romaeur.siw.model.Giocatore;
import it.romaeur.siw.repository.GiocatoreRepository;
import it.romaeur.siw.repository.PrestazioneRepository;
import jakarta.validation.Valid;

@Controller
public class GiocatoreController {
	
	@Autowired GiocatoreRepository giocatoreRepository; 
	@Autowired PrestazioneRepository prestazioneRepository;
	@Autowired GiocatoreValidator giocatoreValidator;

	@GetMapping("/roster")
	public String roster(Model model) {
		model.addAttribute("roster", this.giocatoreRepository.findAll());
		return "roster.html";
	}
	
	@GetMapping("/giocatore/{id}")
	public String getGiocatore(@PathVariable ("id") Long id, Model model) {
		model.addAttribute("giocatore", this.giocatoreRepository.findById(id).get());
		model.addAttribute("prestazioni",this.prestazioneRepository.findByIdGiocatore(id));
		return "giocatore.html";
	}
	
	@GetMapping("/formNewGiocatore")
	public String formNewGiocatore(Model model) {
		model.addAttribute("giocatore", new Giocatore());
		return "formNewGiocatore.html";
	}
	
	@PostMapping("/giocatore")
	public String newGiocatore(@Valid @ModelAttribute ("giocatore") Giocatore giocatore, BindingResult bindingResult, Model model) {
		this.giocatoreValidator.validate(giocatore, bindingResult);
		if(!bindingResult.hasErrors()) {
			model.addAttribute("giocatore", giocatore);
			this.giocatoreRepository.save(giocatore);
			return "giocatore.html";
		}
		return "formNewGiocatore.html";
	}

}
