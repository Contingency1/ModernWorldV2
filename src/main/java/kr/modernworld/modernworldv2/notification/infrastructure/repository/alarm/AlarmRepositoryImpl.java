package kr.modernworld.modernworldv2.notification.infrastructure.repository.alarm;


import static kr.modernworld.modernworldv2.notification.infrastructure.entity.QAlarmJPAEntity.alarmJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.notification.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.notification.domain.alarm.port.AlarmRepository;
import kr.modernworld.modernworldv2.notification.infrastructure.entity.AlarmJPAEntity;
import kr.modernworld.modernworldv2.notification.infrastructure.mapper.AlarmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepository {

  private final AlarmJPARepository alarmJPARepository;
  private final AlarmMapper alarmMapper;
  private final JPAQueryFactory queryFactory;

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

  @Override
  public Optional<Alarm> findOneByNoForUpdate(Long alarmNo) {
    AlarmJPAEntity entity = queryFactory
        .select(alarmJPAEntity)
        .from(alarmJPAEntity)
        .where(alarmJPAEntity.no.eq(alarmNo))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(alarmMapper.toDomain(entity));
  }
}
