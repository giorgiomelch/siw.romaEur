package it.romaeur.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.romaeur.siw.model.Giocatore;
import it.romaeur.siw.repository.GiocatoreRepository;

@Controller
public class GiocatoreController {
	@Autowired GiocatoreRepository giocatoreRepository; 

	@GetMapping("/roster")
	public String roster(Model model) {
		model.addAttribute("roster", this.giocatoreRepository.findAll());
		return "roster.html";
	}
	
	@GetMapping("/giocatore/{id}")
	public String getGiocatore(@PathVariable ("id") Long id, Model model) {
		model.addAttribute("giocatore", this.giocatoreRepository.findById(id).get());
		return "giocatore.html";
	}
	
	@GetMapping("/formNewGiocatore")
	public String formNewGiocatore(Model model) {
		model.addAttribute("giocatore", new Giocatore());
		return "formNewGiocatore";
	}
	
	@PostMapping("/giocatore")
	public String newGiocatore(@ModelAttribute ("giocatore") Giocatore giocatore, Model model) {
		model.addAttribute("giocatore", giocatore);
		this.giocatoreRepository.save(giocatore);
		return "giocatore.html";
	}

}
