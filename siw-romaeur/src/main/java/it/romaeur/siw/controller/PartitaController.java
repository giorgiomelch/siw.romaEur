package it.romaeur.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.romaeur.siw.controller.validator.PartitaValidator;
import it.romaeur.siw.model.Partita;
import it.romaeur.siw.model.VotoMvp;
import it.romaeur.siw.service.GiocatoreService;
import it.romaeur.siw.service.PartitaService;
import it.romaeur.siw.service.PrestazioneService;
import jakarta.validation.Valid;

@Controller
public class PartitaController {
	
	@Autowired PartitaValidator partitaValidator;
	@Autowired GiocatoreService giocatoreService;
	@Autowired PartitaService partitaService;
	@Autowired PrestazioneService prestazioneService; 
	
	
	
	@GetMapping("/calendario")
	public String calendario(Model model) {
		model.addAttribute("calendario", this.partitaService.findAll());
		return "calendario.html";
	}
	@GetMapping(value="/admin/formNuovaPartita")
	public String formNuovaPartita(Model model) {
		model.addAttribute("partita", new Partita());
		return "/admin/formNuovaPartita.html";
	}
	
	@PostMapping("/admin/partita")
	public String newPartita(@Valid @ModelAttribute("partita") Partita partita,BindingResult bindingResult,
			MultipartFile image, Model model) {
		this.partitaValidator.validate(partita, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.partitaService.createNewPartita(partita, image); 
			model.addAttribute("partita", partita);
			return "partita.html";
		}
		return "admin/formNuovaPartita.html";
	} 
	
	@GetMapping("/partita/{id}")
	public String getPartita(@PathVariable("id") Long id, Model model) {
		Partita partita = this.partitaService.findById(id);
		if(partita==null)
			return "partitaError.html";
		model.addAttribute("partita", partita);
		model.addAttribute("prestazioni",partita.getPrestazioni());
		return "partita.html";
	}
	
	

	@GetMapping("/admin/formUpdatePartita/{id}")
	public String formUpdatePartita(@PathVariable("id") Long id, Model model) {
		Partita partita=  this.partitaService.findById(id);
		if(partita==null)
			return "partitaError.html";
		model.addAttribute("partita", partita);
		model.addAttribute("prestazioni", partita.getPrestazioni());
		if(this.partitaService.hasNoPrestazioni(partita))
			model.addAttribute("giocatoriAssenti", this.giocatoreService.findAll());
		else
			model.addAttribute("giocatoriAssenti", this.giocatoreService.findAllExcept(partita.getGiocatoriDellaPartita()));
		return "/admin/formUpdatePrestazioni.html";
	}
	
	@GetMapping("/admin/formConfirmDeletePartita/{idPartita}")
	public String formConfirmDeletePartita(@PathVariable ("idPartita") Long idPartita, Model model) {
		Partita partita=  this.partitaService.findById(idPartita);
		if(partita==null)
			return "partitaError.html";
		model.addAttribute("partita", partita);
		return "admin/formConfirmDeletePartita.html";
	}
	@GetMapping("/admin/deletePartita/{idPartita}")
	public String deletePartita(@PathVariable ("idPartita") Long idPartita, Model model) {
		Partita partita=  this.partitaService.findById(idPartita);
		if(partita==null)
			return "partitaError.html";
		
		this.partitaService.delete(partita);
		
		model.addAttribute("calendario", this.partitaService.findAll());
		return "calendario.html";
	}
	
	@GetMapping("/mvp/{idPartita}")
	public String mvp(@PathVariable ("idPartita") Long idPartita, Model model) {
		model.addAttribute("giocatori",this.giocatoreService.findAll());	
		Partita partita = this.partitaService.findById(idPartita);
		model.addAttribute("partita",partita );
		return "mvp.html";
	}
	
	@PostMapping("/votoMvp/{idPartita}")
	public String newVotoMvp( @RequestParam Long voto, Model model) {
		model.addAttribute("giocatori",this.giocatoreService.findAll());
		model.addAttribute("voto",voto);

		
		return "index.html";
	} 
}
