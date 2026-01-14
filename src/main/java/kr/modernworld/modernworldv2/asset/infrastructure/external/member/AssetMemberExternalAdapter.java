package kr.modernworld.modernworldv2.asset.infrastructure.external.member;

import kr.modernworld.modernworldv2.asset.domain.external.member.MemberExternalPort;
import kr.modernworld.modernworldv2.member.application.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssetMemberExternalAdapter implements MemberExternalPort {

  private final UserService userService;

  @Override
  public void lockUserByUserNo(Long userNo) {
    userService.getUserForUpdate(userNo);
  }

  @Override
  public void validateUser(Long userNo) {
    userService.isPresent(userNo);
  }

  @Override
  public void decreaseCurrentPoint(Long userNo, Long amount) {
    userService.decreaseCurrentPoint(userNo, amount);
  }

  @Override
  public void increaseCurrentAccumulationPoint(Long userNo, Long amount) {
    userService.increaseCurrentAccumulationPoint(userNo, amount);
  }

  @Override
  public String getUserName(Long userNo) {
    return userService.getUserNickname(userNo);
  }
}
