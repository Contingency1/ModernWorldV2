package kr.modernworld.modernworldv2.member.presentation.user.dto.req;

import jakarta.validation.constraints.Size;

public record UpdateUserDescriptionRequestDTO(
    @Size(max = 100)
    String description
) {

}
