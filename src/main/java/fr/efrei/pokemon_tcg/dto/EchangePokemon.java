package fr.efrei.pokemon_tcg.dto;

public class EchangePokemon {
    public static class EchangeRequest {
        private String uuidDresseur2;
        private String uuidPokemon1;
        private String uuidPokemon2;

        public EchangeRequest() {}

        public String getUuidDresseur2() { return uuidDresseur2; }
        public void setUuidDresseur2(String uuidDresseur2) { this.uuidDresseur2 = uuidDresseur2; }

        public String getUuidPokemon1() { return uuidPokemon1; }
        public void setUuidPokemon1(String uuidPokemon1) { this.uuidPokemon1 = uuidPokemon1; }

        public String getUuidPokemon2() { return uuidPokemon2; }
        public void setUuidPokemon2(String uuidPokemon2) { this.uuidPokemon2 = uuidPokemon2; }
    }

}
