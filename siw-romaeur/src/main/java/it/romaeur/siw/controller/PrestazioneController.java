package it.romaeur.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.romaeur.siw.model.Partita;
import it.romaeur.siw.model.Prestazione;
import it.romaeur.siw.repository.PrestazioneRepository;

@Controller
public class PrestazioneController {
	
	@Autowired PrestazioneRepository prestazioneRepository; 
	
	@GetMapping("/formNuovaPrestazione")
	public String formNuovaPrestazione(Model model) {
		model.addAttribute("prestazione", new Prestazione());
		return "formNuovaPrestazione.html";
	}
	
	@PostMapping("/prestazione")
	public String newPrestazione(@ModelAttribute("prestazione") Prestazione prestazione, Model model) {
		
			this.prestazioneRepository.save(prestazione); 
			model.addAttribute("prestazione", prestazione);
			return "prestazione.html";
		} 

}
