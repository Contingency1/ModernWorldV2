package kr.modernworld.modernworldv2.user.presentation.reply.dto.req;

import jakarta.validation.constraints.Min;
import kr.modernworld.modernworldv2.global.common.OrderBy;

public record GetAllReplyRequestDTO(
    @Min(1)
    Long page,
    @Min(1)
    Long take,
    OrderBy orderBy
) {

  public GetAllReplyRequestDTO {
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
