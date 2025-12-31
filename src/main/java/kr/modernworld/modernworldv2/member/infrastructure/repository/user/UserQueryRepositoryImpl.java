package kr.modernworld.modernworldv2.member.infrastructure.repository.user;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import kr.modernworld.modernworldv2.member.domain.user.User;
import kr.modernworld.modernworldv2.member.domain.user.port.UserQueryRepository;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.UserMapper;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserJPAEntity;
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

  @Override
  public Optional<User> findOneByUserNo(Long userNo) {
    Optional<UserJPAEntity> entity = userJPARepository.findById(userNo);
    if (entity.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(userMapper.toDomain(entity.get()));
  }
}
