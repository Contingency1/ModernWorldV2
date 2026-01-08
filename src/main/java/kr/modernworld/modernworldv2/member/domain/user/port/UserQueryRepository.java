package kr.modernworld.modernworldv2.member.domain.user.port;

import java.util.Optional;
import kr.modernworld.modernworldv2.global.common.dto.PageResponseDTO;
import kr.modernworld.modernworldv2.member.application.user.OrderByField;
import kr.modernworld.modernworldv2.member.application.user.dto.UserDTO;
import kr.modernworld.modernworldv2.member.domain.user.User;

public interface UserQueryRepository {

  Optional<User> findByUniqueIdentifier(String uniqueIdentifier);

  Boolean exists(Long userNo);

  Optional<String> findNameByUserNo(Long userNo);

  Optional<UserDTO> findOne(Long userNo);

  PageResponseDTO<UserDTO> findAll(Long page, Long take, String animal,
      OrderByField orderBy,
      String nickname);
}
