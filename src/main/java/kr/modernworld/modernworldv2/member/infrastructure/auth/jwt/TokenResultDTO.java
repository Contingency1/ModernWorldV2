package kr.modernworld.modernworldv2.member.infrastructure.auth.jwt;

public record TokenResultDTO(
    String token,
    Long expirationMillis
) {

}
