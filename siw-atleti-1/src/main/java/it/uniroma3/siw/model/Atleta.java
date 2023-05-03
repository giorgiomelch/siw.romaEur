package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Atleta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nome;
	private String team;
	private Integer numeroDiMaglia;
	
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
	public String getTeam() {
		return team;
	}
	public void setTeam(String squadra) {
		this.team = squadra;
	}
	public Integer getNumeroDiMaglia() {
		return numeroDiMaglia;
	}
	public void setNumeroDiMaglia(Integer numeroDiMaglia) {
		this.numeroDiMaglia = numeroDiMaglia;
	}
	@Override
	public int hashCode() {
		return Objects.hash(nome, numeroDiMaglia, team);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Atleta other = (Atleta) obj;
		return Objects.equals(nome, other.nome) && Objects.equals(numeroDiMaglia, other.numeroDiMaglia)
				&& Objects.equals(team, other.team);
	}
	
	
	
	

}
