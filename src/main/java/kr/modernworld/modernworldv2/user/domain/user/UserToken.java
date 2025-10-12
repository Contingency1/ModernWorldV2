package kr.modernworld.modernworldv2.user.domain.user;

import lombok.Getter;

@Getter
public class UserToken {

  private final Long userNo;

  private String socialAccessToken;

  private String socialRefreshToken;

  public UserToken(Long userNo, String socialAccessToken, String socialRefreshToken) {
    this.userNo = userNo;
    this.socialAccessToken = socialAccessToken;
    this.socialRefreshToken = socialRefreshToken;
  }

  public void update(String accessToken, String refreshToken) {
    this.socialAccessToken = accessToken;
    this.socialRefreshToken = refreshToken;
  }
}
