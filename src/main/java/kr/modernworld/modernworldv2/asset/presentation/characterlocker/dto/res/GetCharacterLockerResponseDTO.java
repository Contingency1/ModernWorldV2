package kr.modernworld.modernworldv2.asset.presentation.characterlocker.dto.res;

public record GetCharacterLockerResponseDTO(
    Long no,
    Long characterNo,
    Long userNo,
    Boolean status,
    CharacterInfoDTO character
) {

}
