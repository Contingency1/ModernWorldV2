package kr.modernworld.modernworldv2.user.domain.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

  private Long no;

  private String nickname;

  private Long currentPoint;

  private Long accumulationPoint;

  private String description;

  private Map<String, List<Integer>> attendance;

  private Boolean status;

  private LocalDateTime createdAt;

  private LocalDateTime deletedAt;

  private Boolean admin;

  private String uniqueIdentifier;

  private String socialName;

  private String image;

  private UserDomain domain;

  private Long chance;

  private UserToken token;

  private UserLegend legend;

  @Builder
  private User(Long no, String nickname, Long currentPoint, Long accumulationPoint,
      String description, Map<String, List<Integer>> attendance, Boolean status,
      LocalDateTime createdAt, LocalDateTime deletedAt, Boolean admin,
      String uniqueIdentifier, String socialName, String image,
      UserDomain domain, Long chance, UserToken token, UserLegend legend) {
    this.no = no;
    this.nickname = nickname;
    this.currentPoint = currentPoint;
    this.accumulationPoint = accumulationPoint;
    this.description = description;
    this.attendance = attendance;
    this.status = status;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    this.admin = admin;
    this.uniqueIdentifier = uniqueIdentifier;
    this.socialName = socialName;
    this.image = image;
    this.domain = domain;
    this.chance = chance;
    this.token = token;
    this.legend = legend;
  }

  public static User createFromSocial(String uniqueIdentifier, String socialName, String imageUrl,
      UserDomain domain) {
    User user = User.builder()
        .uniqueIdentifier(uniqueIdentifier)
        .socialName(socialName)
        .image(imageUrl)
        .domain(domain)
        .currentPoint(0L)
        .accumulationPoint(0L)
        .chance(10L)
        .status(false)
        .admin(false)
        .createdAt(LocalDateTime.now())
        .build();

    user.initLegend();

    return user;
  }

  private void initLegend() {
    this.legend = UserLegend.init(no);
  }

  public void decreaseCurrentPoint(Long point) {
    if (point > this.currentPoint) {
      throw new IllegalArgumentException("Current point is greater than the input point");
    }

    this.currentPoint -= point;
  }

  public void increaseCurrentAccumulationPoint(Long point) {
    this.currentPoint += point;
    this.accumulationPoint += point;
  }

  public void nullifyDeletedAt() {
    if (this.deletedAt != null) {
      this.deletedAt = null;
    }
  }

  public void updateToken(String accessToken, String refreshToken) {
    if (this.token == null) {
      this.token = new UserToken(no, accessToken, refreshToken);
      return;
    }

    this.token.update(accessToken, refreshToken);
  }

}