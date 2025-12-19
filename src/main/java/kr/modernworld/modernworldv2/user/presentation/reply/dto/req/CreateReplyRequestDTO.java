package kr.modernworld.modernworldv2.user.presentation.reply.dto.req;

import jakarta.validation.constraints.Size;

public record CreateReplyRequestDTO(
    @Size(min = 1, max = 100)
    String content
) {

}
