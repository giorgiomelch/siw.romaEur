package it.romaeur.siw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Partita {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@NotNull
	@Min(0)
	private int punteggioA;
	@NotNull
	@Min(0)
	private int punteggioB;
	@NotBlank
	private String nomeSquadraAvversaria;
	@NotNull
	private LocalDate data;
	@OneToMany(mappedBy="partita",
			cascade= {CascadeType.REMOVE})
	private List<Prestazione>prestazioni;
	
	
	
	
	
	public Partita() {
		this.prestazioni = new ArrayList<>();
	}

	public List <Giocatore>getGiocatoriDellaPartita() {
		ArrayList<Giocatore> giocatoriDellaPartita= new ArrayList<Giocatore>();
		for(Prestazione p : this.getPrestazioni())
			giocatoriDellaPartita.add(p.getGiocatore());
		return giocatoriDellaPartita;
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPunteggioA() {
		return punteggioA;
	}
	public void setPunteggioA(int punteggioA) {
		this.punteggioA = punteggioA;
	}
	public int getPunteggioB() {
		return punteggioB;
	}
	public void setPunteggioB(int punteggioB) {
		this.punteggioB = punteggioB;
	}
	public String getNomeSquadraAvversaria() {
		return nomeSquadraAvversaria;
	}
	public void setNomeSquadraAvversaria(String nomeSquadraAvversaria) {
		this.nomeSquadraAvversaria = nomeSquadraAvversaria;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public List<Prestazione> getPrestazioni() {
		return prestazioni;
	}
	public void setPrestazioni(List<Prestazione> prestazioni) {
		this.prestazioni = prestazioni;
	}
	@Override
	public int hashCode() {
		return Objects.hash(data, nomeSquadraAvversaria, punteggioA, punteggioB);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partita other = (Partita) obj;
		return Objects.equals(data, other.data) && Objects.equals(nomeSquadraAvversaria, other.nomeSquadraAvversaria)
				&& punteggioA == other.punteggioA && punteggioB == other.punteggioB;
	}
	
	
	
}
