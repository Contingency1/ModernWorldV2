package kr.modernworld.modernworldv2.notification.infrastructure.sse;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kr.modernworld.modernworldv2.notification.application.sse.port.SseEmitterRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class SseEmitterRepositoryImpl implements SseEmitterRepository {

  private static final ConcurrentHashMap<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  @Override
  public SseEmitter create(String emitterKey, SseEmitter emitter) {
    emitters.put(emitterKey, emitter);
    return emitter;
  }

  @Override
  public Map<String, SseEmitter> getAllByUserNo(String userNo) {
    Map<String, SseEmitter> result = new HashMap<>();

    emitters.forEach((k, v) -> {
      if (k.startsWith(userNo + "_")) {
        result.put(k, v);
      }
    });

    return result;
  }

  @Override
  public void deleteAll(String userNo) {
    for (String name : emitters.keySet()) {
      if (name.startsWith(userNo + "_")) {
        emitters.remove(name);
      }
    }
  }
}
