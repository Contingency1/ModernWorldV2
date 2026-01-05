package kr.modernworld.modernworldv2.social.presentation.like.dto.req;

import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;

public record GetLikeRequestDTO(
    SenderReceiverNoField type
) {

  public GetLikeRequestDTO {
    if (type == null) {
      type = SenderReceiverNoField.RECEIVER_NO;
    }
  }
}
