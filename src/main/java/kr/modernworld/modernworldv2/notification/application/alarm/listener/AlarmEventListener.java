package kr.modernworld.modernworldv2.notification.application.alarm.listener;

import kr.modernworld.modernworldv2.global.common.RewardPoint;
import kr.modernworld.modernworldv2.growth.application.sse.SseEmitterService;
import kr.modernworld.modernworldv2.growth.application.sse.SseEvent;
import kr.modernworld.modernworldv2.notification.application.alarm.AlarmService;
import kr.modernworld.modernworldv2.notification.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.social.application.rsp.event.RSPWinEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class AlarmEventListener {

  private final AlarmService alarmService;
  private final SseEmitterService sseEmitterService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void onAlarmEvent(AlarmEvent event) {
    Long userNo = event.getUserNo();
    AlarmTitle title = event.getTitle();
    String message = event.getMessage();

    alarmService.create(userNo, title, message);
    sseEmitterService.send(userNo, new SseEvent(title.getTitle(), message));
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void RSPWin(RSPWinEvent event) {
    Long userNo = event.userNo();

    String message = String.format("[가위 바위 보 게임]에서 승리하셨습니다! %d포인트를 획득하셨습니다!",
        RewardPoint.WIN_GAME.getPoint());

    alarmService.create(userNo, AlarmTitle.GAME, message);
    sseEmitterService.send(userNo, new SseEvent(AlarmTitle.GAME.getTitle(), message));
  }
}
