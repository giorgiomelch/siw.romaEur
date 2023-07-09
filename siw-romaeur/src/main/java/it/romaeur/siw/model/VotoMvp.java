package it.romaeur.siw.model;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class VotoMvp {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	
	@ManyToOne
	private Partita partitaVotata;
	
	
	@ManyToOne
	private User utente;
	
	@ManyToOne
	private Giocatore giocatoreVotato;

}
