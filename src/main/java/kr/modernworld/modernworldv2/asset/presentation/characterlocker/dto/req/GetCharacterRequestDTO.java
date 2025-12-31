package kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.req;

import kr.modernworld.modernworldv2.asset.domain.character.CharacterSpecies;

public record GetCharacterRequestDTO(
    Boolean status,
    CharacterSpecies species
) {

}
