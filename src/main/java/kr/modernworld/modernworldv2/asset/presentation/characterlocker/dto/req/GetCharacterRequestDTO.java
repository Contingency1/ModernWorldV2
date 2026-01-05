package kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.req;

import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;

public record GetCharacterRequestDTO(
    Boolean status,
    String species
) {

  public CharacterSpecies toCharacterSpecies() {
    if (species == null) {
      return null;
    }
    
    return CharacterSpecies.stringToCharacterSpecies(species);
  }
}
