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
import it.romaeur.siw.service.GiocatoreService;
import it.romaeur.siw.service.PrestazioneService;
import javax.validation.Valid;

@Controller
public class GiocatoreController {
	
	@Autowired GiocatoreService giocatoreService; 
	@Autowired PrestazioneService prestazioneService;
	@Autowired GiocatoreValidator giocatoreValidator;

	@GetMapping("/roster")
	public String roster(Model model) {
		model.addAttribute("roster", this.giocatoreService.findAll());
		return "roster.html";
	}
	
	@GetMapping("/giocatore/{id}")
	public String getGiocatore(@PathVariable ("id") Long id, Model model) {
		Giocatore giocatore=this.giocatoreService.findById(id);
		if(giocatore==null)
			return "giocatoreError.html";
		model.addAttribute("giocatore", giocatore);
		model.addAttribute("prestazioni",this.prestazioneService.findAllByIdGiocatore(id));
		return "giocatore.html";
	}

	
	@GetMapping("/admin/formNewGiocatore")
	public String formNewGiocatore(Model model) {
		model.addAttribute("giocatore", new Giocatore());
		return "admin/formNewGiocatore.html";
	}
	
	@PostMapping("/admin/giocatore")
	public String newGiocatore(@Valid @ModelAttribute ("giocatore") Giocatore giocatore, BindingResult bindingResult, Model model) {
		this.giocatoreValidator.validate(giocatore, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.giocatoreService.save(giocatore);
			model.addAttribute("giocatore", giocatore);
			return "giocatore.html";
		}
		return "admin/formNewGiocatore.html";
	}
	
	@GetMapping("/admin/formConfirmDeleteGiocatore/{idGiocatore}")
	public String formConfirmDeleteGiocatore(@PathVariable ("idGiocatore") Long idGiocatore, Model model) {
		Giocatore giocatore= this.giocatoreService.findById(idGiocatore);
		if(giocatore==null)
			return "giocatoreError.html";
		model.addAttribute("giocatore",giocatore);
		return "admin/formConfirmDeleteGiocatore.html";
	}
	@GetMapping("/admin/deleteGiocatore/{idGiocatore}")
	public String deleteGiocatore(@PathVariable ("idGiocatore") Long idGiocatore, Model model) {
		Giocatore giocatore= this.giocatoreService.findById(idGiocatore);
		if(giocatore==null)
			return "giocatoreError.html";
		this.giocatoreService.delete(giocatore);
		model.addAttribute("roster",this.giocatoreService.findAll());
		return "roster.html";
	}

}
