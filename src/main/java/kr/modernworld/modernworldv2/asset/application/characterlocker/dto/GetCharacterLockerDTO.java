package kr.modernworld.modernworldv2.asset.application.characterlocker.dto;

public record GetCharacterLockerDTO(
    Long no,
    Long characterNo,
    Long userNo,
    Boolean status,
    CharacterInfoDTO character
) {

}
