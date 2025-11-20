package kr.modernworld.modernworldv2.user.infrastructure.repository;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.port.user.UserQueryRepository;
import kr.modernworld.modernworldv2.user.domain.user.User;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryRepositoryImpl implements UserQueryRepository {

  private final UserJPARepository userJPARepository;
  private final UserMapper userMapper;
  private final JPAQueryFactory queryFactory;

  @Override
  public Optional<User> findByUniqueIdentifier(String uniqueIdentifier) {
    return userJPARepository.findByUniqueIdentifier(uniqueIdentifier).map(userMapper::toDomain);
  }

  @Override
  public Boolean exists(Long userNo) {
    Integer one = queryFactory
        .selectOne()
        .from(userJPAEntity)
        .where(userJPAEntity.no.eq(userNo))
        .fetchFirst();

    return one != null;
  }
}
