package kr.modernworld.modernworldv2.social.presentation.rsp.dto.res;

import java.time.Instant;
import kr.modernworld.modernworldv2.social.application.rsp.dto.GetRSPDTO;

public record RSPResponseDTO(
    Long no,
    Long userNo,
    String userChoice,
    String computerChoice,
    String result,
    Instant createdAt
) {

  public static RSPResponseDTO from(GetRSPDTO input) {
    return new RSPResponseDTO(
        input.no(),
        input.userNo(),
        input.userChoice().getName(),
        input.computerChoice().getName(),
        input.result().getResult(),
        input.createdAt()
    );
  }

}
