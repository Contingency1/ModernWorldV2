package kr.modernworld.modernworldv2.member.infrastructure.repository.user.socialtoken;

import static kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QTokenJPAEntity.tokenJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.member.domain.user.socialtoken.port.SocialTokenQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SocialTokenQueryRepositoryImpl implements SocialTokenQueryRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<String> findSocialAccessTokenByUserNo(Long userNo) {
    String token = queryFactory
        .select(tokenJPAEntity.socialAccess)
        .from(tokenJPAEntity)
        .where(tokenJPAEntity.user.no.eq(userNo))
        .fetchOne();

    if (token == null) {
      return Optional.empty();
    }

    return Optional.of(token);
  }
}
