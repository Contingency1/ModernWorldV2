package kr.modernworld.modernworldv2.user.domain.user;

import lombok.Getter;

@Getter
public class UserSocialToken {

  private final Long userNo;

  private final String socialAccessToken;

  private final String socialRefreshToken;

  public UserSocialToken(Long userNo, String socialAccessToken, String socialRefreshToken) {
    this.userNo = userNo;
    this.socialAccessToken = socialAccessToken;
    this.socialRefreshToken = socialRefreshToken;
  }
}
