package kr.modernworld.modernworldv2.social.application.like.port;

public record LikeDTO(
    Long no,
    LikeUserDTO sender,
    LikeUserDTO receiver
) {

}
