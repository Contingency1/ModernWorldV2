package kr.modernworld.modernworldv2.social.infrastructure.repository.rsp;

import static kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.QRspGameRecordJPAEntity.rspGameRecordJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Instant;
import java.util.List;
import kr.modernworld.modernworldv2.social.application.rsp.dto.GetRSPDTO;
import kr.modernworld.modernworldv2.social.application.rsp.port.RSPQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RSPQueryRepositoryImpl implements RSPQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<GetRSPDTO> findAllByUserNoAndDate(Long userNo, Instant start,
      Instant end) {
    return queryFactory
        .select(Projections.constructor(GetRSPDTO.class,
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
