package kr.modernworld.modernworldv2.social.infrastructure.repository.comment;

import static kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.QCommentJPAEntity.commentJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.social.domain.comment.Comment;
import kr.modernworld.modernworldv2.social.domain.comment.port.CommentRepository;
import kr.modernworld.modernworldv2.social.infrastructure.mapper.CommentMapper;
import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.CommentJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

  private final CommentJPARepository commentJPARepository;
  private final CommentMapper commentMapper;
  private final JPAQueryFactory queryFactory;

  @Override
  public Comment save(Comment comment) {
    if (comment.getNo() == null) {
      CommentJPAEntity entity = commentJPARepository.save(commentMapper.toEntity(comment));

      return commentMapper.toDomain(entity);
    }
    CommentJPAEntity entity = commentJPARepository.findById(comment.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.COMMENT_NOT_FOUND));

    commentMapper.updateEntityFromDomain(comment, entity);

    return commentMapper.toDomain(entity);
  }

  @Override
  public Optional<Comment> findByNoForUpdate(Long commentNo) {
    CommentJPAEntity entity = queryFactory
        .selectFrom(commentJPAEntity)
        .where(
            commentJPAEntity.no.eq(commentNo),
            commentJPAEntity.deletedAt.isNull())
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchFirst();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(commentMapper.toDomain(entity));
  }
}
