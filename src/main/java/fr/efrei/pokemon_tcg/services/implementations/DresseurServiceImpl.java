package fr.efrei.pokemon_tcg.services.implementations;

import fr.efrei.pokemon_tcg.dto.CapturePokemon;
import fr.efrei.pokemon_tcg.dto.DresseurDTO;
import fr.efrei.pokemon_tcg.dto.EchangePokemon;
import fr.efrei.pokemon_tcg.dto.EchangePokemonDeck;
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
		dresseur.getPaquetSecondaire().add(pokemon);
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
			throw new RuntimeException("Dresseur introuvable");
		}

		LocalDate today = LocalDate.now();
		if (dresseur.getHistoriqueTirages().contains(today)) {
			throw new RuntimeException("Le dresseur a déjà tiré des Pokémon aujourd'hui");
		}

		List<Pokemon> tousLesPokemons = pokemonService.findAll(null);
		List<Pokemon> pokemonsPossedes = new ArrayList<>();
		pokemonsPossedes.addAll(dresseur.getPaquetPrincipal());
		pokemonsPossedes.addAll(dresseur.getPaquetSecondaire());

		List<Pokemon> pokemonsDisponibles = tousLesPokemons.stream()
				.filter(p -> !pokemonsPossedes.contains(p))
				.collect(Collectors.toList());

		if (pokemonsDisponibles.size() < 5) {
			return false;
		}

		Collections.shuffle(pokemonsDisponibles);
		List<Pokemon> tirage = pokemonsDisponibles.subList(0, 5);

		for (Pokemon p : tirage) {
			if (dresseur.getPaquetPrincipal().size() < 5) {
				dresseur.getPaquetPrincipal().add(p);
			} else {
				dresseur.getPaquetSecondaire().add(p);
			}
		}

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

		Pokemon pokemon1 = dresseur1.getPaquetSecondaire().stream()
				.filter(p -> p.getUuid().equals(request.getUuidPokemon1()))
				.findFirst()
				.orElse(null);

		Pokemon pokemon2 = dresseur2.getPaquetSecondaire().stream()
				.filter(p -> p.getUuid().equals(request.getUuidPokemon2()))
				.findFirst()
				.orElse(null);

		if (pokemon1 == null || pokemon2 == null) {
			throw new RuntimeException("Un des Pokémon n'est pas dans le paquet secondaire");
		}

		dresseur1.getPaquetSecondaire().remove(pokemon1);
		dresseur2.getPaquetSecondaire().remove(pokemon2);

		dresseur1.getPaquetSecondaire().add(pokemon2);
		dresseur2.getPaquetSecondaire().add(pokemon1);

		dresseur1.getHistoriqueEchanges().add(today);
		dresseur2.getHistoriqueEchanges().add(today);

		repository.save(dresseur1);
		repository.save(dresseur2);

		return true;
	}

	@Override
	public boolean changerPokemonDeck(String uuid, EchangePokemonDeck request) {
		Dresseur dresseur = findById(uuid);
		if (dresseur == null) {
			throw new RuntimeException("Dresseur introuvable");
		}

		Pokemon pokemonPrincipal = dresseur.getPaquetPrincipal().stream()
				.filter(p -> p.getUuid().equals(request.getUuidPokemonPrincipal()))
				.findFirst()
				.orElse(null);

		Pokemon pokemonSecondaire = dresseur.getPaquetSecondaire().stream()
				.filter(p -> p.getUuid().equals(request.getUuidPokemonSecondaire()))
				.findFirst()
				.orElse(null);

		if (pokemonPrincipal == null || pokemonSecondaire == null) {
			throw new RuntimeException("Un des Pokémon n'est pas dans le bon deck");
		}

		dresseur.getPaquetPrincipal().remove(pokemonPrincipal);
		dresseur.getPaquetSecondaire().remove(pokemonSecondaire);

		dresseur.getPaquetPrincipal().add(pokemonSecondaire);
		dresseur.getPaquetSecondaire().add(pokemonPrincipal);

		repository.save(dresseur);
		return true;
	}

	@Override
	public boolean combattre(String uuidDresseur1, String uuidDresseur2) {
		Dresseur dresseur1 = findById(uuidDresseur1);
		Dresseur dresseur2 = findById(uuidDresseur2);

		if (dresseur1 == null || dresseur2 == null) {
			throw new RuntimeException("Un des dresseurs est introuvable !");
		}

		double puissanceDresseur1 = calculerPuissanceMoyenne(dresseur1.getPaquetPrincipal());
		double puissanceDresseur2 = calculerPuissanceMoyenne(dresseur2.getPaquetPrincipal());

		Dresseur gagnant = puissanceDresseur1 > puissanceDresseur2 ? dresseur1 : dresseur2;
		Dresseur perdant = puissanceDresseur1 > puissanceDresseur2 ? dresseur2 : dresseur1;

		Pokemon pokemonGagnant = obtenirPokemonLePlusFort(perdant.getPaquetPrincipal());

		gagnant.getPaquetSecondaire().add(pokemonGagnant);

		repository.save(gagnant);
		repository.save(perdant);

		return true;
	}

	private double calculerPuissanceMoyenne(List<Pokemon> paquet) {
		return paquet.stream()
				.mapToInt(Pokemon::getPuissance)
				.average()
				.orElse(0);
	}

	private Pokemon obtenirPokemonLePlusFort(List<Pokemon> paquet) {
		return paquet.stream()
				.max((p1, p2) -> Integer.compare(p1.getPuissance(), p2.getPuissance()))
				.orElse(null);
	}
}
