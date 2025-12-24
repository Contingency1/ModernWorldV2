package kr.modernworld.modernworldv2.user.infrastructure.repository.rsp;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QRspGameRecordJPAEntity.rspGameRecordJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Instant;
import java.util.List;
import kr.modernworld.modernworldv2.user.domain.rsp.port.RSPQueryRepository;
import kr.modernworld.modernworldv2.user.presentation.rsp.dto.res.RSPResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RSPQueryRepositoryImpl implements RSPQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<RSPResponseDTO> findAllByUserNoAndDate(Long userNo, Instant start,
      Instant end) {
    return queryFactory
        .select(Projections.constructor(RSPResponseDTO.class,
            rspGameRecordJPAEntity.no,
            rspGameRecordJPAEntity.user.no,
            rspGameRecordJPAEntity.userChoice,
            rspGameRecordJPAEntity.computerChoice,
            rspGameRecordJPAEntity.result,
            rspGameRecordJPAEntity.createdAt
        ))
        .from(rspGameRecordJPAEntity)
        .where(
            rspGameRecordJPAEntity.user.no.eq(userNo),
            rspGameRecordJPAEntity.createdAt.goe(start),
            rspGameRecordJPAEntity.createdAt.lt(end)
        )
        .orderBy(rspGameRecordJPAEntity.createdAt.desc())
        .fetch();
  }
}
