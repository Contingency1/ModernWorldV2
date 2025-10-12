package kr.modernworld.modernworldv2.user.application.listener;

import kr.modernworld.modernworldv2.user.application.event.LoginFailEvent;
import kr.modernworld.modernworldv2.user.application.event.LoginSuccessEvent;
import kr.modernworld.modernworldv2.user.domain.port.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthEventListener {

  private final SessionRepository sessionRepository;

  @EventListener
  public void loginSuccess(LoginSuccessEvent event) {
    sessionRepository.invalidate();
  }

  @EventListener
  public void loginFail(LoginFailEvent event) {
    sessionRepository.invalidate();
  }
}
