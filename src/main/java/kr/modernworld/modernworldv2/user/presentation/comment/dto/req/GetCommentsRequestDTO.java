package kr.modernworld.modernworldv2.user.presentation.comment.dto.req;

import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;

public record GetCommentsRequestDTO(
    Long page,
    Long take,
    OrderBy orderBy,
    SenderReceiverNoField type
) {

  public GetCommentsRequestDTO {
    if (page == null) {
      page = 1L;
    }

    if (take == null) {
      take = 10L;
    }

    if (orderBy == null) {
      orderBy = OrderBy.DESC;
    }
  }
}
