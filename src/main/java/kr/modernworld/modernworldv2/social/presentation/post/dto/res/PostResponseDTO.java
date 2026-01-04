package kr.modernworld.modernworldv2.social.presentation.post.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.social.application.post.dto.PostDTO;

public record PostResponseDTO(
    Long no,
    String content,
    Instant createdAt,
    Boolean check,
    PostUserResponseDTO userPostSenderNo,
    PostUserResponseDTO userPostReceiverNo
) {

  public static PostResponseDTO from(PostDTO dto) {
    return new PostResponseDTO(
        dto.no(),
        dto.content(),
        dto.createdAt(),
        dto.check(),
        PostUserResponseDTO.from(dto.sender()),
        PostUserResponseDTO.from(dto.receiver())
    );
  }
}
