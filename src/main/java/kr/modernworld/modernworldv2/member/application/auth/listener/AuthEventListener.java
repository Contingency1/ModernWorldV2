package kr.modernworld.modernworldv2.member.application.auth.listener;

import kr.modernworld.modernworldv2.member.application.auth.event.LoginFailEvent;
import kr.modernworld.modernworldv2.member.application.auth.event.LoginSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthEventListener {

  @EventListener
  public void loginSuccess(LoginSuccessEvent event) {
    log.info("UserNo {} login success.", event.userNo());
  }

  @EventListener
  public void loginFail(LoginFailEvent event) {
    log.warn("Login failed. Invalid state. {}", event.state());
  }
}
