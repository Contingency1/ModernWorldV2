package kr.modernworld.modernworldv2.user.domain.port.user;

import java.util.Optional;
import kr.modernworld.modernworldv2.user.domain.user.User;

public interface UserQueryRepository {

  Optional<User> findUserCurrentPointByNo(Long userNo);

  Optional<User> findByUniqueIdentifier(String uniqueIdentifier);

}
