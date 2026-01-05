package kr.modernworld.modernworldv2.social.presentation.comment.dto.req;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCommentRequestDTO(
    @Size(min = 1, max = 100)
    @NotNull
    String content
) {

}
