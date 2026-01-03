package kr.modernworld.modernworldv2.notification.presentation.sse;

import kr.modernworld.modernworldv2.growth.application.sse.SseEmitterService;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SseController {

  private final SseEmitterService sseEmitterService;

  @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> sse(@AuthenticationPrincipal TokenUserInfoDTO user) {

    return new ResponseEntity<>(sseEmitterService.connect(user.userNo()), HttpStatus.OK);
  }

}
