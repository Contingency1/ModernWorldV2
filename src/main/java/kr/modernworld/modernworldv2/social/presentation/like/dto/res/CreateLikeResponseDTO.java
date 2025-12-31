package kr.modernworld.modernworldv2.social.presentation.like.dto.res;

public record CreateLikeResponseDTO(
    Long no,
    CreateLikeUserInfoDTO userLikeSenderNo,
    CreateLikeUserInfoDTO userLikeReceiverNo
) {

}
