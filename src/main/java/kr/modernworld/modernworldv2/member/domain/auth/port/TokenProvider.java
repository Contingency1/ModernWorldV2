package kr.modernworld.modernworldv2.member.domain.auth.port;

import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenResultDTO;
import kr.modernworld.modernworldv2.member.infrastructure.auth.jwt.TokenUserInfoDTO;

public interface TokenProvider {

  TokenResultDTO createAccess(Long userNo, boolean isAdmin, Long time);

  TokenResultDTO createRefresh(Long userNo, boolean isAdmin, Long time);

  TokenUserInfoDTO validateAccess(String token);

  TokenUserInfoDTO validateRefresh(String token);
}
