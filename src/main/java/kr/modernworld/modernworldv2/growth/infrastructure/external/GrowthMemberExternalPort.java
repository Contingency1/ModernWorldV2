package kr.modernworld.modernworldv2.growth.infrastructure.external;

import kr.modernworld.modernworldv2.growth.domain.external.MemberExternalPort;
import kr.modernworld.modernworldv2.member.application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrowthMemberExternalPort implements MemberExternalPort {

  private final UserService userService;

  @Override
  public void processGameResult(Long userNo, Long point) {
    userService.processGameResult(userNo, point);
  }

  @Override
  public void increaseCurrentAccumulationPoint(Long userNo, Long amount) {
    userService.increaseCurrentAccumulationPoint(userNo, amount);
  }
}
