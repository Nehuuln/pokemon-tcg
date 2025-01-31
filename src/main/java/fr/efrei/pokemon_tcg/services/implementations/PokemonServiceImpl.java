package fr.efrei.pokemon_tcg.services.implementations;

import fr.efrei.pokemon_tcg.constants.PokemonAttaques;
import fr.efrei.pokemon_tcg.constants.TypePokemon;
import fr.efrei.pokemon_tcg.dto.CreatePokemon;
import fr.efrei.pokemon_tcg.models.Pokemon;
import fr.efrei.pokemon_tcg.repositories.PokemonRepository;
import fr.efrei.pokemon_tcg.services.IPokemonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class PokemonServiceImpl implements IPokemonService {

	private final PokemonRepository repository;
	private final Random random = new Random();

	public PokemonServiceImpl(PokemonRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<Pokemon> findAll(TypePokemon typePokemon) {
		if (typePokemon == null) {
			return repository.findAll();
		}
		return repository.findAllByType(typePokemon);
	}

	@Override
	public void create(CreatePokemon pokemon) {
		Pokemon pokemonACreer = new Pokemon();
		pokemonACreer.setType(pokemon.getType());
		pokemonACreer.setNom(pokemon.getNom());

		int rarete = genererRarete();
		pokemonACreer.setEtoile(rarete);
		pokemonACreer.setPuissance(genererPuissance(rarete));

		String[] attaques = genererAttaques(pokemon.getType());
		pokemonACreer.setAttaque1(attaques[0]);
		pokemonACreer.setAttaque2(attaques[1]);
		repository.save(pokemonACreer);
	}

	@Override
	public Pokemon findById(String uuid) {
		return repository.findById(uuid).orElse(null);
	}

	@Override
	public boolean update(String uuid, Pokemon pokemon) {
		Pokemon pokemonAModifier = findById(uuid);
		if (pokemonAModifier == null) {
			return false;
		}
		pokemonAModifier.setNom(pokemon.getNom());
		pokemonAModifier.setEtoile(pokemon.getEtoile());
		pokemonAModifier.setType(pokemon.getType());
		repository.save(pokemonAModifier);
		return true;
	}

	@Override
	public boolean delete(String uuid) {
		Pokemon pokemonASupprimer = findById(uuid);
		if (pokemonASupprimer == null) {
			return false;
		}
		repository.deleteById(uuid);
		return true;
	}

	@Override
	public Pokemon tirerAleatoire() {
		return null;
	}

	private int genererRarete() {
		int rand = random.nextInt(100);

		if (rand < 50) return 1;
		if (rand < 75) return 2;
		if (rand < 90) return 3;
		if (rand < 98) return 4;
		return 5;
	}

	private int genererPuissance(int rarete) {
		Random random = new Random();
		return switch (rarete) {
			case 1 -> random.nextInt(20) + 1;  // 1 à 20
			case 2 -> random.nextInt(20) + 21; // 21 à 40
			case 3 -> random.nextInt(20) + 41; // 41 à 60
			case 4 -> random.nextInt(20) + 61; // 61 à 80
			case 5 -> random.nextInt(20) + 81; // 81 à 100
			default -> 1;
		};
	}

	private String[] genererAttaques(TypePokemon type) {
		return PokemonAttaques.getAttaquesParType(type);
	}

}
