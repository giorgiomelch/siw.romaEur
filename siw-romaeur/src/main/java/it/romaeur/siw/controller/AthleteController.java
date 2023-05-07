package it.romaeur.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.romaeur.siw.repository.AthleteRepository;

@Controller
public class AthleteController {
	@Autowired AthleteRepository athleteRepository; 
	
	@GetMapping("/roaster")
	public String roaster(Model model) {
		model.addAttribute("roaster", this.athleteRepository.findAll());
		return "roaster.html";
	}

}
