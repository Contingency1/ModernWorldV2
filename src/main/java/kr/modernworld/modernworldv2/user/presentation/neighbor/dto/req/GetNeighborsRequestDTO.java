package kr.modernworld.modernworldv2.user.presentation.neighbor.dto.req;

import jakarta.validation.constraints.Max;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;

public record GetNeighborsRequestDTO(
    Long page,
    @Max(100)
    Long take,
    OrderBy orderBy,
    Boolean status,
    SenderReceiverNoField type
) {

  public GetNeighborsRequestDTO {
    if (page == null) {
      page = 1L;
    }

    if (take == null) {
      take = 10L;
    }

    if (orderBy == null) {
      orderBy = OrderBy.DESC;
    }

    if (status == null) {
      status = false;
    }
  }
}
