package kr.modernworld.modernworldv2.social.application.like.dto;

public record LikeDTO(
    Long no,
    LikeUserDTO sender,
    LikeUserDTO receiver
) {

}
