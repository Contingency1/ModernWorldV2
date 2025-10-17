package kr.modernworld.modernworldv2.user.application.oauth;

public record OAuthTokenDTO(
    String socialAccessToken,
    String socialRefreshToken,
    Integer accessExpiresIn,
    Integer refreshExpiresIn
) {

}
