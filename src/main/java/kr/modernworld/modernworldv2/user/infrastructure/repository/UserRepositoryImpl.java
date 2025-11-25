package kr.modernworld.modernworldv2.user.infrastructure.repository;

import static kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.port.user.UserRepository;
import kr.modernworld.modernworldv2.user.domain.user.User;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.UserMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserRepository {

  private final UserJPARepository userJPARepository;
  private final JPAQueryFactory queryFactory;
  private final UserMapper userMapper;

  @Override
  public User save(User user) {
    if (user.getNo() == null) {
      return userMapper.toDomain(userJPARepository.save(userMapper.toEntity(user)));
    }

    UserJPAEntity userJPAEntity = userJPARepository.findById(user.getNo())
        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + user.getNo()));

    userMapper.updateUserEntityFromDomain(user, userJPAEntity);

    return userMapper.toDomain(userJPAEntity);
  }

  @Override
  public Optional<User> findUserByUserNoForUpdate(Long userNo) {
    UserJPAEntity entity = queryFactory
        .selectFrom(userJPAEntity)
        .where(userJPAEntity.no.eq(userNo))
        .setLockMode(LockModeType.PESSIMISTIC_WRITE)
        .fetchOne();

    if (entity == null) {
      return Optional.empty();
    }

    return Optional.of(userMapper.toDomain(entity));
  }

}
