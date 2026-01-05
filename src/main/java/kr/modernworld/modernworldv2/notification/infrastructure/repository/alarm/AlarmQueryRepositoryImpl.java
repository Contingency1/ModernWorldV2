package kr.modernworld.modernworldv2.notification.infrastructure.repository.alarm;


import static kr.modernworld.modernworldv2.notification.infrastructure.entity.QAlarmJPAEntity.alarmJPAEntity;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.dto.PageMetaDTO;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.notification.application.alarm.dto.AlarmDTO;
import kr.modernworld.modernworldv2.notification.application.alarm.port.AlarmQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlarmQueryRepositoryImpl implements AlarmQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  @Transactional(readOnly = true)
  public PageResponseDTO<AlarmDTO> getAllAlarmsWithMeta(Long userNo, Long page, Long take,
      OrderBy orderBy) {

    List<AlarmDTO> data = queryFactory
        .select(Projections.constructor(AlarmDTO.class,
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


  private OrderSpecifier<?> createOrderSpecifier(OrderBy orderBy) {
    if (orderBy == OrderBy.ASC) {
      return alarmJPAEntity.createdAt.asc();
    }

    return alarmJPAEntity.createdAt.desc();
  }
}
