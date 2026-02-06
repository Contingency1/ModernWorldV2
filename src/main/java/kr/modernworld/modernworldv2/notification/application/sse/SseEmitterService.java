package kr.modernworld.modernworldv2.notification.application.sse;

import java.io.IOException;
import java.util.Optional;
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
    String emitterKey = String.valueOf(userNo);

    Optional<SseEmitter> oldOne = sseEmitterRepository.findOne(emitterKey);

    if (oldOne.isPresent()) {
      sseEmitterRepository.delete(emitterKey, oldOne.get());
      oldOne.get().complete();
    }

    SseEmitter emitter = new SseEmitter(SSE_TTL);

    setEmitter(emitter, emitterKey);

    sseEmitterRepository.create(emitterKey, emitter);

    sendEvent(userNo, emitter, "connected");

    return emitter;
  }

  private void setEmitter(SseEmitter emitter, String emitterKey) {
    emitter.onCompletion(() -> {
//      log.info("=== [성공] Emitter 객체 수명 종료됨 (GC 대기 상태 진입) ===");
      sseEmitterRepository.delete(emitterKey, emitter);
    });

    emitter.onTimeout(() -> {
//      log.info("=== [성공] Emitter 객체 수명 TTL에 따른 종료 (GC 대기 상태 진입) ===");
      sseEmitterRepository.delete(emitterKey, emitter);
    });

    emitter.onError((e) -> {
      log.error("userNo {}: SSE error", emitterKey, e);
      sseEmitterRepository.delete(emitterKey, emitter);
    });
  }

  public void send(Long userNo, SseEvent event) {
    Optional<SseEmitter> emitter = sseEmitterRepository.findOne(String.valueOf(userNo));

    emitter.ifPresent(sseEmitter -> sendEvent(userNo, sseEmitter, event));
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
      sseEmitterRepository.delete(String.valueOf(userNo), emitter);
    }
  }

}
