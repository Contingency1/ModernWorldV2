package kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.res;

import kr.modernworld.modernworldv2.asset.application.characterlocker.dto.CharacterInfoDTO;

public record CharacterInfoResponseDTO(
    String name,
    String description,
    String image,
    String species,
    Long price
) {

  public static CharacterInfoResponseDTO from(CharacterInfoDTO characterInfo) {
    return new CharacterInfoResponseDTO(
        characterInfo.name(),
        characterInfo.description(),
        characterInfo.image(),
        characterInfo.species().getName(),
        characterInfo.price()
    );
  }
}
