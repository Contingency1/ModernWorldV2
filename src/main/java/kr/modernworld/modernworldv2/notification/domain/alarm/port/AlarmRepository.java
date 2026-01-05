package kr.modernworld.modernworldv2.notification.domain.alarm.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.notification.domain.alarm.Alarm;

public interface AlarmRepository {

  Alarm save(Alarm alarm);

  void delete(Alarm alarm);

  Optional<Alarm> findOneByNoForUpdate(Long alarmNo);


}
