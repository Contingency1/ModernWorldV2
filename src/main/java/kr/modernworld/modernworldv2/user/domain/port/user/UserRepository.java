package kr.modernworld.modernworldv2.user.domain.port.user;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.user.User;

public interface UserRepository {

  Optional<User> findUserByUserNoForUpdate(Long userNo);

  User save(User user);
}
