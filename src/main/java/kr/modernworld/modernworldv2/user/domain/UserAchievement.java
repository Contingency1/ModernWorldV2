package kr.modernworld.modernworldv2.user.domain;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserAchievement {

  private Long no;

  private Long userNo;

  private Long achievementNo;

  private Boolean status;

  private Instant createdAt;

  public void validationUserNo(Long userNo) {
    if (!userNo.equals(this.userNo)) {
      throw new IllegalStateException("User Achievement is not valid");
    }
  }

  public void equip() {
    this.status = Boolean.TRUE;
  }

  public void unequip() {
    this.status = Boolean.FALSE;
  }

  @Builder
  private UserAchievement(Long no, Long userNo, Long achievementNo, Boolean status,
      Instant createdAt) {
    this.no = no;
    this.userNo = userNo;
    this.achievementNo = achievementNo;
    this.status = status;
    this.createdAt = createdAt;
  }

  public static UserAchievement init(Long userNo, Long achievementNo) {
    return UserAchievement.builder()
        .userNo(userNo)
        .achievementNo(achievementNo)
        .status(false)
        .createdAt(Instant.now())
        .build();
  }
}
