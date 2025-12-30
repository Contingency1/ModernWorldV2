package kr.modernworld.modernworldv2.user.application.auth.dto;

public record RenewRefreshTokenDTO(
    String accessToken,
    String refreshToken,
    Long refreshExpirationMillis) {

}
