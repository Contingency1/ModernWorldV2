package kr.modernworld.modernworldv2.user.infrastructure.repository.jwt;

public record TokenResultDTO(
    String token,
    Long expirationMillis
) {

}
