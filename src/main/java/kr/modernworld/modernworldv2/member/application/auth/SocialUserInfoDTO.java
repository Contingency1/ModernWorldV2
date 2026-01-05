package kr.modernworld.modernworldv2.member.application.auth;

public record SocialUserInfoDTO(
    String uniqueIdentifier,
    String name,
    String profileImageUrl
) {

}
