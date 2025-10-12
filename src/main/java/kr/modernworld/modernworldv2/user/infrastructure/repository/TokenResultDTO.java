package kr.modernworld.modernworldv2.user.infrastructure.repository;

public record TokenResultDTO(
    String token,
    Long expirationMillis
) {

}
