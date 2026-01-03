package kr.modernworld.modernworldv2.notification.application.alarm.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.notification.domain.alarm.Alarm;

public interface AlarmQueryRepository {

  PageResponseDTO<Alarm> getAllAlarmsWithMeta(Long userNo, Long page, Long take,
      OrderBy orderBy);

  Optional<Alarm> findOneByNo(Long alarmNo);

}
