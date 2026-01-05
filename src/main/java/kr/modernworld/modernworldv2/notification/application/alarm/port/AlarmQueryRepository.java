package kr.modernworld.modernworldv2.notification.application.alarm.port;

import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.notification.application.alarm.dto.AlarmDTO;

public interface AlarmQueryRepository {

  PageResponseDTO<AlarmDTO> getAllAlarmsWithMeta(Long userNo, Long page, Long take,
      OrderBy orderBy);


}
