package kr.modernworld.modernworldv2.user.infrastructure.repository.jwt;

public record TokenUserInfoDTO(
    String userNo,
    boolean isAdmin
) {

}
