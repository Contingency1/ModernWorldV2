package kr.modernworld.modernworldv2.asset.application.character.api;

import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;

public record CharacterApiDTO(
    Long no,
    String name,
    String description,
    String image,
    CharacterSpecies species,
    Long price
) {

}
