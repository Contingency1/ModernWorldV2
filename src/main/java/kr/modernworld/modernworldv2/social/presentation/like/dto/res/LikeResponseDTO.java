package kr.modernworld.modernworldv2.social.presentation.like.dto.res;

import kr.modernworld.modernworldv2.social.application.like.dto.LikeDTO;

public record LikeResponseDTO(
    Long no,
    LikeUserResponseDTO userLikeSenderNo,
    LikeUserResponseDTO userLikeReceiverNo
) {

  public static LikeResponseDTO from(LikeDTO dto) {
    return new LikeResponseDTO(dto.no(),
        LikeUserResponseDTO.from(dto.sender()),
        LikeUserResponseDTO.from(dto.receiver()));
  }

}
