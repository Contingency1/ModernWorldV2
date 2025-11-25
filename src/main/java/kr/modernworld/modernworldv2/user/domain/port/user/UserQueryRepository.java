package kr.modernworld.modernworldv2.user.domain.port.user;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.user.User;

public interface UserQueryRepository {

  Optional<User> findByUniqueIdentifier(String uniqueIdentifier);

  Boolean exists(Long userNo);

}
