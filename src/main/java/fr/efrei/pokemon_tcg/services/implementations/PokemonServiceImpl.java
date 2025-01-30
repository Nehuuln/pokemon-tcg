package fr.efrei.pokemon_tcg.services.implementations;

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
		pokemonACreer.setEtoile(genererRarete());
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

	private int genererRarete() {
		int rand = random.nextInt(100);

		if (rand < 50) return 1;
		if (rand < 75) return 2;
		if (rand < 90) return 3;
		if (rand < 98) return 4;
		return 5;
	}
}
