package kr.modernworld.modernworldv2.user.presentation.dto.req;

import kr.modernworld.modernworldv2.admin.domain.CharacterSpecies;

public record GetCharacterRequestDTO(
    Boolean status,
    CharacterSpecies species
) {

}
