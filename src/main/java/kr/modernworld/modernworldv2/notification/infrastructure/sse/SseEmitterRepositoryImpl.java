package kr.modernworld.modernworldv2.notification.infrastructure.sse;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import kr.modernworld.modernworldv2.notification.application.sse.port.SseEmitterRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepositoryImpl implements SseEmitterRepository {

  private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();

  @Override
  public SseEmitter create(String emitterKey, SseEmitter emitter) {
    userEmitters.put(emitterKey, emitter);
    return emitter;
  }

  @Override
  public Optional<SseEmitter> findOne(String userNo) {
    SseEmitter sseEmitter = userEmitters.get(userNo);

    if (sseEmitter == null) {
      return Optional.empty();
    }

    return Optional.of(sseEmitter);
  }

  @Override
  public void deleteById(String id) {
    userEmitters.remove(id);
  }
}
