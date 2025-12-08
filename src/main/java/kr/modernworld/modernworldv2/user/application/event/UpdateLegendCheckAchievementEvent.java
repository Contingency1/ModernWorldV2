package kr.modernworld.modernworldv2.user.application.event;

import kr.modernworld.modernworldv2.user.application.userachievement.LegendField;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UpdateLegendCheckAchievementEvent extends ApplicationEvent {

  private final Long userNo;
  private final LegendField legendField;

  public UpdateLegendCheckAchievementEvent(Object source, Long userNo, LegendField legendField) {
    super(source);
    this.userNo = userNo;
    this.legendField = legendField;
  }
}
