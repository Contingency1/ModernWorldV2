package kr.modernworld.modernworldv2.user.presentation.characterlocker.dto.res;

public record GetCharacterLockerResponseDTO(
    Long no,
    Long characterNo,
    Long userNo,
    Boolean status,
    CharacterInfoDTO character
) {

}
