package kr.modernworld.modernworldv2.member.presentation.user.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import kr.modernworld.modernworldv2.member.application.user.OrderByField;

public record GetUsersRequestDTO(
    @Min(1)
    @Max(Long.MAX_VALUE)
    Long page,
    @Min(1)
    @Max(50)
    Long take,
    String animal,
    String orderBy,
    @Size(min = 1, max = 50)
    String nickname
) {

  public GetUsersRequestDTO {
    if (page == null) {
      page = 1L;
    }

    if (take == null) {
      take = 10L;
    }

    if (orderBy == null || orderBy.isEmpty()) {
      orderBy = OrderByField.CREATED_AT.getField();
    }

  }
}
