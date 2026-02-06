package kr.modernworld.modernworldv2.notification.application.sse.port;

import java.util.Optional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterRepository {

  SseEmitter create(String emitterKey, SseEmitter emitter);

  Optional<SseEmitter> findOne(String userNo);

  void delete(String emitterKey, SseEmitter emitter);

}
