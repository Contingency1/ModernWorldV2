package kr.modernworld.modernworldv2.user.infrastructure.auth.jwt;

public record TokenResultDTO(
    String token,
    Long expirationMillis
) {

}
