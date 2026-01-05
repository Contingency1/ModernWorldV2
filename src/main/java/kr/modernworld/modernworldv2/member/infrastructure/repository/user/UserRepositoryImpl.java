package kr.modernworld.modernworldv2.member.infrastructure.repository.user;

import static kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.QUserJPAEntity.userJPAEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.modernworld.modernworldv2.global.error.BusinessErrorCode;
import kr.modernworld.modernworldv2.global.error.BusinessException;
import kr.modernworld.modernworldv2.member.domain.user.User;
import kr.modernworld.modernworldv2.member.domain.user.port.UserRepository;
import kr.modernworld.modernworldv2.member.infrastructure.mapper.UserMapper;
import kr.modernworld.modernworldv2.member.infrastructure.persistence.entity.UserJPAEntity;
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

    UserJPAEntity entity = userJPARepository.findById(user.getNo())
        .orElseThrow(() -> new BusinessException(BusinessErrorCode.USER_NOT_FOUND));

    userMapper.updateEntityFromDomain(user, entity);

    return userMapper.toDomain(entity);
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
