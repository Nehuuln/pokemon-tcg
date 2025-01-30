package fr.efrei.pokemon_tcg.services.implementations;

import fr.efrei.pokemon_tcg.dto.CapturePokemon;
import fr.efrei.pokemon_tcg.dto.DresseurDTO;
import fr.efrei.pokemon_tcg.dto.EchangePokemon;
import fr.efrei.pokemon_tcg.models.Dresseur;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.repositories.DresseurRepository;
import fr.efrei.pokemon_tcg.services.IDresseurService;
import fr.efrei.pokemon_tcg.services.IPokemonService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DresseurServiceImpl implements IDresseurService {

	private final DresseurRepository repository;
	private final IPokemonService pokemonService;
	public DresseurServiceImpl(DresseurRepository repository, PokemonServiceImpl pokemonService) {
		this.repository = repository;
		this.pokemonService = pokemonService;
	}

	@Override
	public List<Dresseur> findAll() {
		return repository.findAllByDeletedAtNull();
	}

	@Override
	public Dresseur findById(String uuid) {
		return repository.findById(uuid).orElse(null);
	}

	public void capturerPokemon(String uuid, CapturePokemon capturePokemon) {
		Dresseur dresseur = findById(uuid);
		Pokemon pokemon = pokemonService.findById(capturePokemon.getUuid());
		dresseur.getPokemonList().add(pokemon);
		repository.save(dresseur);
	}

	@Override
	public void create(DresseurDTO dresseurDTO) {
		Dresseur dresseur = new Dresseur();
		dresseur.setNom(dresseurDTO.getNom());
		dresseur.setPrenom(dresseurDTO.getPrenom());
		dresseur.setDeletedAt(null);
		repository.save(dresseur);
	}

	@Override
	public boolean update(String uuid, DresseurDTO dresseurDTO) {
		return false;
	}

	@Override
	public boolean delete(String uuid) {
		Dresseur dresseur = findById(uuid);
		dresseur.setDeletedAt(LocalDateTime.now());
		repository.save(dresseur);
		return true;
	}

	@Override
	public boolean tirerPokemon(String uuid) {
		Dresseur dresseur = findById(uuid);
		if (dresseur == null) {
			throw new RuntimeException("Dresseur introuvable !");
		}

		LocalDate today = LocalDate.now();
		if (dresseur.getHistoriqueTirages().contains(today)) {
			throw new RuntimeException("Le dresseur a déjà tiré aujourd'hui !");
		}

		List<Pokemon> tousLesPokemons = pokemonService.findAll(null);
		List<Pokemon> pokemonsPossedes = dresseur.getPokemonList();
		List<Pokemon> pokemonsDisponibles = tousLesPokemons.stream()
				.filter(p -> !pokemonsPossedes.contains(p))
				.collect(Collectors.toList());  // On copie la liste dans une liste mutable

		if (pokemonsDisponibles.size() < 5) {
			throw new RuntimeException("Pas assez de Pokémon disponibles pour le tirage !");
		}

		List<Pokemon> tirage = new ArrayList<>();
		Collections.shuffle(pokemonsDisponibles, new Random());  // On peut maintenant mélanger la liste
		for (int i = 0; i < 5; i++) {
			tirage.add(pokemonsDisponibles.get(i));
		}

		dresseur.getPokemonList().addAll(tirage);
		dresseur.getHistoriqueTirages().add(today);
		repository.save(dresseur);

		return true;
	}

	@Override
	public boolean echangerPokemon(String uuid, EchangePokemon.EchangeRequest request) {
		Dresseur dresseur1 = findById(uuid);
		Dresseur dresseur2 = findById(request.getUuidDresseur2());

		if (dresseur1 == null || dresseur2 == null) {
			throw new RuntimeException("L'un des dresseurs est introuvable");
		}

		LocalDate today = LocalDate.now();
		if (dresseur1.getHistoriqueEchanges().contains(today) || dresseur2.getHistoriqueEchanges().contains(today)) {
			throw new RuntimeException("Un des dresseurs a déjà échangé aujourd'hui");
		}

		Pokemon pokemon1 = pokemonService.findById(request.getUuidPokemon1());
		Pokemon pokemon2 = pokemonService.findById(request.getUuidPokemon2());

		if (!dresseur1.getPokemonList().contains(pokemon1) || !dresseur2.getPokemonList().contains(pokemon2)) {
			throw new RuntimeException("Un des dresseurs ne possède pas le Pokémon à échanger");
		}

		dresseur1.getPokemonList().remove(pokemon1);
		dresseur2.getPokemonList().remove(pokemon2);
		dresseur1.getPokemonList().add(pokemon2);
		dresseur2.getPokemonList().add(pokemon1);
		dresseur1.getHistoriqueEchanges().add(today);
		dresseur2.getHistoriqueEchanges().add(today);
		repository.save(dresseur1);
		repository.save(dresseur2);

		return true;
	}
}
