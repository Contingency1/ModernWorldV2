package kr.modernworld.modernworldv2.user.infrastructure.repository.alarm;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QAlarmJPAEntity.alarmJPAEntity;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.dto.PageMetaDTO;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.user.domain.alarm.Alarm;
import kr.modernworld.modernworldv2.user.domain.alarm.port.AlarmQueryRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.AlarmMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.AlarmJPAEntity;
import kr.modernworld.modernworldv2.user.presentation.alarm.dto.res.AlarmResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmQueryRepositoryImpl implements AlarmQueryRepository {

  private final JPAQueryFactory queryFactory;
  private final AlarmMapper alarmMapper;

  @Override
  @Transactional(readOnly = true)
  public PageResponseDTO<AlarmResponseDTO> getAllAlarmsWithMeta(Long userNo, Long page, Long take,
      OrderBy orderBy) {

    List<AlarmResponseDTO> data = queryFactory
        .select(Projections.constructor(AlarmResponseDTO.class,
            alarmJPAEntity.no,
            alarmJPAEntity.user.no,
            alarmJPAEntity.title,
            alarmJPAEntity.content,
            alarmJPAEntity.status,
            alarmJPAEntity.createdAt
        ))
        .from(alarmJPAEntity)
        .where(alarmJPAEntity.user.no.eq(userNo))
        .orderBy(createOrderSpecifier(orderBy))
        .offset((page - 1) * take)
        .limit(take)
        .fetch();

    Long totalCount = queryFactory
        .select(alarmJPAEntity.count())
        .from(alarmJPAEntity)
        .where(alarmJPAEntity.user.no.eq(userNo))
        .fetchOne();

    if (totalCount == null) {
      totalCount = 0L;
    }

    Long totalPage = (long) Math.ceil((double) totalCount / take);

    PageMetaDTO meta = new PageMetaDTO(page, take, totalCount, totalPage);

    return new PageResponseDTO<>(data, meta);
  }

  @Override
  public Optional<Alarm> findOneByNo(Long alarmNo) {
    AlarmJPAEntity entity = queryFactory
        .select(alarmJPAEntity)
        .from(alarmJPAEntity)
        .where(alarmJPAEntity.no.eq(alarmNo))
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(alarmMapper.toDomain(entity));
  }

  private OrderSpecifier<?> createOrderSpecifier(OrderBy orderBy) {
    if (orderBy == OrderBy.ASC) {
      return alarmJPAEntity.createdAt.asc();
    }

    return alarmJPAEntity.createdAt.desc();
  }
}
