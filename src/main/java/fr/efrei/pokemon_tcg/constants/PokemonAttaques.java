package fr.efrei.pokemon_tcg.constants;

import java.util.Map;
import java.util.Random;
import java.util.List;

public class PokemonAttaques {
    private static final Random RANDOM = new Random();

    private static final Map<TypePokemon, List<String>> ATTAQUES_PAR_TYPE = Map.ofEntries(
            Map.entry(TypePokemon.NORMAL, List.of("Charge", "Combo-Griffe", "Pisto-Poing", "Détritus")),
            Map.entry(TypePokemon.FEU, List.of("Flamèche", "Lance-Flammes", "Boutefeu", "Canicule")),
            Map.entry(TypePokemon.EAU, List.of("Pistolet à O", "Surf", "Hydrocanon", "Bulles d'O")),
            Map.entry(TypePokemon.PLANTE, List.of("Fouet Lianes", "Tempête Florale", "Lance-Soleil", "Feuille Magik")),
            Map.entry(TypePokemon.ACIER, List.of("Griffe Acier", "Tête de Fer", "Luminocanon", "Poing Météor")),
            Map.entry(TypePokemon.DRAGON, List.of("Dracogriffe", "Dracochoc", "Colère", "Ultralaser")),
            Map.entry(TypePokemon.ELECTRIQUE, List.of("Éclair", "Tonnerre", "Fatal-Foudre", "Étincelle")),
            Map.entry(TypePokemon.INSECTE, List.of("Dard-Venin", "Bourdon", "Piqûre", "Mégacorne")),
            Map.entry(TypePokemon.PSY, List.of("Choc Mental", "Psyko", "Onde Folie", "Rafale Psy")),
            Map.entry(TypePokemon.SOL, List.of("Jet de Sable", "Séisme", "Telluriforce", "Coud'Boue")),
            Map.entry(TypePokemon.TENEBRE, List.of("Morsure", "Machouille", "Tricherie", "Poursuite")),
            Map.entry(TypePokemon.COMBAT, List.of("Balayage", "Uppercut", "Close Combat", "Coup de Poing")),
            Map.entry(TypePokemon.FEE, List.of("Câlinerie", "Pouvoir Lunaire", "Vampibaiser", "Brume")),
            Map.entry(TypePokemon.GLACE, List.of("Laser Glace", "Vent Glace", "Blizzard", "Avalanche")),
            Map.entry(TypePokemon.POISON, List.of("Dard-Venin", "Bomb-Beurk", "Toxik", "Pointe Toxik")),
            Map.entry(TypePokemon.ROCHE, List.of("Jet-Pierres", "Éboulement", "Lame de Roc", "Roulade")),
            Map.entry(TypePokemon.SPECTRE, List.of("Léchouille", "Ball'Ombre", "Ténèbres", "Cauchemar")),
            Map.entry(TypePokemon.VOL, List.of("Cru-Aile", "Piqué", "Bec Vrille", "Tornade"))
    );

    public static String[] getAttaquesParType(TypePokemon type) {
        List<String> attaques = ATTAQUES_PAR_TYPE.getOrDefault(type, List.of("Charge", "Tackle"));

        int index1 = RANDOM.nextInt(attaques.size());
        int index2;

        do {
            index2 = RANDOM.nextInt(attaques.size());
        } while (index1 == index2);

        return new String[]{attaques.get(index1), attaques.get(index2)};
    }
}
