package kr.modernworld.modernworldv2.user.infrastructure.auth.jwt;

public record TokenUserInfoDTO(
    Long userNo,
    boolean isAdmin
) {

}
