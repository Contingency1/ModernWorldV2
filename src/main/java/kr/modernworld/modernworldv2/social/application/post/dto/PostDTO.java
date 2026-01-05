package kr.modernworld.modernworldv2.social.application.post.dto;

import java.time.Instant;

public record PostDTO(
    Long no,
    String content,
    Instant createdAt,
    Boolean check,
    PostUserDTO sender,
    PostUserDTO receiver
) {

}
