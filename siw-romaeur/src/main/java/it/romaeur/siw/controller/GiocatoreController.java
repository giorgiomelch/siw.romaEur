package it.romaeur.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.romaeur.siw.repository.GiocatoreRepository;

@Controller
public class GiocatoreController {
	@Autowired GiocatoreRepository giocatoreRepository; 
	
	@GetMapping("/roster")
	public String roster(Model model) {
		model.addAttribute("roster", this.giocatoreRepository.findAll());
		return "roster.html";
	}

}
