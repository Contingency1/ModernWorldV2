package kr.modernworld.modernworldv2.social.presentation.like.dto.res.get;

public record ReceivedLikeResponse(
    Long no,
    GetLikeUserInfoDTO sender
) implements LikeResponseDTO {

}
