package kr.modernworld.modernworldv2.member.infrastructure.auth.jwt;

public record TokenUserInfoDTO(
    Long userNo,
    boolean isAdmin
) {

}