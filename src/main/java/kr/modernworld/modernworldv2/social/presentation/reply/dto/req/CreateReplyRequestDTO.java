package kr.modernworld.modernworldv2.social.presentation.reply.dto.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateReplyRequestDTO(
    @NotNull
    @Size(min = 1, max = 100)
    String content
) {

}
