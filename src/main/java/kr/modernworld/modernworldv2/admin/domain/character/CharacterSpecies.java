package kr.modernworld.modernworldv2.admin.domain.character;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CharacterSpecies {

  DOG("dog"), CAT("cat");

  final String name;

  CharacterSpecies(String name) {
    this.name = name;
  }

  @JsonCreator
  public static CharacterSpecies stringToCharacterSpecies(String str) {
    for (CharacterSpecies species : CharacterSpecies.values()) {
      if (species.name.equals(str)) {
        return species;
      }
    }

    throw new IllegalArgumentException("Invalid species: " + str);
  }

  @JsonValue
  public String getName() {
    return this.name;
  }
}
