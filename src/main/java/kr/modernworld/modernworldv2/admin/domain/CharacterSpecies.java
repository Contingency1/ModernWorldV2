package kr.modernworld.modernworldv2.admin.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CharacterSpecies {

  DOG("dog"), CAT("cat");

  final String name;

  CharacterSpecies(String name) {
    this.name = name;
  }

  @JsonCreator
  public CharacterSpecies toCharacterSpecies(String str) {
    for (CharacterSpecies species : CharacterSpecies.values()) {
      if (species.name.equals(str)) {
        return species;
      }
    }

    throw new IllegalArgumentException("Invalid species: " + str);
  }

  @JsonValue
  public String getName() {
    return name;
  }
}
