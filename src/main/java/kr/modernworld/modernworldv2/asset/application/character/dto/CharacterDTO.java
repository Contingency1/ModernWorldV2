package kr.modernworld.modernworldv2.asset.application.character.dto;

import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;

public record CharacterDTO(
    Long no,
    String name,
    String description,
    String image,
    CharacterSpecies species,
    Long price
) {

}
