package kr.modernworld.modernworldv2.user.infrastructure.repository.neighbor;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QNeighborJPAEntity.neighborJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.user.domain.neighbor.Neighbor;
import kr.modernworld.modernworldv2.user.domain.neighbor.port.NeighborRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.NeighborMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.NeighborJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NeighborRepositoryImpl implements NeighborRepository {

  private final NeighborJPARepository neighborJPARepository;
  private final JPAQueryFactory queryFactory;
  private final NeighborMapper neighborMapper;
  private final EntityManager entityManager;

  @Override
  public Neighbor save(Neighbor neighbor) {
    if (neighbor.getNo() == null) {
      NeighborJPAEntity entity = neighborJPARepository.save(neighborMapper.toEntity(neighbor));
      return neighborMapper.toDomain(entity);
    }

    NeighborJPAEntity entity = neighborJPARepository.findById(neighbor.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.NEIGHBOR_NOT_FOUND));

    neighborMapper.updateEntityFromDomain(neighbor, entity);
    return neighborMapper.toDomain(entity);
  }

  @Override
  public Long delete(Long neighborNo, Long userNo) {
    Long count = queryFactory
        .delete(neighborJPAEntity)
        .where(
            neighborJPAEntity.no.eq(neighborNo),
            neighborJPAEntity.sender.no.eq(userNo)
                .or(neighborJPAEntity.receiver.no.eq(userNo)))
        .execute();

    entityManager.clear();

    return count;
  }

  @Override
  public Optional<Neighbor> findOneThatStatusIsFalseForUpdate(Long senderNo,
      Long receiverNo) {
    NeighborJPAEntity entity = queryFactory
        .selectFrom(neighborJPAEntity)
        .where(
            neighborJPAEntity.sender.no.eq(receiverNo),
            neighborJPAEntity.receiver.no.eq(senderNo),
            neighborJPAEntity.status.eq(false))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchFirst();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(neighborMapper.toDomain(entity));
  }

  @Override
  public Optional<Neighbor> findByNoAndReceiverNoForUpdate(Long neighborNo, Long receiverNo) {
    NeighborJPAEntity entity = queryFactory
        .selectFrom(neighborJPAEntity)
        .where(
            neighborJPAEntity.no.eq(neighborNo),
            neighborJPAEntity.receiver.no.eq(receiverNo))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchFirst();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(neighborMapper.toDomain(entity));
  }

}
