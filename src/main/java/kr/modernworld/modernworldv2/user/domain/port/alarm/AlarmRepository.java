package kr.modernworld.modernworldv2.user.domain.port.alarm;

import kr.modernworld.modernworldv2.user.domain.alarm.Alarm;

public interface AlarmRepository {

  void save(Alarm alarm);

  void delete(Alarm alarm);

}
