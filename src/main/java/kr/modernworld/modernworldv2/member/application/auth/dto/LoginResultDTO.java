package kr.modernworld.modernworldv2.member.application.auth.dto;

public record LoginResultDTO(
    String accessToken,
    String refreshToken,
    String nickname,
    Long userNo,
    Long accessExpirationMills,
    Long refreshExpirationMillis
) {

}