package kr.modernworld.modernworldv2.user.domain.port.user;

import kr.modernworld.modernworldv2.user.domain.user.User;

public interface UserRepository {

  void updateCurrentPoint(Long userNo, Long newPoint);

  User save(User user);
}
