package fr.efrei.pokemon_tcg.dto;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public class CreatePokemon {

	@Length(min = 3, max = 20)
	private String nom;

	@Positive
	private Integer etoile;

	private TypePokemon type;

	public String getNom() {
		return nom;
	}

	public Integer getEtoile() {
		return etoile;
	}

	public TypePokemon getType() {
		return type;
	}
}
