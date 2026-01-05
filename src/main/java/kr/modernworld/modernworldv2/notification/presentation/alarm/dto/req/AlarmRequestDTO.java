package kr.modernworld.modernworldv2.notification.presentation.alarm.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.modernworld.modernworldv2.global.common.OrderBy;

public record AlarmRequestDTO(
    @Min(1)
    Long page,
    @Min(1)
    @Max(100)
    Long take,
    OrderBy orderBy
) {

  public AlarmRequestDTO {
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
