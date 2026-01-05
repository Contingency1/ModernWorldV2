package kr.modernworld.modernworldv2.social.infrastructure.external;

import kr.modernworld.modernworldv2.member.application.user.UserService;
import kr.modernworld.modernworldv2.social.domain.external.MemberExternalPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocialMemberExternalAdapter implements MemberExternalPort {

  private final UserService userService;

  @Override
  public void validateUser(Long userNo) {
    userService.isPresent(userNo);
  }

  @Override
  public String getUserName(Long userNo) {
    return userService.getUserNickname(userNo);
  }
}
