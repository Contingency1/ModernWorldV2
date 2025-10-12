package kr.modernworld.modernworldv2.user.presentation.oauth;

public record LoginResultDTO(
    String accessToken,
    String refreshToken,
    String nickname,
    Long userNo,
    Long accessExpirationMills,
    Long refreshExpirationMillis
) {

}