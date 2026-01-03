package kr.modernworld.modernworldv2.growth.application.sse;

import java.io.IOException;
import java.util.Map;
import kr.modernworld.modernworldv2.notification.application.sse.port.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
@RequiredArgsConstructor
public class SseEmitterService {

  private final SseEmitterRepository sseEmitterRepository;
  private static final Long SSE_TTL = 30 * 60 * 1000L;

  public SseEmitter connect(Long userNo) {
    String emitterName = userNo.toString() + "_" + System.currentTimeMillis();
    SseEmitter emitter = new SseEmitter(SSE_TTL);

    emitter.onCompletion(() -> sseEmitterRepository.deleteAll(String.valueOf(userNo)));
    emitter.onTimeout(() -> sseEmitterRepository.deleteAll(String.valueOf(userNo)));
    emitter.onError((e) -> sseEmitterRepository.deleteAll(String.valueOf(userNo)));

    sseEmitterRepository.create(emitterName, emitter);

    sendEvent(userNo, emitter, "connected");

    return emitter;
  }

  public void send(Long userNo, SseEvent event) {
    Map<String, SseEmitter> emitters = sseEmitterRepository.getAllByUserNo(
        String.valueOf(userNo));

    for (SseEmitter emitter : emitters.values()) {
      sendEvent(userNo, emitter, event);
    }

  }

  private void sendEvent(Long userNo, SseEmitter emitter, Object event) {
    try {
      emitter.send(SseEmitter.event()
          .id("1")
          .name("message")
          .data(event));
    } catch (IOException e) {
      log.error("SSE connection Error: {}", e.getMessage());
      sseEmitterRepository.deleteAll(String.valueOf(userNo));
      throw new RuntimeException(e);
    }
  }

}
