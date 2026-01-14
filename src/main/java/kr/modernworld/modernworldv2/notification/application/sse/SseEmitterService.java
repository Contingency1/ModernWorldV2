package kr.modernworld.modernworldv2.notification.application.sse;

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
    sseEmitterRepository.deleteAll(String.valueOf(userNo));

    String emitterName = userNo + "_" + System.currentTimeMillis();
    SseEmitter emitter = new SseEmitter(SSE_TTL);

    emitter.onCompletion(() -> sseEmitterRepository.deleteById(emitterName));
    emitter.onTimeout(() -> sseEmitterRepository.deleteById(emitterName));
    emitter.onError((e) -> sseEmitterRepository.deleteById(emitterName));

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
      log.error("UserNo: {}, SSE connection Error: {}", userNo, e.getMessage());
      emitter.completeWithError(e);
    }
  }

}
