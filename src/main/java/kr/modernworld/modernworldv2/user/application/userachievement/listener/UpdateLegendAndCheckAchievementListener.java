package kr.modernworld.modernworldv2.user.application.userachievement.listener;

import kr.modernworld.modernworldv2.user.application.legend.LegendService;
import kr.modernworld.modernworldv2.user.application.userachievement.AchievementUnlockService;
import kr.modernworld.modernworldv2.user.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.user.application.userachievement.event.DecrementLegendEvent;
import kr.modernworld.modernworldv2.user.application.userachievement.event.IncrementLegendAndCheckAchievementEvent;
import kr.modernworld.modernworldv2.user.domain.legend.Legend;
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
  public void onIncrement(IncrementLegendAndCheckAchievementEvent event) {
    Long userNo = event.getUserNo();
    LegendField legendField = event.getLegendField();

    Legend legend = legendService.increment(userNo, legendField);

    achievementUnlockService.unlockAchievement(userNo, legend, legendField);
  }

  @EventListener
  @Transactional
  public void onDecrement(DecrementLegendEvent event) {
    Long userNo = event.getUserNo();
    LegendField legendField = event.getLegendField();

    legendService.decrement(userNo, legendField);
  }


}
