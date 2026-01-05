package kr.modernworld.modernworldv2.asset.presentation.present.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.asset.application.present.dto.GetPresentDTO;

public record GetPresentResponseDTO(
    Long no,
    String status,
    Instant createdAt,
    PresentItemResponseDTO item,
    PresentUserResponseDTO userPresentSenderNo,
    PresentUserResponseDTO userPresentReceiverNo
) {

  public static GetPresentResponseDTO from(GetPresentDTO present) {
    return new GetPresentResponseDTO(
        present.no(),
        present.status().toString(),
        present.createdAt(),
        PresentItemResponseDTO.from(present.item()),
        PresentUserResponseDTO.from(present.userPresentSenderNo()),
        PresentUserResponseDTO.from(present.userPresentReceiverNo())
    );
  }
}
