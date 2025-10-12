package kr.modernworld.modernworldv2.user.infrastructure.repository;

import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.port.user.UserRepository;
import kr.modernworld.modernworldv2.user.domain.user.User;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.LegendMapper;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.TokenMapper;
import kr.modernworld.modernworldv2.user.infrastructure.mapper.UserMapper;
import kr.modernworld.modernworldv2.user.infrastructure.persistence.entity.UserJPAEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserJPARepository userJPARepository;
  private final UserMapper userMapper;
  private final TokenMapper tokenMapper;
  private final LegendMapper legendMapper;

  @Override
  public Optional<User> findByUniqueIdentifier(String uniqueIdentifier) {
    return userJPARepository.findByUniqueIdentifier(uniqueIdentifier).map(userMapper::toDomain);
  }

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

}
