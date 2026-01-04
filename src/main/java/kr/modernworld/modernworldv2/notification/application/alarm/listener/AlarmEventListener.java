package kr.modernworld.modernworldv2.notification.application.alarm.listener;

import kr.modernworld.modernworldv2.asset.domain.present.event.PresentCreatedEvent;
import kr.modernworld.modernworldv2.asset.domain.present.event.PresentItemRefundedEvent;
import kr.modernworld.modernworldv2.global.common.RewardPoint;
import kr.modernworld.modernworldv2.notification.application.alarm.AlarmService;
import kr.modernworld.modernworldv2.notification.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.notification.application.sse.SseEmitterService;
import kr.modernworld.modernworldv2.notification.application.sse.SseEvent;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.social.application.rsp.event.RSPWinEvent;
import kr.modernworld.modernworldv2.social.domain.comment.event.CommentCreatedEvent;
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

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void givePresent(PresentCreatedEvent event) {
    Long senderNo = event.senderNo();
    Long receiverNo = event.receiverNo();
    String itemName = event.itemName();

    String message = String.format("%s님이 %s을(를) 선물로 보냈습니다.", "익명",
        itemName);

    alarmService.create(receiverNo, AlarmTitle.PRESENT, message);
    sseEmitterService.send(receiverNo, new SseEvent(AlarmTitle.PRESENT.getTitle(), message));
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void handlePresentRefund(PresentItemRefundedEvent event) {
    Long userNo = event.userNo();
    String itemName = event.itemName();
    Long refundedPoint = event.refundedPoint();

    String message = String.format(
        "%s은(는) 이미 보유중인 아이템 입니다. 아이템 가격의 50%%, [%d]포인트로 반환되었습니다.",
        itemName, refundedPoint
    );

    alarmService.create(userNo, AlarmTitle.PRESENT, message);
    sseEmitterService.send(userNo, new SseEvent(AlarmTitle.PRESENT.getTitle(), message));
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createComment(CommentCreatedEvent event) {
    Long receiverNo = event.receiverNo();
    String senderName = event.senderName();

    String message = String.format("%s님이 방명록을 남겼습니다.", senderName);

    alarmService.create(receiverNo, AlarmTitle.COMMENT, message);
    sseEmitterService.send(receiverNo, new SseEvent(AlarmTitle.COMMENT.getTitle(), message));
  }
}
