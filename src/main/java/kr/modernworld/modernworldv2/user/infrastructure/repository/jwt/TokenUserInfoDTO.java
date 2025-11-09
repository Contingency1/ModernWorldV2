package kr.modernworld.modernworldv2.user.infrastructure.repository.jwt;

public record TokenUserInfoDTO(
    Long userNo,
    boolean isAdmin
) {

}
