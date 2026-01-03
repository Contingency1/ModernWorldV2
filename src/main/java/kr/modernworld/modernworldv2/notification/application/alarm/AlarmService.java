package kr.modernworld.modernworldv2.notification.application.alarm;

import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.notification.application.alarm.port.AlarmQueryRepository;
import kr.modernworld.modernworldv2.notification.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.notification.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.notification.domain.alarm.port.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {

  private final AlarmQueryRepository alarmQueryRepository;
  private final AlarmRepository alarmRepository;

  @Transactional
  public void create(Long userNo, AlarmTitle title, String message) {
    Alarm alarm = Alarm.init(userNo, title, message);

    alarmRepository.save(alarm);
  }

  @Transactional(readOnly = true)
  public PageResponseDTO<Alarm> getAllAlarms(Long userNo, Long page, Long take,
      OrderBy orderBy) {
    return alarmQueryRepository.getAllAlarmsWithMeta(userNo, page, take,
        orderBy);
  }

  @Transactional
  public void updateAlarmRead(Long userNo, Long alarmNo) {
    Alarm alarm = alarmQueryRepository.findOneByNo(alarmNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NO_SUCH_ALARM));

    try {
      alarm.validationUser(userNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.ALARM_NOT_FOUND, ", reason: " + e.getMessage());
    }

    alarm.makeStatusTrue();

    alarmRepository.save(alarm);
  }

  @Transactional
  public void deleteOne(Long userNo, Long alarmNo) {
    Alarm alarm = alarmQueryRepository.findOneByNo(alarmNo)
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NO_SUCH_ALARM));

    try {
      alarm.validationUser(userNo);
    } catch (IllegalStateException e) {
      throw new BusinessException(BusinessErrorCode.ALARM_NOT_FOUND, ", reason: " + e.getMessage());
    }

    alarmRepository.delete(alarm);
  }
}
