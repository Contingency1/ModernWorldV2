package kr.modernworld.modernworldv2.social.application.rsp.dto;

import java.time.Instant;
import kr.modernworld.modernworldv2.social.domain.rsp.GameResult;
import kr.modernworld.modernworldv2.social.domain.rsp.RSPChoice;

public record GetRSPDTO(
    Long no,
    Long userNo,
    RSPChoice userChoice,
    RSPChoice computerChoice,
    GameResult result,
    Instant createdAt
) {

}
