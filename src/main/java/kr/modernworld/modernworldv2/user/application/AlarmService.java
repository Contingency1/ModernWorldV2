package kr.modernworld.modernworldv2.user.application;

import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.user.domain.alarm.AlarmTitle;
import kr.modernworld.modernworldv2.user.domain.port.alarm.AlarmQueryRepository;
import kr.modernworld.modernworldv2.user.domain.port.alarm.AlarmRepository;
import kr.modernworld.modernworldv2.user.presentation.alarm.dto.req.AlarmRequestDTO;
import kr.modernworld.modernworldv2.user.presentation.alarm.dto.res.AlarmResponseDTO;
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
  public PageResponseDTO<AlarmResponseDTO> getAllAlarms(Long userNo, AlarmRequestDTO query) {
    return alarmQueryRepository.getAllAlarmsWithMeta(userNo, query.page(), query.take(),
        query.orderBy());
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
