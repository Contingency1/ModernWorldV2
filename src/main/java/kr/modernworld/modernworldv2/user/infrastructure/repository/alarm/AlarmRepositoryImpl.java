package kr.modernworld.modernworldv2.user.infrastructure.repository.alarm;

import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.user.domain.alarm.port.AlarmRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.AlarmMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.AlarmJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {

  private final AlarmJPARepository alarmJPARepository;
  private final AlarmMapper alarmMapper;

  @Override
  public Alarm save(Alarm alarm) {
    if (alarm.getNo() == null) {
      AlarmJPAEntity entity = alarmJPARepository.save(alarmMapper.toEntity(alarm));

      return alarmMapper.toDomain(entity);
    }

    AlarmJPAEntity entity = alarmJPARepository.findById(alarm.getNo())
        .orElseThrow(() -> new BusinessException(
            BusinessErrorCode.ALARM_NOT_FOUND));

    alarmMapper.updateEntityFromDomain(alarm, entity);

    return alarmMapper.toDomain(entity);
  }

  @Override
  public void delete(Alarm alarm) {
    alarmJPARepository.delete(alarmMapper.toEntity(alarm));
  }
}
