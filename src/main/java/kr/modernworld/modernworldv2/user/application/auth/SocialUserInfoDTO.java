package kr.modernworld.modernworldv2.user.application.auth;

public record SocialUserInfoDTO(
    String uniqueIdentifier,
    String name,
    String profileImageUrl
) {

}
