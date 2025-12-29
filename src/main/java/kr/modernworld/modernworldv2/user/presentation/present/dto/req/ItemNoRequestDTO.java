package kr.modernworld.modernworldv2.user.presentation.present.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemNoRequestDTO(
    @NotNull
    @Min(1)
    Long itemNo
) {

}
