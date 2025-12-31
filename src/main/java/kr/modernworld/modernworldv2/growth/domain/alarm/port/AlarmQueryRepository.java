package kr.modernworld.modernworldv2.growth.domain.alarm.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.growth.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.growth.presentation.alarm.dto.res.AlarmResponseDTO;

public interface AlarmQueryRepository {

  PageResponseDTO<AlarmResponseDTO> getAllAlarmsWithMeta(Long userNo, Long page, Long take,
      OrderBy orderBy);

  Optional<Alarm> findOneByNo(Long alarmNo);

}
