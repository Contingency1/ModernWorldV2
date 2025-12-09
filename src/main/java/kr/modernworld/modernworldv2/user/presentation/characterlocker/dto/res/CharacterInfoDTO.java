package kr.modernworld.modernworldv2.user.presentation.characterlocker.dto.res;

import kr.modernworld.modernworldv2.admin.domain.character.CharacterSpecies;

public record CharacterInfoDTO(
    String name,
    String description,
    String image,
    CharacterSpecies species,
    Long price
) {

}
