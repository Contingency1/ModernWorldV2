package kr.modernworld.modernworldv2.user.application.event;

import kr.modernworld.modernworldv2.user.domain.alarm.AlarmTitle;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AlarmEvent extends ApplicationEvent {

  private final Long userNo;
  private final String message;
  private final AlarmTitle title;

  public AlarmEvent(Object source, Long userNo, String message, AlarmTitle title) {
    super(source);
    this.userNo = userNo;
    this.message = message;
    this.title = title;
  }
}
