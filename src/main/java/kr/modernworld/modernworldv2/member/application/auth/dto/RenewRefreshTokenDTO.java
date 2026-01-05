package kr.modernworld.modernworldv2.member.application.auth.dto;

public record RenewRefreshTokenDTO(
    String accessToken,
    String refreshToken,
    Long refreshExpirationMillis) {

}
