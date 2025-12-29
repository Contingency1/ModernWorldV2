package kr.modernworld.modernworldv2.user.application.auth.event;

import org.springframework.context.ApplicationEvent;

public class LoginSuccessEvent extends ApplicationEvent {

  public LoginSuccessEvent(Object source) {
    super(source);
  }
}
