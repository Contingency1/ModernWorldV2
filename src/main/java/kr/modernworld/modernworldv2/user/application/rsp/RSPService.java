package kr.modernworld.modernworldv2.user.application.rsp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.RewardPoint;
import kr.modernworld.modernworldv2.member.application.user.UserService;
import kr.modernworld.modernworldv2.user.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.user.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.user.application.userachievement.event.IncrementLegendAndCheckAchievementEvent;
import kr.modernworld.modernworldv2.user.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.user.domain.rsp.GameResult;
import kr.modernworld.modernworldv2.user.domain.rsp.RSP;
import kr.modernworld.modernworldv2.user.domain.rsp.RSPChoice;
import kr.modernworld.modernworldv2.user.domain.rsp.port.RSPQueryRepository;
import kr.modernworld.modernworldv2.user.domain.rsp.port.RSPRepository;
import kr.modernworld.modernworldv2.user.presentation.rsp.dto.res.RSPResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RSPService {

  private final RSPQueryRepository rspQueryRepository;
  private final RSPRepository rspRepository;
  private final UserService userService;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional(readOnly = true)
  public List<RSPResponseDTO> get(Long userNo, LocalDate date) {
    ZoneId zoneId = ZoneId.of("Asia/Seoul");

    Instant startOfDay = date.atStartOfDay(zoneId).toInstant();
    Instant endOfDay = date.plusDays(1).atStartOfDay(zoneId).toInstant();

    return rspQueryRepository.findAllByUserNoAndDate(userNo,
        startOfDay, endOfDay);
  }

  @Transactional
  public RSPResponseDTO create(Long userNo, Integer choice) {
    RSPChoice userChoice = RSPChoice.integerToRSPChoice(choice);

    RSP rspRecord = RSP.init(userNo, userChoice);
    RSP saved = rspRepository.save(rspRecord);

    Long pointToAdd = 0L;

    if (saved.getResult().equals(GameResult.WIN)) {
      pointToAdd = RewardPoint.WIN_GAME.getPoint();

      eventPublisher.publishEvent(new IncrementLegendAndCheckAchievementEvent(this, userNo,
          LegendField.RSP_WIN_COUNT));

      String eventMessage = String.format("[가위 바위 보 게임]에서 승리하셨습니다! %d포인트를 획득하셨습니다!",
          RewardPoint.WIN_GAME.getPoint());
      eventPublisher.publishEvent(new AlarmEvent(this, userNo, eventMessage, AlarmTitle.GAME));
    }

    userService.processGameResult(userNo, pointToAdd);

    return new RSPResponseDTO(saved.getNo(), saved.getUserNo(), saved.getUserChoice(),
        saved.getComputerChoice(), saved.getResult(), saved.getCreatedAt());
  }

}
