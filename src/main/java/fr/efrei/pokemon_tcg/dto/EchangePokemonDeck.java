package fr.efrei.pokemon_tcg.dto;

public class EchangePokemonDeck {
    private String uuidPokemonPrincipal;
    private String uuidPokemonSecondaire;

    public String getUuidPokemonPrincipal() {
        return uuidPokemonPrincipal;
    }

    public void setUuidPokemonPrincipal(String uuidPokemonPrincipal) {
        this.uuidPokemonPrincipal = uuidPokemonPrincipal;
    }

    public String getUuidPokemonSecondaire() {
        return uuidPokemonSecondaire;
    }

    public void setUuidPokemonSecondaire(String uuidPokemonSecondaire) {
        this.uuidPokemonSecondaire = uuidPokemonSecondaire;
    }
}
