package kr.modernworld.modernworldv2.user.presentation.characterlocker.dto.res;

public record CharacterLockerResponseDTO(
    Long no,
    Long characterNo,
    Long userNo,
    Boolean status
) {

}
