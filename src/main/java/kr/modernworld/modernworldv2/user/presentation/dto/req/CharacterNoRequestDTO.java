package kr.modernworld.modernworldv2.user.presentation.dto.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CharacterNoRequestDTO(
    @NotNull @Min(1)
    Long characterNo
) {

}
