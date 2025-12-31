package kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.res;

import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;

public record CharacterInfoDTO(
    String name,
    String description,
    String image,
    CharacterSpecies species,
    Long price
) {

}
