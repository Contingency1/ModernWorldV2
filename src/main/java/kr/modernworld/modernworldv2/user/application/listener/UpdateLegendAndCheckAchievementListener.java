package kr.modernworld.modernworldv2.user.application.listener;

import kr.modernworld.modernworldv2.user.application.LegendService;
import kr.modernworld.modernworldv2.user.application.event.UpdateLegendCheckAchievementEvent;
import kr.modernworld.modernworldv2.user.application.userachievement.AchievementUnlockService;
import kr.modernworld.modernworldv2.user.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.user.domain.Legend;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateLegendAndCheckAchievementListener {

  private final LegendService legendService;
  private final AchievementUnlockService achievementUnlockService;

  @EventListener
  @Transactional
  public void onUpdate(UpdateLegendCheckAchievementEvent event) {
    Long userNo = event.getUserNo();
    LegendField legendField = event.getLegendField();

    Legend legend = legendService.update(userNo, legendField);

    achievementUnlockService.unlockAchievement(userNo, legend, legendField);
  }


}
