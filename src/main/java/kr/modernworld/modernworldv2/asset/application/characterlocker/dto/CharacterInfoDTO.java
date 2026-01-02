package kr.modernworld.modernworldv2.asset.application.characterlocker.dto;

import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;

public record CharacterInfoDTO(
    String name,
    String description,
    String image,
    CharacterSpecies species,
    Long price
) {

}
