package kr.modernworld.modernworldv2.member.application.auth.event;

import org.springframework.context.ApplicationEvent;

public class LoginFailEvent extends ApplicationEvent {

  public LoginFailEvent(Object source) {
    super(source);
  }
}
