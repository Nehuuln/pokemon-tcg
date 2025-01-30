package fr.efrei.pokemon_tcg.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dresseur {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	private String nom;

	private String prenom;

	private LocalDateTime deletedAt;

	@ElementCollection
	private List<LocalDate> historiqueTirages = new ArrayList<>();

	@ElementCollection
	private List<LocalDate> historiqueEchanges = new ArrayList<>();

	@ManyToMany
	private List<Pokemon> pokemonList = new ArrayList<>();

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public List<Pokemon> getPokemonList() {
		return pokemonList;
	}

	public void setPokemonList(List<Pokemon> pokemonList) {
		this.pokemonList = pokemonList;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}

	public List<LocalDate> getHistoriqueTirages() {
		return historiqueTirages;
	}

	public void setHistoriqueTirages(List<LocalDate> historiqueTirages) {
		this.historiqueTirages = historiqueTirages;
	}

	public List<LocalDate> getHistoriqueEchanges() {
		return historiqueEchanges;
	}

	public void setHistoriqueEchanges(List<LocalDate> historiqueEchanges) {}
}
