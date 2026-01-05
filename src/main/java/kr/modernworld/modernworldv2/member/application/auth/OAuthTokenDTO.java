package kr.modernworld.modernworldv2.member.application.auth;

public record OAuthTokenDTO(
    String socialAccessToken,
    String socialRefreshToken,
    Integer accessExpiresIn,
    Integer refreshExpiresIn
) {

}
