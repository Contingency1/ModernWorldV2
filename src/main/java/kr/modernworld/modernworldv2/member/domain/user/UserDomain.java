package kr.modernworld.modernworldv2.member.domain.user;

import lombok.Getter;

@Getter
public enum UserDomain {
  NAVER("naver"), KAKAO("kakao"), GOOGLE("google");

  private final String domainName;

  UserDomain(String domainName) {
    this.domainName = domainName;
  }

  public static UserDomain strToUserDomain(String str) {
    for (UserDomain value : UserDomain.values()) {
      if (str.equals(value.getDomainName())) {
        return value;
      }
    }

    throw new IllegalArgumentException(str);
  }

}
