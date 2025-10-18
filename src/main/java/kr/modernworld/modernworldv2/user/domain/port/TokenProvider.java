package kr.modernworld.modernworldv2.user.domain.port;

import kr.modernworld.modernworldv2.user.infrastructure.repository.TokenResultDTO;

public interface TokenProvider {

  TokenResultDTO createAccess(Long userNo, boolean isAdmin, Long time);

  TokenResultDTO createRefresh(Long userNo, boolean isAdmin, Long time);
}
