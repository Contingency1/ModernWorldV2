package kr.modernworld.modernworldv2.user.application.oauth;

public record SocialUserInfoDTO(
    String uniqueIdentifier,
    String name,
    String profileImageUrl
) {

}
