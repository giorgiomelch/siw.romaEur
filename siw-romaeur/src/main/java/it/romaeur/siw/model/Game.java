package it.romaeur.siw.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private int scoreA;
	private int scoreB;
	private String teamNameB;
	private LocalDate date;
	@OneToMany(mappedBy="game")
	private List<Performance>performances;
	
	public int getScoreA() {
		return scoreA;
	}
	public void setScoreA(int scoreA) {
		this.scoreA = scoreA;
	}
	public int getScoreB() {
		return scoreB;
	}
	public void setScoreB(int scoreB) {
		this.scoreB = scoreB;
	}
	public String getTeamNameB() {
		return teamNameB;
	}
	public void setTeamNameB(String teamNameB) {
		this.teamNameB = teamNameB;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public List<Performance> getPerformances() {
		return performances;
	}
	public void setPerformances(List<Performance> performances) {
		this.performances = performances;
	}
	@Override
	public int hashCode() {
		return Objects.hash(date, performances, scoreA, scoreB, teamNameB);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return Objects.equals(date, other.date) && Objects.equals(performances, other.performances)
				&& scoreA == other.scoreA && scoreB == other.scoreB && Objects.equals(teamNameB, other.teamNameB);
	}
	
	
}
