package kr.modernworld.modernworldv2.member.domain.user.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.member.domain.user.User;

public interface UserQueryRepository {

  Optional<User> findByUniqueIdentifier(String uniqueIdentifier);

  Boolean exists(Long userNo);

  Optional<User> findOneByUserNo(Long userNo);

}
