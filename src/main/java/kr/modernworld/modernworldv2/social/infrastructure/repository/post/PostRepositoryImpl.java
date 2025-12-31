package kr.modernworld.modernworldv2.social.infrastructure.repository.post;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QPostJPAEntity.postJPAEntity;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.social.domain.post.Post;
import kr.modernworld.modernworldv2.social.domain.post.port.PostRepository;
import kr.modernworld.modernworldv2.member.infrastructure.mapper.PostMapper;
import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.PostJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

  private final PostJPARepository postJPARepository;
  private final JPAQueryFactory queryFactory;
  private final PostMapper postMapper;

  @Override
  public Post save(Post post) {
    if (post.getNo() == null) {
      PostJPAEntity entity = postJPARepository.save(postMapper.toEntity(post));

      return postMapper.toDomain(entity);
    }

    PostJPAEntity entity = postJPARepository.findById(post.getNo())
        .orElseThrow(() -> new BusinessException(
            BusinessErrorCode.POST_NOT_FOUND));

    postMapper.updateEntityFromDomain(post, entity);

    return postMapper.toDomain(entity);
  }

  @Override
  public Optional<Post> findOneByUserNoAndPostNoForUpdate(Long userNo, Long postNo) {
    PostJPAEntity entity = queryFactory
        .selectFrom(postJPAEntity)
        .leftJoin(postJPAEntity.receiver)
        .leftJoin(postJPAEntity.sender)
        .where(postJPAEntity.no.eq(postNo), filterPosts(userNo))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(postMapper.toDomain(entity));
  }

  private Predicate filterPosts(Long userNo) {
    BooleanExpression isSender = postJPAEntity.sender.no.eq(userNo)
        .and(postJPAEntity.senderDelete.isFalse());

    BooleanExpression isReceiver = postJPAEntity.receiver.no.eq(userNo)
        .and(postJPAEntity.receiverDelete.isFalse());

    return isSender.or(isReceiver);
  }
}
