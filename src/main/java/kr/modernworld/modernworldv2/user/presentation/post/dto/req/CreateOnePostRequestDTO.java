package kr.modernworld.modernworldv2.user.presentation.post.dto.req;

import org.hibernate.validator.constraints.Length;

public record CreateOnePostRequestDTO(
    @Length(min = 1, max = 100)
    String content
) {

}
