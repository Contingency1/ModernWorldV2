package kr.modernworld.modernworldv2.social.application.rsp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.RewardPoint;
import kr.modernworld.modernworldv2.growth.domain.external.MemberExternalPort;
import kr.modernworld.modernworldv2.social.application.rsp.dto.GetRSPDTO;
import kr.modernworld.modernworldv2.social.application.rsp.event.RSPWinEvent;
import kr.modernworld.modernworldv2.social.application.rsp.port.RSPQueryRepository;
import kr.modernworld.modernworldv2.social.domain.rsp.GameResult;
import kr.modernworld.modernworldv2.social.domain.rsp.RSP;
import kr.modernworld.modernworldv2.social.domain.rsp.RSPChoice;
import kr.modernworld.modernworldv2.social.domain.rsp.port.RSPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RSPService {

  private final RSPQueryRepository rspQueryRepository;
  private final RSPRepository rspRepository;
  private final ApplicationEventPublisher eventPublisher;

  private final MemberExternalPort memberExternalPort;

  @Transactional(readOnly = true)
  public List<GetRSPDTO> get(Long userNo, LocalDate date) {
    ZoneId zoneId = ZoneId.of("Asia/Seoul");

    Instant startOfDay = date.atStartOfDay(zoneId).toInstant();
    Instant endOfDay = date.plusDays(1).atStartOfDay(zoneId).toInstant();

    return rspQueryRepository.findAllByUserNoAndDate(userNo,
        startOfDay, endOfDay);
  }

  @Transactional
  public GetRSPDTO create(Long userNo, Integer choice) {
    RSPChoice userChoice = RSPChoice.integerToRSPChoice(choice);

    RSP rspRecord = RSP.init(userNo, userChoice);
    RSP saved = rspRepository.save(rspRecord);

    Long pointToAdd = 0L;

    if (saved.getResult().equals(GameResult.WIN)) {
      pointToAdd = RewardPoint.WIN_GAME.getPoint();

      eventPublisher.publishEvent(new RSPWinEvent(userNo));
    }

    memberExternalPort.processGameResult(userNo, pointToAdd);

    return new GetRSPDTO(saved.getNo(), saved.getUserNo(), saved.getUserChoice(),
        saved.getComputerChoice(), saved.getResult(), saved.getCreatedAt());
  }

}
