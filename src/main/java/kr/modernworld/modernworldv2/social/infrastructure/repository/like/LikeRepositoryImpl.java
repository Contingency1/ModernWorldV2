package kr.modernworld.modernworldv2.social.infrastructure.repository.like;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QLikeJPAEntity.likeJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.social.domain.like.Like;
import kr.modernworld.modernworldv2.social.domain.like.port.LikeRepository;
import kr.modernworld.modernworldv2.member.infrastructure.mapper.LikeMapper;
import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.LikeJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {

  private final LikeJPARepository likeJPARepository;
  private final LikeMapper likeMapper;
  private final JPAQueryFactory queryFactory;

  @Override
  public Like save(Like like) {
    // 애초에 update 할 일 자체가 없다.
    LikeJPAEntity entity = likeJPARepository.save(likeMapper.toEntity(like));
    return likeMapper.toDomain(entity);
  }

  @Override
  public Optional<Like> findBySenderNoAndReceiverNoForUpdate(Long senderNo, Long receiverNo) {
    LikeJPAEntity entity = queryFactory
        .selectFrom(likeJPAEntity)
        .where(
            likeJPAEntity.sender.no.eq(senderNo),
            likeJPAEntity.receiver.no.eq(receiverNo)
        )
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchFirst();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(likeMapper.toDomain(entity));
  }

  @Override
  public void delete(Like like) {
    likeJPARepository.deleteById(like.getNo());
  }
}
