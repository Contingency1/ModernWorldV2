package kr.modernworld.modernworldv2.growth.application.userachievement.listener;

import kr.modernworld.modernworldv2.asset.domain.inventory.event.InventoryCreatedEvent;
import kr.modernworld.modernworldv2.growth.application.legend.LegendService;
import kr.modernworld.modernworldv2.growth.application.userachievement.AchievementUnlockService;
import kr.modernworld.modernworldv2.growth.application.userachievement.LegendField;
import kr.modernworld.modernworldv2.growth.domain.legend.Legend;
import kr.modernworld.modernworldv2.social.application.rsp.event.RSPWinEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegendEventListener {

  private final LegendService legendService;
  private final AchievementUnlockService achievementUnlockService;

  @EventListener
  public void createOneInventory(InventoryCreatedEvent event) {
    Long userNo = event.userNo();

    incrementLegendAndCheckAchievement(userNo, LegendField.ITEM_COUNT);
  }

  @EventListener
  public void createOneRSPRecord(RSPWinEvent event) {
    Long userNo = event.userNo();

    incrementLegendAndCheckAchievement(userNo, LegendField.RSP_WIN_COUNT);
  }

  private void incrementLegendAndCheckAchievement(Long userNo, LegendField field) {
    Legend legend = legendService.increment(userNo, field);

    achievementUnlockService.unlockAchievement(userNo, legend, field);
  }
}
