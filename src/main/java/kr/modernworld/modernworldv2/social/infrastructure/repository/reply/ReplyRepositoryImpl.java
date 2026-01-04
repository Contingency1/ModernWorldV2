package kr.modernworld.modernworldv2.social.infrastructure.repository.reply;

import static kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.QReplyJPAEntity.replyJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.social.domain.reply.Reply;
import kr.modernworld.modernworldv2.social.domain.reply.port.ReplyRepository;
import kr.modernworld.modernworldv2.social.infrastructure.mapper.ReplyMapper;
import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.ReplyJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

  private final ReplyJPARepository replyJPARepository;
  private final ReplyMapper replyMapper;
  private final JPAQueryFactory queryFactory;

  @Override
  public Reply save(Reply reply) {
    if (reply.getNo() == null) {
      ReplyJPAEntity entity = replyJPARepository.save(replyMapper.toEntity(reply));
      return replyMapper.toDomain(entity);
    }

    ReplyJPAEntity entity = replyJPARepository.findById(reply.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.REPLY_NOT_FOUND));

    replyMapper.updateEntityFromDomain(reply, entity);
    return replyMapper.toDomain(entity);
  }

  @Override
  public Optional<Reply> findOneForUpdate(Long replyNo) {
    ReplyJPAEntity entity = queryFactory
        .selectFrom(replyJPAEntity)
        .where(
            replyJPAEntity.no.eq(replyNo),
            replyJPAEntity.deletedAt.isNull())
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchFirst();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(replyMapper.toDomain(entity));
  }
}
