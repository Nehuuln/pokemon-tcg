package fr.efrei.pokemon_tcg.controllers;

import fr.efrei.pokemon_tcg.dto.*;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.services.IDresseurService;
import fr.efrei.pokemon_tcg.services.implementations.DresseurServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dresseurs")
public class DresseurController {

	private final IDresseurService dresseurService;

	public DresseurController(DresseurServiceImpl dresseurService) {
		this.dresseurService = dresseurService;
	}

	@GetMapping
	public ResponseEntity<List<Dresseur>> findAll() {
		return new ResponseEntity<>(dresseurService.findAll(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody DresseurDTO dresseurDTO) {
		dresseurService.create(dresseurDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@DeleteMapping("/{uuid}")
	public ResponseEntity<?> delete(@PathVariable String uuid) {
		dresseurService.delete(uuid);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{uuid}/capturer")
	public ResponseEntity<?> capturer(
			@PathVariable String uuid,
			@RequestBody CapturePokemon capturePokemon
	) {
		dresseurService.capturerPokemon(uuid, capturePokemon);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PatchMapping("/{uuid}/tirer")
	public ResponseEntity<String> tirer(@PathVariable String uuid) {
		boolean success = dresseurService.tirerPokemon(uuid);
		return success ? ResponseEntity.ok("Pokémon tiré avec succès") :
				ResponseEntity.badRequest().body("Échec du tirage.");
	}

	@PatchMapping("/{uuid}/echanger")
	public ResponseEntity<String> echangerPokemon(
			@PathVariable String uuid,
			@RequestBody EchangePokemon.EchangeRequest request) {

		boolean success = dresseurService.echangerPokemon(uuid, request);

		if (success) {
			return ResponseEntity.ok("Échange effectué avec succès");
		}
		return ResponseEntity.badRequest().body("Échec de l'échange.");
	}

	@PatchMapping("/{uuid}/changer")
	public ResponseEntity<String> changerPokemonDeck(
			@PathVariable String uuid,
			@RequestBody EchangePokemonDeck request) {

		boolean success = dresseurService.changerPokemonDeck(uuid, request);

		if (success) {
			return ResponseEntity.ok("Échange entre decks effectué avec succès");
		}
		return ResponseEntity.badRequest().body("Échec de l'échange.");
	}

	@PatchMapping("/{uuid}/combat")
	public ResponseEntity<String> combat(
			@PathVariable String uuid,
			@RequestBody CombatPokemon combattre
	) {
		boolean success = dresseurService.combattre(uuid, combattre.getUuidDresseur2()); // Correction ici
		if (success) {
			return ResponseEntity.ok("Combat terminé ! Le gagnant a remporté un Pokémon !");
		}
		return ResponseEntity.badRequest().body("Le combat a échoué.");
	}
}
