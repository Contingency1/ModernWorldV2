package kr.modernworld.modernworldv2.user.infrastructure.repository.present;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QPresentJPAEntity.presentJPAEntity;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.present.Present;
import kr.modernworld.modernworldv2.user.domain.present.port.PresentRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.PresentMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.PresentJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PresentRepositoryImpl implements PresentRepository {

  private final PresentJPARepository presentJPARepository;
  private final PresentMapper presentMapper;
  private final JPAQueryFactory queryFactory;

  @Override
  public Present save(Present present) {
    if (present.getNo() == null) {
      PresentJPAEntity save = presentJPARepository.save(presentMapper.toEntity(present));

      return presentMapper.toDomain(save);
    }

    PresentJPAEntity entity = presentJPARepository.findById(present.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.PRESENT_NOT_FOUND));

    presentMapper.updateEntityFromDomain(present, entity);
    return presentMapper.toDomain(entity);
  }

  @Override
  public Optional<Present> findByNoForUpdate(Long userNo, Long presentNo) {
    PresentJPAEntity entity = queryFactory
        .selectFrom(presentJPAEntity)
        .where(presentJPAEntity.no.eq(presentNo), filterPresent(userNo))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(presentMapper.toDomain(entity));
  }

  private BooleanExpression filterPresent(Long userNo) {
    BooleanExpression isSender = presentJPAEntity.sender.no.eq(userNo)
        .and(presentJPAEntity.senderDelete.isFalse());

    BooleanExpression isReceiver = presentJPAEntity.receiver.no.eq(userNo)
        .and(presentJPAEntity.receiverDelete.isFalse());

    return isSender.or(isReceiver);
  }
}
