package kr.modernworld.modernworldv2.member.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.CharacterLockerJPAEntity;
import kr.modernworld.modernworldv2.asset.infrastructure.persistence.entity.InventoryJPAEntity;
import kr.modernworld.modernworldv2.growth.infrastructure.persistence.entity.LegendJPAEntity;
import kr.modernworld.modernworldv2.member.domain.user.UserDomain;
import kr.modernworld.modernworldv2.notification.infrastructure.entity.AlarmJPAEntity;
import kr.modernworld.modernworldv2.social.infrastructure.persistence.entity.RspGameRecordJPAEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "user", schema = "modernworld", uniqueConstraints = {
    @UniqueConstraint(name = "nickname", columnNames = {"nickname"}),
    @UniqueConstraint(name = "unique_identifier", columnNames = {"unique_identifier"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class UserJPAEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "no")
  private Long no;

  @Size(max = 10)
  @Column(name = "nickname", length = 10)
  private String nickname;

  @ColumnDefault("'0'")
  @Column(name = "current_point")
  private Long currentPoint;

  @ColumnDefault("'0'")
  @Column(name = "accumulation_point")
  private Long accumulationPoint;

  @Size(max = 150)
  @Column(name = "description", length = 150)
  private String description;

  @Column(name = "attendance")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, List<Integer>> attendance;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "status", nullable = false)
  private Boolean status = false;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "deleted_at")
  private Instant deletedAt;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "admin", nullable = false)
  private Boolean admin = false;

  @Size(max = 300)
  @NotNull
  @Column(name = "unique_identifier", nullable = false, length = 300)
  private String uniqueIdentifier;

  @Size(max = 200)
  @NotNull
  @Column(name = "social_name", nullable = false, length = 200)
  private String socialName;

  @Size(max = 300)
  @ColumnDefault("'https://ma7-production-s3-2.s3.ap-northeast-2.amazonaws.com/page/BaseProfileImage/pngwing.com.png'")
  @Column(name = "image", length = 300)
  private String image;

  @NotNull
  @Column(name = "domain", nullable = false, columnDefinition = "ENUM('naver', 'kakao', 'google')")
  private UserDomain domain;

  @ColumnDefault("'10'")
  @Column(name = "chance")
  private Long chance;

  // User의 생명주기와 완전히 동일한 관계
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private LegendJPAEntity legend;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<AlarmJPAEntity> alarms = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CharacterLockerJPAEntity> characterLockers = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<InventoryJPAEntity> inventories = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<RspGameRecordJPAEntity> rspGameRecords = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<UserAchievementJPAEntity> userAchievements = new ArrayList<>();
}