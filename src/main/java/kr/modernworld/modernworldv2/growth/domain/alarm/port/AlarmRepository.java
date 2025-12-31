package kr.modernworld.modernworldv2.growth.domain.alarm.port;

import kr.modernworld.modernworldv2.growth.domain.alarm.Alarm;

public interface AlarmRepository {

  Alarm save(Alarm alarm);

  void delete(Alarm alarm);

}
