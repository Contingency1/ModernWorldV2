package kr.modernworld.modernworldv2.social.presentation.like.dto.res.get;

public record GivenLikeResponse(
    Long no,
    GetLikeUserInfoDTO receiver
) implements LikeResponseDTO {

}
