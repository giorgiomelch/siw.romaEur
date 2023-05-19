package it.romaeur.siw.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.romaeur.siw.model.Partita;
import it.romaeur.siw.model.Prestazione;
import it.romaeur.siw.repository.PartitaRepository;
import it.romaeur.siw.repository.PrestazioneRepository;

@Controller
public class PrestazioneController {

	@Autowired PrestazioneRepository prestazioneRepository; 

	@Autowired PartitaRepository partitaRepository;

	@GetMapping("/formNuovaPrestazione/{partitaId}")
	public String formNuovaPrestazione(@PathVariable("partitaId") Long partitaId, Model model) {
		model.addAttribute("prestazione", new Prestazione());
		return "formNuovaPrestazione.html";
	}

	@Transactional
	@PostMapping("/prestazione/{idPartita}")
	public String newPrestazione(@ModelAttribute("prestazione") Prestazione prestazione,@PathVariable("idPartita") Long idPartita, Model model) {
		Partita partita = this.partitaRepository.findById(idPartita).get();    
		prestazione.setPartita(partita);
		model.addAttribute("prestazione", prestazione);

		if(prestazione != null && partita != null) {
			partita.getPrestazioni().add(prestazione);
			this.partitaRepository.save(partita);
			model.addAttribute("prestazioni",partita.getPrestazioni());

		}
		this.prestazioneRepository.save(prestazione); 


		return "prestazione.html";
	} 




}
