package fr.efrei.pokemon_tcg.services;

import fr.efrei.pokemon_tcg.dto.CapturePokemon;
import fr.efrei.pokemon_tcg.dto.DresseurDTO;
import fr.efrei.pokemon_tcg.dto.EchangePokemon;
import fr.efrei.pokemon_tcg.dto.EchangePokemonDeck;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;

import java.util.List;

public interface IDresseurService {

	List<Dresseur> findAll();
	Dresseur findById(String uuid);
	void create(DresseurDTO dresseurDTO);

	boolean update(String uuid, DresseurDTO dresseurDTO);
	boolean delete(String uuid);

	void capturerPokemon(String uuid, CapturePokemon capturePokemon);

	boolean tirerPokemon(String uuid);

	boolean echangerPokemon(String uuid, EchangePokemon.EchangeRequest request);

	boolean changerPokemonDeck(String uuid, EchangePokemonDeck request);

}
