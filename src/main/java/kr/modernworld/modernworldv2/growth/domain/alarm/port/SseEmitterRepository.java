package kr.modernworld.modernworldv2.growth.domain.alarm.port;

import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface SseEmitterRepository {

  SseEmitter create(String emitterKey, SseEmitter emitter);

  Map<String, SseEmitter> getAllByUserNo(String userNo);

  void deleteAll(String userNo);

}
