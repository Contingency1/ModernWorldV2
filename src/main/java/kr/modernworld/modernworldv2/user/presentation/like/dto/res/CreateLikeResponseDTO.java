package kr.modernworld.modernworldv2.user.presentation.like.dto.res;

public record CreateLikeResponseDTO(
    Long no,
    CreateLikeUserInfoDTO userLikeSenderNo,
    CreateLikeUserInfoDTO userLikeReceiverNo
) {

}
