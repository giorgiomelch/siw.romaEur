package it.romaeur.siw.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Giocatore {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	@NotBlank
	private String cognome;
	@NotNull
	private LocalDate dataDiNascita;
	@NotNull
	private String ruolo; 	
	@NotNull
	@Min(0)
	@Max(99)
	private int numeroMaglia;
	private String srcImage;
	@OneToMany(mappedBy= "giocatore")
	private List<Prestazione> prestazione;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public int getNumeroMaglia() {
		return numeroMaglia;
	}
	public void setNumeroMaglia(int numeroMaglia) {
		this.numeroMaglia = numeroMaglia;
	}
	public String getSrcImage() {
		return srcImage;
	}
	public void setSrcImage(String srcImage) {
		this.srcImage = srcImage;
	}
	public List<Prestazione> getPrestazione() {
		return prestazione;
	}
	public void setPrestazione(List<Prestazione> prestazione) {
		this.prestazione = prestazione;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cognome, dataDiNascita, nome);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Giocatore other = (Giocatore) obj;
		return Objects.equals(cognome, other.cognome) && Objects.equals(dataDiNascita, other.dataDiNascita)
				&& Objects.equals(nome, other.nome);
	}
	
	
	
	
}
