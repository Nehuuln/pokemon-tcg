package fr.efrei.pokemon_tcg.models;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import jakarta.persistence.*;

@Entity
public class Pokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	private String nom;

	private Integer etoile;

	@Enumerated(EnumType.STRING)
	private TypePokemon type;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Integer getEtoile() {
		return etoile;
	}

	public void setEtoile(Integer etoile) {
		this.etoile = etoile;
	}

	public TypePokemon getType() {
		return type;
	}

	public void setType(TypePokemon type) {
		this.type = type;
	}

	public String getUuid() {
		return uuid;
	}
}
