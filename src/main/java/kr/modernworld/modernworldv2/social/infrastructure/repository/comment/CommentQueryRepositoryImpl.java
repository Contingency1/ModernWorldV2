package kr.modernworld.modernworldv2.social.infrastructure.repository.comment;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QCommentJPAEntity.commentJPAEntity;
import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QReplyJPAEntity.replyJPAEntity;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.global.common.SenderReceiverNoField;
import kr.modernworld.modernworldv2.social.domain.comment.port.CommentQueryRepository;
import kr.modernworld.modernworldv2.social.presentation.comment.dto.res.CommentResponseDTO;
import kr.modernworld.modernworldv2.social.presentation.comment.dto.res.CommentUserDTO;
import kr.modernworld.modernworldv2.social.presentation.comment.dto.res.GetCommentReplyCountDTO;
import kr.modernworld.modernworldv2.social.presentation.comment.dto.res.GetCommentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  @Transactional(readOnly = true)
  public Optional<CommentResponseDTO> findByNo(Long commentNo) {
    CommentResponseDTO response = queryFactory
        .select(Projections.constructor(
            CommentResponseDTO.class,
            commentJPAEntity.no,
            commentJPAEntity.content,
            commentJPAEntity.createdAt,
            Projections.constructor(
                CommentUserDTO.class,
                commentJPAEntity.sender.no,
                commentJPAEntity.sender.nickname),
            Projections.constructor(
                CommentUserDTO.class,
                commentJPAEntity.receiver.no,
                commentJPAEntity.receiver.nickname)
        ))
        .from(commentJPAEntity)
        .leftJoin(commentJPAEntity.sender)
        .leftJoin(commentJPAEntity.receiver)
        .where(commentJPAEntity.no.eq(commentNo), commentJPAEntity.deletedAt.isNull())
        .fetchOne();

    if (response == null) {
      return Optional.empty();
    }

    return Optional.of(response);
  }

  @Override
  public Optional<GetCommentResponseDTO> findByNoWithReplyCount(Long commentNo) {
    GetCommentResponseDTO response = queryFactory
        .select(Projections.constructor(
            GetCommentResponseDTO.class,
            commentJPAEntity.no,
            commentJPAEntity.content,
            commentJPAEntity.createdAt,
            Projections.constructor(
                CommentUserDTO.class,
                commentJPAEntity.sender.no,
                commentJPAEntity.sender.nickname),
            Projections.constructor(
                CommentUserDTO.class,
                commentJPAEntity.receiver.no,
                commentJPAEntity.receiver.nickname),
            Projections.constructor(
                GetCommentReplyCountDTO.class,
                ExpressionUtils.as(
                    JPAExpressions
                        .select(replyJPAEntity.count())
                        .from(replyJPAEntity)
                        .where(replyJPAEntity.comment.eq(commentJPAEntity)
                            .and(replyJPAEntity.deletedAt.isNull())),
                    "count"
                )
            )
        ))
        .from(commentJPAEntity)
        .leftJoin(commentJPAEntity.sender)
        .leftJoin(commentJPAEntity.receiver)
        .where(commentJPAEntity.no.eq(commentNo), commentJPAEntity.deletedAt.isNull())
        .fetchOne();

    if (response == null) {
      return Optional.empty();
    }

    return Optional.of(response);
  }

  @Override
  public List<GetCommentResponseDTO> findAll(Long userNo, Long skip, Long take, OrderBy orderBy,
      SenderReceiverNoField type) {

    return queryFactory
        .select(Projections.constructor(
            GetCommentResponseDTO.class,
            commentJPAEntity.no,
            commentJPAEntity.content,
            commentJPAEntity.createdAt,
            Projections.constructor(
                CommentUserDTO.class,
                commentJPAEntity.sender.no,
                commentJPAEntity.sender.nickname
            ),
            Projections.constructor(
                CommentUserDTO.class,
                commentJPAEntity.receiver.no,
                commentJPAEntity.receiver.nickname
            ),
            Projections.constructor(
                GetCommentReplyCountDTO.class,
                ExpressionUtils.as(
                    JPAExpressions
                        .select(replyJPAEntity.count())
                        .from(replyJPAEntity)
                        .where(replyJPAEntity.comment.eq(commentJPAEntity)
                            .and(replyJPAEntity.deletedAt.isNull())),
                    "count"
                )
            )
        ))
        .from(commentJPAEntity)
        .leftJoin(commentJPAEntity.sender)
        .leftJoin(commentJPAEntity.receiver)
        .where(getWhere(userNo, type))
        .orderBy(orderBy == OrderBy.ASC ? commentJPAEntity.createdAt.asc()
            : commentJPAEntity.createdAt.desc())
        .offset(skip)
        .limit(take)
        .fetch();
  }

  @Override
  public Long countByUserNo(Long userNo, SenderReceiverNoField type) {
    return queryFactory
        .select(commentJPAEntity.count())
        .from(commentJPAEntity)
        .where(getWhere(userNo, type))
        .fetchOne();
  }

  private BooleanExpression getWhere(Long userNo, SenderReceiverNoField type) {
    BooleanExpression sender = commentJPAEntity.sender.no.eq(userNo)
        .and(commentJPAEntity.deletedAt.isNull());
    BooleanExpression receiver = commentJPAEntity.receiver.no.eq(userNo)
        .and(commentJPAEntity.deletedAt.isNull());

    if (type != null) {
      if (type.equals(SenderReceiverNoField.RECEIVER_NO)) {
        return receiver;
      }

      return sender;
    }

    return sender.or(receiver);
  }

  @Override
  public Boolean exists(Long commentNo) {
    Integer one = queryFactory
        .selectOne()
        .from(commentJPAEntity)
        .where(
            commentJPAEntity.no.eq(commentNo),
            commentJPAEntity.deletedAt.isNull())
        .fetchFirst();

    return one != null;
  }
}
