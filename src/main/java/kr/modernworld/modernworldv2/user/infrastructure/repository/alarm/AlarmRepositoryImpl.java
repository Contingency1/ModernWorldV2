package kr.modernworld.modernworldv2.user.infrastructure.repository.alarm;

import kr.modernworld.modernworldv2.user.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.user.domain.port.alarm.AlarmRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.AlarmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {

  private final AlarmJPARepository alarmJPARepository;
  private final AlarmMapper alarmMapper;

  @Override
  public void save(Alarm alarm) {
    alarmJPARepository.save(alarmMapper.toEntity(alarm));
  }

  @Override
  public void delete(Alarm alarm) {
    alarmJPARepository.delete(alarmMapper.toEntity(alarm));
  }
}
