package kr.modernworld.modernworldv2.notification.application.alarm.listener;

import kr.modernworld.modernworldv2.asset.domain.present.event.PresentCreatedEvent;
import kr.modernworld.modernworldv2.asset.domain.present.event.PresentItemRefundedEvent;
import kr.modernworld.modernworldv2.global.common.RewardPoint;
import kr.modernworld.modernworldv2.growth.domain.userachievement.event.AchievementUnlockedEvent;
import kr.modernworld.modernworldv2.notification.application.alarm.AlarmService;
import kr.modernworld.modernworldv2.notification.application.sse.SseEmitterService;
import kr.modernworld.modernworldv2.notification.application.sse.SseEvent;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.social.application.rsp.event.RSPWinEvent;
import kr.modernworld.modernworldv2.social.domain.comment.event.CommentCreatedEvent;
import kr.modernworld.modernworldv2.social.domain.like.event.LikeCreatedEvent;
import kr.modernworld.modernworldv2.social.domain.neighbor.event.NeighborConnectedEvent;
import kr.modernworld.modernworldv2.social.domain.neighbor.event.NeighborSentEvent;
import kr.modernworld.modernworldv2.social.domain.post.event.PostCreatedEvent;
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

  private void saveAlarmAndSendSSE(Long userNo, AlarmTitle game, String message) {
    alarmService.create(userNo, game, message);
    sseEmitterService.send(userNo, new SseEvent(game.getTitle(), message));
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void RSPWin(RSPWinEvent event) {
    Long userNo = event.userNo();

    String message = String.format("[가위 바위 보 게임]에서 승리하셨습니다! %d포인트를 획득하셨습니다!",
        RewardPoint.WIN_GAME.getPoint());

    saveAlarmAndSendSSE(userNo, AlarmTitle.GAME, message);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void givePresent(PresentCreatedEvent event) {
    Long receiverNo = event.receiverNo();
    String senderName = event.senderName();
    String itemName = event.itemName();

    String message = String.format("%s님이 %s을(를) 선물로 보냈습니다.", senderName,
        itemName);

    saveAlarmAndSendSSE(receiverNo, AlarmTitle.PRESENT, message);
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

    saveAlarmAndSendSSE(userNo, AlarmTitle.PRESENT, message);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createComment(CommentCreatedEvent event) {
    Long receiverNo = event.receiverNo();
    String senderName = event.senderName();

    String message = String.format("%s님이 방명록을 남겼습니다.", senderName);

    saveAlarmAndSendSSE(receiverNo, AlarmTitle.COMMENT, message);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createOneLike(LikeCreatedEvent event) {
    Long receiverNo = event.receiverNo();
    String senderName = event.senderName();

    String message = String.format("%s님이 좋아요를 눌렀습니다.", senderName);

    saveAlarmAndSendSSE(receiverNo, AlarmTitle.LIKE, message);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void createOnePost(PostCreatedEvent event) {
    Long receiverNo = event.receiverNo();
    Long senderNo = event.senderNo();

    String message = String.format("%s님이 쪽지를 보내셨습니다.", "익명");

    saveAlarmAndSendSSE(receiverNo, AlarmTitle.POST, message);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void sendOneNeighborRequest(NeighborSentEvent event) {
    Long receiverNo = event.receiverNo();
    String senderName = event.senderName();

    String message = String.format("%s님에게 이웃 요청이 왔습니다.", senderName);

    saveAlarmAndSendSSE(receiverNo, AlarmTitle.NEIGHBOR, message);
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void connectNeighbor(NeighborConnectedEvent event) {
    Long senderNo = event.senderNo();
    Long receiverNo = event.receiverNo();
    String senderName = event.senderName();
    String receiverName = event.receiverName();

    String messageForSender = String.format("%s님과 이웃이 되었습니다.", receiverName);
    String messageForReceiver = String.format("%s님과 이웃이 되었습니다.", senderName);

    alarmService.create(senderNo, AlarmTitle.NEIGHBOR, messageForSender);
    alarmService.create(receiverNo, AlarmTitle.NEIGHBOR, messageForReceiver);

    sseEmitterService.send(senderNo,
        new SseEvent(AlarmTitle.NEIGHBOR.getTitle(), messageForSender));
    sseEmitterService.send(receiverNo,
        new SseEvent(AlarmTitle.NEIGHBOR.getTitle(), messageForReceiver));
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void unlockAchievement(AchievementUnlockedEvent event) {
    Long userNo = event.userNo();
    String achievementTitle = event.achievementTitle();
    Long rewardPoint = event.achievementRewardPoint();

    String message = String.format("업적 [%s]을 달성했습니다! %s포인트를 흭득하셨습니다!",
        achievementTitle, rewardPoint);

    saveAlarmAndSendSSE(userNo, AlarmTitle.ACHIEVEMENT, message);
  }
}
