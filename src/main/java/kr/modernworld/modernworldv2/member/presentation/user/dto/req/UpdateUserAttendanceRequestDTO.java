package kr.modernworld.modernworldv2.member.presentation.user.dto.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateUserAttendanceRequestDTO(
    @Min(1)
    @Max(10)
    Integer stickerNo
) {

}
