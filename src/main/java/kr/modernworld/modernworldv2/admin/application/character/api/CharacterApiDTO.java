package kr.modernworld.modernworldv2.admin.application.character.api;

import kr.modernworld.modernworldv2.admin.domain.character.CharacterSpecies;

public record CharacterApiDTO(
    Long no,
    String name,
    String description,
    String image,
    CharacterSpecies species,
    Long price
) {

}
