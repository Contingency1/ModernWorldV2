package kr.modernworld.modernworldv2.user.presentation.oauth;

public record LoginResponseDTO(
    String accessToken,
    String nickname,
    Long userNo
) {

}
