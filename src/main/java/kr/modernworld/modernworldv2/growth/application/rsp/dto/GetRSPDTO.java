package kr.modernworld.modernworldv2.growth.application.rsp.dto;

import java.time.Instant;
import kr.modernworld.modernworldv2.growth.domain.rsp.GameResult;
import kr.modernworld.modernworldv2.growth.domain.rsp.RSPChoice;

public record GetRSPDTO(
    Long no,
    Long userNo,
    RSPChoice userChoice,
    RSPChoice computerChoice,
    GameResult result,
    Instant createdAt
) {

}
