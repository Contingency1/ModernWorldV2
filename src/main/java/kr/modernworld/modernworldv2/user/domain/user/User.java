package kr.modernworld.modernworldv2.user.domain.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

  private final Long no;

  private String nickname;

  private Long currentPoint;

  private Long accumulationPoint;

  private String description;

  private Map<String, List<Integer>> attendance;

  private Boolean status;

  private final LocalDateTime createdAt;

  private LocalDateTime deletedAt;

  private Boolean admin;

  private final String uniqueIdentifier;

  private String socialName;

  private String image;

  private UserDomain domain;

  private Long chance;

  private UserSocialToken token;

  @Builder
  private User(Long no, String nickname, Long currentPoint, Long accumulationPoint,
      String description, Map<String, List<Integer>> attendance, Boolean status,
      LocalDateTime createdAt, LocalDateTime deletedAt, Boolean admin,
      String uniqueIdentifier, String socialName, String image,
      UserDomain domain, Long chance, UserSocialToken token) {
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
        .attendance(createInitialAttendance())
        .build();

    return user;
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
    this.token = new UserSocialToken(this.no, accessToken, refreshToken);
  }

  private static Map<String, List<Integer>> createInitialAttendance() {
    Map<String, List<Integer>> data = new LinkedHashMap<>();

    data.put("0", new ArrayList<>(List.of(0, 100)));
    data.put("1", new ArrayList<>(List.of(0, 200)));
    data.put("2", new ArrayList<>(List.of(0, 300)));
    data.put("3", new ArrayList<>(List.of(0, 200)));
    data.put("4", new ArrayList<>(List.of(0, 400)));
    data.put("5", new ArrayList<>(List.of(0, 300)));
    data.put("6", new ArrayList<>(List.of(0, 300)));

    return data;
  }

  public void decreaseChance() {
    if (this.chance <= 0) {
      throw new IllegalStateException("Chance must be greater than zero");
    }

    this.chance -= 1L;
  }
}