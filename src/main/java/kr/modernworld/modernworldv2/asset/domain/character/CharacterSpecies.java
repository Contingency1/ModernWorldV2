package kr.modernworld.modernworldv2.asset.domain.character;

import lombok.Getter;

@Getter
public enum CharacterSpecies {

  DOG("dog"), CAT("cat");

  final String name;

  CharacterSpecies(String name) {
    this.name = name;
  }

  public static CharacterSpecies stringToCharacterSpecies(String str) {
    for (CharacterSpecies species : CharacterSpecies.values()) {
      if (species.name.equals(str)) {
        return species;
      }
    }

    throw new IllegalArgumentException("Invalid species: " + str);
  }

}
