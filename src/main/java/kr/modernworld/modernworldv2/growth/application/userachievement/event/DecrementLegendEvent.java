package kr.modernworld.modernworldv2.growth.application.userachievement.event;

import kr.modernworld.modernworldv2.growth.application.userachievement.LegendField;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DecrementLegendEvent extends ApplicationEvent {

  private final Long userNo;
  private final LegendField legendField;

  public DecrementLegendEvent(Object source, Long userNo,
      LegendField legendField) {
    super(source);
    this.userNo = userNo;
    this.legendField = legendField;
  }
}
