package kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.res;

import kr.modernworld.modernworldv2.asset.application.characterlocker.dto.GetCharacterLockerDTO;

public record GetCharacterLockerResponseDTO(
    Long no,
    Long characterNo,
    Long userNo,
    Boolean status,
    CharacterInfoResponseDTO character
) {

  public static GetCharacterLockerResponseDTO from(GetCharacterLockerDTO input) {
    return new GetCharacterLockerResponseDTO(
        input.no(),
        input.characterNo(),
        input.userNo(),
        input.status(),
        CharacterInfoResponseDTO.from(input.character())
    );
  }

}
