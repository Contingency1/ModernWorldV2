package kr.modernworld.modernworldv2.user.infrastructure.repository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.modernworld.modernworldv2.global.exception.SessionNotFoundException;
import kr.modernworld.modernworldv2.user.domain.port.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository("LoginSessionRepository")
@RequiredArgsConstructor
public class LoginSessionRepository implements SessionRepository {

  private final HttpServletRequest request;

  @Override
  public void save(String sessionKey, String value) {
    request.getSession(true).setAttribute(sessionKey, value);
  }

  @Override
  public String findValue(String sessionKey) {
    HttpSession session = request.getSession(false);

    if (session == null) {
      throw new SessionNotFoundException("Session not found.");
    }

    String value = (String) session.getAttribute(sessionKey);

    if (value == null) {
      throw new IllegalArgumentException(
          "This session Key has no value. Session Key: " + sessionKey);
    }

    return value;
  }

  @Override
  public void invalidate() {
    HttpSession session = request.getSession(false);

    if (session == null) {
      return;
    }

    session.invalidate();
  }
}
