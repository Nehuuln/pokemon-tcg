package fr.efrei.pokemon_tcg.models;

import fr.efrei.pokemon_tcg.constants.TypePokemon;
import jakarta.persistence.*;

@Entity
public class Pokemon {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	private String nom;

	private int puissance;

	private String attaque1;

	private String attaque2;

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

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getPuissance() {
		return puissance;
	}

	public String getAttaque1() {
		return attaque1;
	}

	public String getAttaque2() {
		return attaque2;
	}

	public void setPuissance(int puissance) {
		this.puissance = puissance;
	}

	public void setAttaque1(String attaque1) {
		this.attaque1 = attaque1;
	}

	public void setAttaque2(String attaque2) {
		this.attaque2 = attaque2;
	}
}
