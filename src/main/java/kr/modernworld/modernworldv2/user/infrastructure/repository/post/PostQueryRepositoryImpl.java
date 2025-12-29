package kr.modernworld.modernworldv2.user.infrastructure.repository.post;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QPostJPAEntity.postJPAEntity;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.user.domain.post.port.PostQueryRepository;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QUserJPAEntity;
import kr.modernworld.modernworldv2.user.presentation.post.dto.res.PostResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.post.dto.res.PostUserInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<PostResponseDTO> findAll(Long userNo,
      SenderReceiverNoField senderReceiverNoField,
      OrderBy orderBy) {
    QUserJPAEntity sender = new QUserJPAEntity("sender");
    QUserJPAEntity receiver = new QUserJPAEntity("receiver");

    return queryFactory
        .select(getSelect(sender, receiver))
        .from(postJPAEntity)
        .leftJoin(postJPAEntity.sender, sender)
        .leftJoin(postJPAEntity.receiver, receiver)
        .where(filterPosts(userNo, senderReceiverNoField))
        .orderBy(getOrderBy(orderBy))
        .fetch();
  }

  @Override
  public Optional<PostResponseDTO> findOne(Long userNo, Long postNo) {
    QUserJPAEntity sender = new QUserJPAEntity("sender");
    QUserJPAEntity receiver = new QUserJPAEntity("receiver");

    PostResponseDTO response = queryFactory
        .select(getSelect(sender, receiver))
        .from(postJPAEntity)
        .leftJoin(postJPAEntity.sender, sender)
        .leftJoin(postJPAEntity.receiver, receiver)
        .where(postJPAEntity.no.eq(postNo), filterPosts(userNo, null))
        .fetchFirst();

    if (response == null) {
      return Optional.empty();
    }

    return Optional.of(response);
  }

  private static ConstructorExpression<PostResponseDTO> getSelect(QUserJPAEntity sender,
      QUserJPAEntity receiver) {
    return Projections.constructor(
        PostResponseDTO.class,
        postJPAEntity.no,
        postJPAEntity.content,
        postJPAEntity.createdAt,
        postJPAEntity.check,
        Projections.constructor(
            PostUserInfoDTO.class, sender.no, sender.nickname
        ),
        Projections.constructor(
            PostUserInfoDTO.class, receiver.no, receiver.nickname
        )
    );
  }

  private Predicate filterPosts(Long userNo, SenderReceiverNoField type) {
    BooleanExpression isSender = postJPAEntity.sender.no.eq(userNo)
        .and(postJPAEntity.senderDelete.isFalse());
    BooleanExpression isReceiver = postJPAEntity.receiver.no.eq(userNo)
        .and(postJPAEntity.receiverDelete.isFalse());

    if (type == SenderReceiverNoField.SENDER_NO) {
      return isSender;
    }

    if (type == SenderReceiverNoField.RECEIVER_NO) {
      return isReceiver;
    }

    return isSender.or(isReceiver);
  }

  private static OrderSpecifier<Instant> getOrderBy(OrderBy orderBy) {
    if (orderBy == OrderBy.ASC) {
      return postJPAEntity.createdAt.asc();
    }

    return postJPAEntity.createdAt.desc();
  }
}
