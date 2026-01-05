package kr.modernworld.modernworldv2.member.presentation.oauth.dto;

public record LoginResponseDTO(
    String accessToken,
    String nickname,
    Long userNo
) {

}
