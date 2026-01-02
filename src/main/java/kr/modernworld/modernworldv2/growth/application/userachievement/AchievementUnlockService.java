package kr.modernworld.modernworldv2.growth.application.userachievement;

import kr.modernworld.modernworldv2.growth.application.achievement.AchievementService;
import kr.modernworld.modernworldv2.growth.application.achievement.dto.AchievementInfoDTO;
import kr.modernworld.modernworldv2.growth.application.alarm.event.AlarmEvent;
import kr.modernworld.modernworldv2.growth.application.userachievement.port.UserAchievementQueryRepository;
import kr.modernworld.modernworldv2.growth.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.growth.domain.external.MemberExternalPort;
import kr.modernworld.modernworldv2.growth.domain.legend.Legend;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementUnlockService {

  private final UserAchievementService userAchievementService;
  private final UserAchievementQueryRepository userAchievementQueryRepository;
  private final AchievementService achievementService;
  private final ApplicationEventPublisher applicationEventPublisher;

  private final MemberExternalPort memberExternalPort;

  @Transactional
  public void unlockAchievement(Long userNo, Legend userLegend,
      LegendField legendField) {
    Long legendCount = switch (legendField) {
      case ATTENDANCE_COUNT -> userLegend.getAttendanceCount();
      case COMMENT_COUNT -> userLegend.getCommentCount();
      case LIKE_COUNT -> userLegend.getLikeCount();
      case ITEM_COUNT -> userLegend.getItemCount();
      case PRESENT_COUNT -> userLegend.getPresentCount();
      case RSP_WIN_COUNT -> userLegend.getRspWinCount();
    };

    AchievementTier.findByCount(legendCount).ifPresent(tier -> {
      String achievementName = legendField.getTitle() + tier.getLevel();
      checkAchievementAndGet(userNo, achievementName);
    });
  }

  private void checkAchievementAndGet(Long userNo, String achievementName) {
    Boolean achievementExists = userAchievementQueryRepository
        .existsByUserNoAndAchievementName(userNo, achievementName);

    if (!achievementExists) {
      AchievementInfoDTO achievementInfo = achievementService.getAchievementInfo(achievementName);

      userAchievementService.createUserAchievement(userNo, achievementInfo.no());
      memberExternalPort.increaseCurrentAccumulationPoint(userNo, achievementInfo.point());

      applicationEventPublisher.publishEvent(
          new AlarmEvent(this, userNo,
              String.format("업적 [%s]을 달성했습니다! %s포인트를 흭득하셨습니다!",
                  achievementInfo.title(), achievementInfo.point()),
              AlarmTitle.ACHIEVEMENT));
    }

  }
}
