package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Atleta;
import it.uniroma3.siw.repository.AtletaRepository;


@Controller
public class AtletaController {
	@Autowired AtletaRepository atletaRepository;
	
	@GetMapping("/formNewAtleta")
	public String formNewAtleta(Model model) {
		model.addAttribute("atleta",new Atleta());
		return "formNewAtleta.html";
		
	}
	
	@PostMapping("/atleti")
	public String newAtleta(@ModelAttribute("atleta")Atleta atleta,Model model) {
		if(!atletaRepository.existsByTeamAndNumeroDiMaglia(atleta.getTeam(),atleta.getNumeroDiMaglia())) {
			this.atletaRepository.save(atleta);
		
		model.addAttribute("atleta",atleta);
		return "atleta.html";
		}
		else {
			model.addAttribute("messaggioErrore","Questo atleta esiste già");
			return "formNewAtleta.html";
		}
		
	}
	
	@GetMapping("/atleti/{id}")
	public String getAtleta(@PathVariable("id") Long id,Model model) {
		model.addAttribute("atleta",this.atletaRepository.findById(id).get());
	return "atleta.html";
	}
	
	@GetMapping("/atleti")
	public String showAtleti(Model model) {
		model.addAttribute("atleti",this.atletaRepository.findAll());
		return "atleti.html";
	}
	
	@GetMapping("/formSearchAtleti")
	public String formSearchAtleti() {
		return "formSearchAtleti.html";
	}
	@PostMapping("/searchAtleti")
	public String searchAtleti(Model model,@RequestParam String team) {
		model.addAttribute("atleti",this.atletaRepository.findByTeam(team));
		return "foundAtleti.html";
	}
	


}
