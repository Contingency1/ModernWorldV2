package kr.modernworld.modernworldv2.user.application.listener;

import kr.modernworld.modernworldv2.user.application.AlarmService;
import kr.modernworld.modernworldv2.user.application.event.AlarmEvent;
import kr.modernworld.modernworldv2.user.application.sse.SseEmitterService;
import kr.modernworld.modernworldv2.user.application.sse.SseEvent;
import kr.modernworld.modernworldv2.user.domain.alarm.AlarmTitle;
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
}
