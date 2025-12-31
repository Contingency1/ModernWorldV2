package kr.modernworld.modernworldv2.social.presentation.post.dto.res;

import java.time.Instant;

public record PostResponseDTO(
    Long no,
    String content,
    Instant createdAt,
    Boolean check,
    PostUserInfoDTO userPostSenderNo,
    PostUserInfoDTO userPostReceiverNo
) {

}
