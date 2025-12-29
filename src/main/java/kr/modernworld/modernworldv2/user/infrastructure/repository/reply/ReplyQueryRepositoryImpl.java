package kr.modernworld.modernworldv2.user.infrastructure.repository.reply;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QReplyJPAEntity.replyJPAEntity;
import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.OrderBy;
import kr.modernworld.modernworldv2.user.domain.reply.port.ReplyQueryRepository;
import kr.modernworld.modernworldv2.user.presentation.reply.dto.res.ReplyResponseDTO;
import kr.modernworld.modernworldv2.user.presentation.reply.dto.res.ReplyUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyQueryRepositoryImpl implements ReplyQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<ReplyResponseDTO> findOne(Long replyNo) {

    ReplyResponseDTO response = queryFactory
        .select(
            Projections.constructor(
                ReplyResponseDTO.class,
                replyJPAEntity.no,
                replyJPAEntity.comment.no,
                replyJPAEntity.content,
                replyJPAEntity.createdAt,
                Projections.constructor(
                    ReplyUserDTO.class,
                    replyJPAEntity.user.no,
                    replyJPAEntity.user.nickname
                )
            ))
        .from(replyJPAEntity)
        .leftJoin(replyJPAEntity.user, userJPAEntity)
        .leftJoin(replyJPAEntity.comment)
        .where(
            replyJPAEntity.no.eq(replyNo),
            replyJPAEntity.deletedAt.isNull(),
            replyJPAEntity.comment.deletedAt.isNull())
        .fetchOne();

    if (response == null) {
      return Optional.empty();
    }

    return Optional.of(response);
  }

  @Override
  public Long count(Long commentNo) {
    return queryFactory
        .select(replyJPAEntity.count())
        .from(replyJPAEntity)
        .where(replyJPAEntity.comment.no.eq(commentNo), replyJPAEntity.deletedAt.isNull())
        .fetchOne();
  }

  @Override
  public List<ReplyResponseDTO> findByCommentNo(Long commentNo, Long skip, Long take,
      OrderBy orderBy) {
    return queryFactory
        .select(
            Projections.constructor(
                ReplyResponseDTO.class,
                replyJPAEntity.no,
                replyJPAEntity.comment.no,
                replyJPAEntity.content,
                replyJPAEntity.createdAt,
                Projections.constructor(
                    ReplyUserDTO.class,
                    replyJPAEntity.user.no,
                    replyJPAEntity.user.nickname
                )
            ))
        .from(replyJPAEntity)
        .where(
            replyJPAEntity.comment.no.eq(commentNo),
            replyJPAEntity.deletedAt.isNull())
        .orderBy(
            orderBy == OrderBy.ASC ?
                replyJPAEntity.createdAt.asc()
                : replyJPAEntity.createdAt.desc())
        .offset(skip)
        .limit(take)
        .fetch();
  }
}
