package kr.modernworld.modernworldv2.social.infrastructure.repository.neighbor;

import static kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.QNeighborJPAEntity.neighborJPAEntity;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserAchievementJPAEntity;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserJPAEntity;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.NeighborDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.NeighborUserDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.GetNeighborDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.GetNeighborUserDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.NeighborUserAchievementDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.dto.get.UserAchievementWrapperDTO;
import kr.modernworld.modernworldv2.social.application.neighbor.port.NeighborQueryRepository;
import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.NeighborJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NeighborQueryRepositoryImpl implements NeighborQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Boolean isAlreadyNeighbor(Long senderNo, Long receiverNo) {
    Integer exist = queryFactory
        .selectOne()
        .from(neighborJPAEntity)
        .where(filterStatusTrue(senderNo, receiverNo)
            .or(filterStatusTrue(receiverNo, senderNo))
        )
        .fetchFirst();

    return exist != null;
  }

  @Override
  public List<GetNeighborDTO> findAll(Long userNo, Long skip, Long take, OrderBy orderBy,
      Boolean status, SenderReceiverNoField senderReceiver) {
    List<NeighborJPAEntity> neighbors = queryFactory
        .selectFrom(neighborJPAEntity)
        .leftJoin(neighborJPAEntity.sender).fetchJoin()
        .leftJoin(neighborJPAEntity.receiver).fetchJoin()
        .where(
            eqStatus(status),
            filterMyNeighbor(userNo, senderReceiver)
        )
        .orderBy(orderBy(orderBy))
        .offset(skip)
        .limit(take)
        .fetch();

    return neighbors.stream()
        .map(neighbor -> mapToDTO(neighbor, userNo))
        .toList();
  }

  @Override
  public Boolean findOneRequestThatStatusIsFalse(Long senderNo, Long receiverNo) {
    Integer exist = queryFactory
        .selectOne()
        .from(neighborJPAEntity)
        .where(neighborJPAEntity.sender.no.eq(senderNo),
            neighborJPAEntity.receiver.no.eq(receiverNo),
            neighborJPAEntity.status.eq(false))
        .fetchFirst();

    return exist != null;
  }

  @Override
  public Optional<NeighborDTO> findOneByNo(Long neighborNo) {
    NeighborDTO response = queryFactory
        .select(Projections.constructor(
            NeighborDTO.class,
            neighborJPAEntity.no,
            Projections.constructor(
                NeighborUserDTO.class,
                neighborJPAEntity.sender.no,
                neighborJPAEntity.sender.nickname
            ),
            Projections.constructor(
                NeighborUserDTO.class,
                neighborJPAEntity.receiver.no,
                neighborJPAEntity.receiver.nickname
            ),
            neighborJPAEntity.createdAt,
            neighborJPAEntity.status
        ))
        .from(neighborJPAEntity)
        .where(neighborJPAEntity.no.eq(neighborNo))
        .fetchFirst();

    if (response == null) {
      return Optional.empty();
    }

    return Optional.of(response);
  }

  @Override
  public Boolean isPresent(Long neighborNo) {
    Integer entity = queryFactory
        .selectOne()
        .from(neighborJPAEntity)
        .where(neighborJPAEntity.no.eq(neighborNo))
        .fetchFirst();

    return entity != null;
  }

  @Override
  public Long count(Long userNo, Boolean status, SenderReceiverNoField senderReceiver) {
    return queryFactory
        .select(neighborJPAEntity.count())
        .from(neighborJPAEntity)
        .where(
            eqStatus(status),
            filterMyNeighbor(userNo, senderReceiver)
        )
        .fetchOne();

  }


  private static BooleanExpression filterStatusTrue(Long senderNo, Long receiverNo) {
    BooleanExpression first = neighborJPAEntity.sender.no.eq(senderNo)
        .and(neighborJPAEntity.receiver.no.eq(receiverNo));

    BooleanExpression second = neighborJPAEntity.sender.no.eq(receiverNo)
        .and(neighborJPAEntity.receiver.no.eq(senderNo));

    BooleanExpression status = neighborJPAEntity.status.eq(true);

    return (first.or(second)).and(status);

  }

  private BooleanExpression eqStatus(Boolean status) {
    if (status == null) {
      return null;
    }
    return neighborJPAEntity.status.eq(status);
  }

  private BooleanExpression filterMyNeighbor(Long userNo, SenderReceiverNoField type) {
    if (type == SenderReceiverNoField.SENDER_NO) {
      return neighborJPAEntity.sender.no.eq(userNo);
    }

    if (type == SenderReceiverNoField.RECEIVER_NO) {
      return neighborJPAEntity.receiver.no.eq(userNo);
    }

    return neighborJPAEntity.sender.no.eq(userNo)
        .or(neighborJPAEntity.receiver.no.eq(userNo));
  }

  private OrderSpecifier<?> orderBy(OrderBy orderBy) {
    if (orderBy == OrderBy.ASC) {
      return neighborJPAEntity.createdAt.asc();
    }
    return neighborJPAEntity.createdAt.desc();
  }

  private GetNeighborDTO mapToDTO(NeighborJPAEntity entity, Long userNo) {
    UserJPAEntity targetUser;

    if (entity.getSender().getNo().equals(userNo)) {
      targetUser = entity.getReceiver();
    } else {
      targetUser = entity.getSender();
    }

    List<UserAchievementWrapperDTO> achievements = targetUser.getUserAchievements().stream()
        .filter(UserAchievementJPAEntity::getStatus)
        .map(userAchieve -> new UserAchievementWrapperDTO(
            new NeighborUserAchievementDTO(userAchieve.getAchievement().getName(),
                userAchieve.getAchievement().getLevel().toString())
        ))
        .toList();

    GetNeighborUserDTO userDTO = new GetNeighborUserDTO(
        targetUser.getNo(),
        targetUser.getNickname(),
        targetUser.getImage(),
        targetUser.getDescription(),
        achievements
    );

    return new GetNeighborDTO(
        entity.getNo(),
        entity.getCreatedAt(),
        entity.getStatus(),
        userDTO
    );
  }
}
